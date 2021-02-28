package servers;

import java.net.InetSocketAddress;
import java.net.SocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NonBlockingChannelClient {
    public static void main(String[] args) {

        try {

            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress("localhost", 9999);
            sc.connect(address);

            while (! sc.finishConnect())
                System.out.println("Waiting to finish connection ..");
            System.out.println("Connection is established ..");

            ByteBuffer byteBuffer = ByteBuffer.allocate(200);
            while (sc.read(byteBuffer) >= 0) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    System.out.print((char) byteBuffer.get());
                }
                byteBuffer.clear();
            }

            sc.close();

        } catch (Exception exception) {
            System.out.println("Error Occurred: " + exception.getMessage());
        }
    }
}
