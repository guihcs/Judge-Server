package network;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

public class Connection {

    private DataOutputStream writer;
    private InputStream inputStream;
    private Socket socket;
    private Queue<String> messageQueue = new LinkedList<>();

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        writer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        inputStream = socket.getInputStream();
    }

    public void send(String data) throws IOException{

        writer.write(data.getBytes(StandardCharsets.UTF_8));
        writer.flush();
    }

    public void read(int size){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(size);
            int totalRead = 0;
            while (inputStream.available() > 0 && totalRead < size){
                byte val = (byte)inputStream.read();

                if(val == '\n') {

                    byte[] result = new byte[totalRead];

                    System.arraycopy(buffer.array(), 0, result, 0, totalRead);

                    messageQueue.add(new String(result, StandardCharsets.UTF_8));
                    buffer = ByteBuffer.allocate(size - totalRead);
                    totalRead = 0;
                    continue;
                }
                buffer.put(val);
                totalRead++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAlive(){
        return !socket.isClosed();
    }

    public boolean hasNext(){
        return messageQueue.size() > 0;
    }

    public String next(){
        return messageQueue.poll();
    }


    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
