package asyncfixed;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;

public class AsyncServerFixed {

    private static final int PORT = 9999;
    private static final String hostname = "localhost";

    public static void main(String[] args) {

        AsynchronousServerSocketChannel serverChannel = null;
        try {
            serverChannel = AsynchronousServerSocketChannel.open();
        } catch (IOException ioException) {
            System.out.printf("Exception caught when opening the async server channel: %s%n", ioException.getMessage());
        }

        ServerAttachment serverAttachment = new ServerAttachment();
        serverAttachment.channelServer = serverChannel;
        serverChannel.accept(serverAttachment, new ConnectionHandler());

        try {
            Thread.currentThread().join();
        } catch (InterruptedException interruptedException) {
            System.out.println("Server terminating");
        }
    }
}
