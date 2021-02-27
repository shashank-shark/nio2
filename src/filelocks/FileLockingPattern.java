package filelocks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;


public class FileLockingPattern {
    public static void main(String[] args) throws IOException {

        RandomAccessFile accessFile = new RandomAccessFile("data/simple.txt", "rw");
        FileChannel fileChannel = accessFile.getChannel();

        FileLock lock = fileChannel.lock();
        try {
            // here we try to interact with the file channel
            throw new IOException();
        } catch (IOException ioException) {
            // handle the exception
        } finally {
            lock.release();
        }

    }
}
