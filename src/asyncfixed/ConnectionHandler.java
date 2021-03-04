package asyncfixed;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * For managing connections from clients.
 */
public class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, ServerAttachment> {

    @Override
    public void completed(AsynchronousSocketChannel client, ServerAttachment attachment) {

        // once the connection is accepted from client
        try {
            SocketAddress clientAddress = client.getRemoteAddress();
            System.out.printf("Accepted connection from %s%n", clientAddress);
            attachment.channelServer.accept(attachment, this);

            ServerAttachment newServerAttachment = new ServerAttachment();
            newServerAttachment.channelServer = attachment.channelServer;
            newServerAttachment.channelClient = client;
            newServerAttachment.isReadMode = true;
            newServerAttachment.buffer = ByteBuffer.allocate(2048);
            newServerAttachment.clientAddress = clientAddress;

            ServerReadWriteHandler serverReadWriteHandler = new ServerReadWriteHandler();
            client.read(newServerAttachment.buffer, newServerAttachment, serverReadWriteHandler);

        } catch (IOException ioException) {
            System.out.printf("Exception caught while accepting and processing client connection: %s%n", ioException.getMessage());
        }
    }

    @Override
    public void failed(Throwable exc, ServerAttachment attachment) {
        System.out.println("Failed to accept client connection");
    }
}
