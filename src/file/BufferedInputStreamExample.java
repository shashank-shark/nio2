package file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * If you are consistently doing small reads then a BufferedInputStream will give you significantly better performance.
 * Each read request on an unbuffered stream typically results in a system call to the operating system to read
 * the requested number of bytes. The overhead of doing a system call is may be thousands of machine instructions
 * per syscall. A buffered stream reduces this by doing one large read for (say) up to 8k bytes into an internal buffer,
 * and then handing out bytes from that buffer. This can drastically reduce the number of system calls.
 *
 * However, if you are consistently doing large reads (e.g. 8k or more) then a BufferedInputStream slows
 * things a bit. You typically don't reduce the number of syscalls, and the buffering introduces an extra data copying
 * step.
 *
 * In your use-case (where you read a 20 byte chunk first then lots of large chunks)
 * I'd say that using a BufferedInputStream is more likely to reduce performance than increase it.
 * But ultimately, it depends on the actual read patterns.
 */

public class BufferedInputStreamExample {

    public static void main(String[] args) {

        /*
         * As you picked up, BufferedInputStream is about reading in blocks of data rather than a single byte at a time.
         * It also provides the convenience method of readLine(). However, it's also used for peeking at
         * data further in the stream then rolling back to a previous part of the stream if required
         * (see the mark() and reset() methods)
         */

        try (FileInputStream fis = new FileInputStream("data/patrons.ldif");
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int _byte = 0;
            while ((_byte = bis.read()) != -1) {
                System.out.print((char) _byte);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
