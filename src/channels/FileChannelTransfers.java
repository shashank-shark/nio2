package channels;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class FileChannelTransfers {

    public static void main (String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream(new File("data/patrons.ldif"));
        FileChannel inputFileChannel = inputStream.getChannel();

        WritableByteChannel writeChannel = Channels.newChannel(System.out);
        inputFileChannel.transferTo(0, inputFileChannel.size(), writeChannel);

        try {
            writeChannel.close();   inputFileChannel.close();
            inputStream.close();
        } catch (IOException ioException) {
            System.out.println("Error in closing channel: " + ioException.getMessage());
        }
    }
}
