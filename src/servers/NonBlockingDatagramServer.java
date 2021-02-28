package servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NonBlockingDatagramServer {
    public static void main(String[] args)throws IOException {

        System.out.println("UDP Server starting and listening at port : " + 9999 + " for incoming requests");
        DatagramChannel dcServer = DatagramChannel.open();
        dcServer.socket().bind(new InetSocketAddress(9999));

        ByteBuffer symbol = ByteBuffer.allocate(4);
        ByteBuffer payload = ByteBuffer.allocate(16);

        while (true) {
            payload.clear();
            symbol.clear();

            SocketAddress sa = dcServer.receive(symbol);
            if (sa == null)
                return;

            System.out.println("Received request from " + sa);
            String stockSymbol = new String(symbol.array(), 0 , 4);
            System.out.println("Stock Symbol : " + stockSymbol);

            if (stockSymbol.toUpperCase().equals("MSTF")) {
                payload.putFloat(0, 37.04f);
                payload.putFloat(4, 37.22f);
                payload.putFloat(8, 37.48f);
                payload.putFloat(12, 37.41f);
            } else {
                payload.putFloat(0,0.0f);
                payload.putFloat(4, 0.0f);
                payload.putFloat(8, 0.0f);
                payload.putFloat(12, 0.0f);
            }

            dcServer.send(payload, sa);
        }

    }
}
