package async;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;

public class AsyncServer {

    private static final int PORT = 9999;
    private static final String HOST = "localhost";

    public static void main (String[] args) {

        AsynchronousServerSocketChannel channelServer = null;
        try {
            channelServer = AsynchronousServerSocketChannel.open();
        } catch (IOException ioException) {
            System.out.println("Exception caught: " + ioException.getMessage());
        }

        Attachment attachment = new Attachment();
        attachment.channelServer = channelServer;
        channelServer.accept(attachment, new ConnectionHandler());

        try {
            Thread.currentThread().join();
        } catch (InterruptedException interruptedException) {
            System.out.println("Interrupt recieved : Server Terminating");
        }
    }
}
