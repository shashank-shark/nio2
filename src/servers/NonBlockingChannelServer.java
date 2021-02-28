package servers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class NonBlockingChannelServer {

    public static void main(String[] args) throws IOException {

        System.out.println("Starting the server . .");
        ServerSocketChannel scc = ServerSocketChannel.open();

        scc.socket().bind(new InetSocketAddress(9999));
        scc.configureBlocking(false);
        String message = "Hello Shashank";
        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());

        while (true) {
            System.out.print(".");
            SocketChannel sc = scc.accept();

            if (sc != null) {
                System.out.println();
                System.out.println("Recieved connection from: " + sc.socket().getRemoteSocketAddress());
                byteBuffer.rewind();
                sc.write(byteBuffer);
                sc.close();
            } else {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException interruptedException) {
                    System.out.println("Caught thread interruption: " + interruptedException.getMessage());
                }
            }
        }
    }
}
