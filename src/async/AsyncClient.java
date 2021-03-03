package async;

import com.sun.jmx.snmp.SnmpInt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class AsyncClient {

    private static final Charset charSetUTF8 = StandardCharsets.UTF_8;
    private static final int PORT = 9999;
    private static final String hostname = "localhost";

    public static void main (String[] args) {

        AsynchronousSocketChannel channel = null;
        try {
            channel = AsynchronousSocketChannel.open();
        } catch (IOException ioException) {
            System.out.println("Exception caught: " + ioException.getMessage());
        }

        try {
            channel.connect(new InetSocketAddress(hostname, PORT)).get();
            System.out.println("Client at " + channel.getLocalAddress() + " is connected");
        } catch (ExecutionException | InterruptedException | IOException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        Attachment attachment = new Attachment();
        attachment.channelClient = channel;
        attachment.isReadMode = false;
        attachment.buffer = ByteBuffer.allocate(2048);
        attachment.thread = Thread.currentThread();

        byte[] data = "Hello".getBytes(StandardCharsets.UTF_8);
        attachment.buffer.put(data);
        attachment.buffer.flip();
        channel.write(attachment.buffer,attachment, new ReadWriteHandler());

        try {
            attachment.thread.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
