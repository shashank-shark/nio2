package asyncfixed;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ServerReadWriteHandler implements CompletionHandler<Integer, ServerAttachment> {

    private static final Charset charSetUTF8 = StandardCharsets.UTF_8;

    @Override
    public void completed(Integer result, ServerAttachment attachment) {

        if (result == -1) {

            try {
                attachment.channelClient.close();
            } catch (IOException ioException) {
                System.out.printf("Exception caught while closing connection with client: %s%n" , ioException.getMessage());
            }

            return;
        }

        if (attachment.isReadMode) {
            attachment.buffer.flip();
            int limit = attachment.buffer.limit();
            byte[] buffer = new byte[limit];
            attachment.buffer.get(buffer, 0, limit);
            System.out.printf("Client at %s sends message: %s%n", attachment.clientAddress, new String(buffer, charSetUTF8));
            attachment.isReadMode = false;
            attachment.buffer.rewind();
            attachment.channelClient.write(attachment.buffer, attachment, this);
        } else {
            attachment.isReadMode = true;
            attachment.buffer.clear();
            attachment.channelClient.read(attachment.buffer, attachment, this);
        }
    }

    @Override
    public void failed(Throwable exc, ServerAttachment attachment) {
        System.out.println("Connection with client is broken");
    }
}
