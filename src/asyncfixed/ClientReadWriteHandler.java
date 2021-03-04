package asyncfixed;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientReadWriteHandler implements CompletionHandler<Integer, ClientAttachment> {

    private static final Charset charSetUTF8 = StandardCharsets.UTF_8;

    @Override
    public void completed(Integer result, ClientAttachment attachment) {

        if (result == -1) {

            try {
                attachment.channel.close();
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
            try {
                System.out.printf("Client at %s sends message: %s%n", attachment.channel.getRemoteAddress(), new String(buffer, charSetUTF8));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            attachment.isReadMode = false;
            attachment.buffer.rewind();
            attachment.channel.write(attachment.buffer, attachment, this);
        } else {
            attachment.isReadMode = true;
            attachment.buffer.clear();
            attachment.channel.read(attachment.buffer, attachment, this);
        }
    }

    @Override
    public void failed(Throwable exc, ClientAttachment attachment) {
        System.out.println("Connection with client is broken");
    }
}
