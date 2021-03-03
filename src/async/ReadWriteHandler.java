package async;

import java.io.IOException;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {

    private static final Charset charSetUTF8 = StandardCharsets.UTF_8;

    @Override
    public void completed(Integer result, Attachment attachment) {
        if (result == -1) {
            try {
                attachment.channelClient.close();
                System.out.println("Stopping listening to client: " + attachment.clientAddress);
            } catch (IOException ioException) {
                System.out.println("Exception caught while closing the stream: " + ioException);
            }

            return;
        }

        if (attachment.isReadMode) {
            attachment.buffer.flip();
            int limit = attachment.buffer.limit();
            byte[] bytes = new byte[limit];
            attachment.buffer.get(bytes, 0, limit);
            System.out.println("Message recieved from client: " +  new String(bytes, charSetUTF8));
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
    public void failed(Throwable exc, Attachment attachment) {
        System.out.println("Connection with the client is broken");
    }
}
