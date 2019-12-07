package network;

public class NetworkMessage {

    //todo convert data to json
    private String data;
    private String address;
    private NetworkOrigin origin;

    public NetworkMessage(String data, String address, NetworkOrigin origin) {
        this.data = data;
        this.address = address.replaceAll("[^0-9.a-zA-Z]+", "");
        this.origin = origin;
    }

    public String getData() {
        return data;
    }


    public String getAddress() {
        return address;
    }

    public NetworkOrigin getOrigin() {
        return origin;
    }


    @Override
    public String toString() {
        return "NetworkMessage{" +
                "data='" + data + '\'' +
                ", address='" + address + '\'' +
                ", origin=" + origin +
                '}';
    }
}
