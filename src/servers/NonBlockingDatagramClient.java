package servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NonBlockingDatagramClient {
    public static void main(String[] args) throws IOException {

        DatagramChannel dcClient = DatagramChannel.open();

        ByteBuffer symbol = ByteBuffer.wrap("MSTF".getBytes());
        ByteBuffer responsePayload = ByteBuffer.allocate(16);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 9999);
        dcClient.send(symbol, inetSocketAddress);

        System.out.println("Recieving response payload from server : ");
        dcClient.receive(responsePayload);

        System.out.println(responsePayload.getFloat(0));;
        System.out.println(responsePayload.getFloat(4));
        System.out.println(responsePayload.getFloat(8));
        System.out.println(responsePayload.getFloat(12));
    }
}
