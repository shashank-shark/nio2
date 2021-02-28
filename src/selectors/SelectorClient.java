package selectors;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class SelectorClient {

    public static int port = 9999;
    public static final String host = "localhost";
    public static ByteBuffer timeBuffer = ByteBuffer.allocateDirect(8);

    public static void main(String[] args) {

        try {

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host, port));

//            // wait for connection
//            while (! socketChannel.isConnected()) {
//            }

            long time = 0L;
            timeBuffer.clear();
            while (socketChannel.read(timeBuffer) != -1) {
                timeBuffer.flip();
                time = timeBuffer.getLong();

//                while (timeBuffer.hasRemaining()) {
//                    time = time << 8;
//                    time = time | timeBuffer.get() & 255;
//                }

                Date date = new Date(time);
                System.out.println("Time recieved from the server is: " + date.toString());
                timeBuffer.clear();
            }
            socketChannel.close();

        } catch (Exception exception) {
            System.out.println("Exception caught: " + exception.getMessage());
        }

    }
}
