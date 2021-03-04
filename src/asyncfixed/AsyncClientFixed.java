package asyncfixed;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class AsyncClientFixed {

    private final static Charset charSetUTF8 = StandardCharsets.UTF_8;
    private static final int PORT = 9999;
    private static final String hostname = "localhost";

    public static void main(String[] args) {
        AsynchronousSocketChannel clientChannel = null;

        try {
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException ioException) {
            System.out.printf("Exception caught while opening connection with server: %s%n", ioException.getMessage());
            return;
        }

        try {
            clientChannel.connect(new InetSocketAddress(hostname, PORT)).get();
            System.out.printf("Client at %s connected %n", clientChannel.getLocalAddress());
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

        ClientAttachment clientAttachment = new ClientAttachment();
        clientAttachment.channel = clientChannel;
        clientAttachment.isReadMode = false;
        clientAttachment.buffer = ByteBuffer.allocate(2048);
        clientAttachment.mainThread = Thread.currentThread();

        byte[] data = "Hello".getBytes(StandardCharsets.UTF_8);
        clientAttachment.buffer.put(data);
        clientAttachment.buffer.flip();
        clientAttachment.channel.write(clientAttachment.buffer, clientAttachment, new ClientReadWriteHandler());

        try {
            clientAttachment.mainThread.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

    }
}
