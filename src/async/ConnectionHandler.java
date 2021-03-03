package async;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ConnectionHandler  implements CompletionHandler<AsynchronousSocketChannel, Attachment> {

    // when the connection is successfully established
    @Override
    public void completed(AsynchronousSocketChannel clientChannel, Attachment attachment) {
        try {
            SocketAddress clientAddress = clientChannel.getRemoteAddress();
            System.out.println("Accepted connection from: " + clientAddress);
            attachment.channelServer.accept(attachment, this);

            Attachment newAttachment = new Attachment();
            newAttachment.channelServer = attachment.channelServer;
            newAttachment.channelClient = clientChannel;
            newAttachment.isReadMode = true;
            newAttachment.buffer = ByteBuffer.allocate(2048);
            newAttachment.clientAddress = clientAddress;

            ReadWriteHandler rwh = new ReadWriteHandler();
            clientChannel.write(newAttachment.buffer, newAttachment, rwh);

        } catch (IOException ioException) {
            System.out.println("Exception caught in complete: " + ioException.getMessage());
        }

    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {
        System.out.println("Failed to accept connection: ");
    }
}
