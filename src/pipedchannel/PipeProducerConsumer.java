package pipedchannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.TimeUnit;

public class PipeProducerConsumer {

    public static final int BUFSIZE = 10;
    public static final int LIMIT = 3;

    public static void main(String[] args) throws IOException {

        final Pipe pipe = Pipe.open();
        ByteBuffer senderBuffer = ByteBuffer.allocate(BUFSIZE);
        ByteBuffer receiverBuffer = ByteBuffer.allocate(BUFSIZE);

        Runnable senderTask = () -> {
            WritableByteChannel source = pipe.sink();
            senderBuffer.clear();
            for (int i = 0; i < LIMIT; i ++) {

                for (int j = 0; j < BUFSIZE; j ++) {
                    senderBuffer.put((byte) (Math.random() * 256));
                }
                senderBuffer.flip();

                try {
                    while (source.write(senderBuffer) > 0);
                } catch (IOException ioException) {
                    System.out.println("Exception caught: " + ioException.getMessage());
                }
                senderBuffer.clear();
            }
            try {
                source.close();
            } catch (IOException ioException) {
                System.out.println("Exception caught while closing the source: " + ioException.getMessage());
            }
        };

        Runnable receiverTask = () -> {
            ReadableByteChannel dest = pipe.source();
            try {
                while (dest.read(receiverBuffer) >= 0) {
                    receiverBuffer.flip();
                    while (receiverBuffer.remaining() > 0) {
                        System.out.println(receiverBuffer.get() & 255);
                    }
                    receiverBuffer.clear();
                }

                dest.close();
            } catch (IOException ioException) {
                System.out.println("Exception caught: " + ioException.getMessage());
            }

        };

        Thread sender = new Thread(senderTask);
        long senderId = sender.getId();
        sender.start();
        System.out.println(senderId + " started.");

        Thread receiver = new Thread(receiverTask);
        long receiverId = receiver.getId();
        receiver.start();
        System.out.println(receiverId + "started.");

        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException interruptedException) {
            System.out.println("Thread interrupted: " + interruptedException.getMessage());
        }


        System.out.println("State of Sender thread: " + sender.getState());
        System.out.println("State of Receiver thread: " + receiver.getState());

    }
}
