package filelocks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class ChannelFileLockExampleWriter {

    public static final int MAXQUERIES = 150000;
    public static final int MAXUPDATES = 150000;

    public static final int RECLEN = 16;

    public static ByteBuffer byteBuffer = ByteBuffer.allocate(RECLEN);
    public static IntBuffer intBuffer = byteBuffer.asIntBuffer();

    public static int counter = 1;

    public static void main(String[] args) throws IOException {

        boolean writer = false;

        File writeLogFile = new File("C:\\Users\\hw1003685\\Documents\\logfiles\\logwrite.txt");
        if (! writeLogFile.exists())
            writeLogFile.createNewFile();

        if (args.length != 0)
            writer = true;

        File tempFile = new File("temp.txt");
        if (! tempFile.exists())
            tempFile.createNewFile();

        RandomAccessFile raf = new RandomAccessFile("temp.txt", (writer) ? "rw" : "r");
        FileChannel fileChannel = raf.getChannel();

        if (writer)
            update(fileChannel);
        else
            query(fileChannel);

    }

    public static void query(FileChannel fileChannel) throws IOException {

        for (int i = 0; i < MAXQUERIES; i ++) {
            System.out.print("Acquiring shared lock : ");

            FileLock lock = fileChannel.lock(0, RECLEN, true);
            System.out.println("Successfully azquired shared lock");

            try {
                byteBuffer.clear();
                fileChannel.read(byteBuffer, 0);

                int a = intBuffer.get(0);
                int b = intBuffer.get(1);
                int c = intBuffer.get(2);
                int d = intBuffer.get(3);

                System.out.println("Reading: " + a + ", " + b + ", " + c + ", " + d);
                if (a*2 != b || a*3 != c || a*4 != d) {
                    System.out.println("ERROR");
                    return;
                }
            } finally {
                lock.release();
            }
        }
    }

    public static void update(FileChannel fileChannel) throws IOException {

        for (int i = 0; i < MAXUPDATES; i ++) {
            System.out.print ("Acquiring exclusive lock : ");
            FileLock lock = fileChannel.lock(0, RECLEN, false);
            System.out.println ("Lock successfully acquired");

            try {
                intBuffer.clear();
                int a = counter;
                int b = counter * 2;
                int c = counter * 3;
                int d = counter * 4;

                System.out.println("Writing: " + a + ", " + b + ", " + c + ", " + d);
                intBuffer.put(a);
                intBuffer.put(b);
                intBuffer.put(c);
                intBuffer.put(d);

                counter ++;

                byteBuffer.clear();
                fileChannel.write(byteBuffer, 0);
            } finally {
                lock.release();
            }
        }
    }
}
