package network;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetworkAdapter {

    private static NetworkAdapter instance;
    private MulticastSocket datagramSocket;
    private volatile boolean isReceivingMulticast = false;
    private List<MessageListener> listeners;
    private Map<String, Connection> connectionMap;
    private Thread socketListener;

    private NetworkAdapter(){
        try {
            connectionMap = new HashMap<>();
            listeners = new LinkedList<>();

            datagramSocket = new MulticastSocket(6001);
            datagramSocket.joinGroup(InetAddress.getByName("233.233.233.233"));
            datagramSocket.setSoTimeout(5000);
            isReceivingMulticast = true;

            Thread datagramListener = new Thread(this::listenMulticast);
            datagramListener.setDaemon(true);
            datagramListener.start();

            socketListener = new Thread(this::listenSocket);
            socketListener.setDaemon(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static NetworkAdapter getInstance() {
        if(instance == null) instance = new NetworkAdapter();
        return instance;
    }

    public void sendToAll(String data){
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String address, String data){

        if(!connectionMap.containsKey(address)){
            try {
                Socket socket = new Socket(InetAddress.getByName(address), 6000);
                connectionMap.put(address, new Connection(socket));
                if(!socketListener.isAlive()) socketListener.start();
                connectionMap.get(address).send(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connectionMap.get(address).send(data);
            } catch (IOException e) {
                connectionMap.get(address).close();
                connectionMap.remove(address);
                send(address, data);
            }
        }
    }


    public void onData(MessageListener listener){
        listeners.add(listener);
    }

    public void close(){
        isReceivingMulticast = false;
        connectionMap = null;
    }


    private void listenMulticast(){
        while (isReceivingMulticast){

            try{
                byte[] data = new byte[512000];
                DatagramPacket packet = new DatagramPacket(data, data.length);

                datagramSocket.receive(packet);
                byte[] dataRead = new byte[packet.getLength()];
                System.arraycopy(data, 0, dataRead, 0, dataRead.length);
                String text = new String(dataRead, StandardCharsets.UTF_8);

                for (MessageListener listener : listeners) {
                    listener.listen(new NetworkMessage(text, packet.getAddress().toString(), NetworkOrigin.DATAGRAM));
                }
            }catch (Exception e){
                //e.printStackTrace();
            }

        }
    }


    private void listenSocket(){

        while (isReceivingMulticast) {
            for (String key : connectionMap.keySet()) {
                Connection connection = connectionMap.get(key);
                connection.read(512000);
                while (connection.hasNext()) {
                    String data = connection.next();
                    for (MessageListener listener : listeners) {
                        listener.listen(new NetworkMessage(data, key, NetworkOrigin.SOCKET));
                    }
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
