package mappedfile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Note that the maximum file size that can be mapped is 2GB, because of Integer.MAX_VALUE.
 * To map large files, either we have to break the files into pieces or use libraries using
 * java Unsafe operations.
 */

public class MappingFileToMemory {

    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\hw1003685\\Documents\\en_visio_professional_2019_x86_x64_dvd_3b951cef.iso");
        RandomAccessFile raf = new RandomAccessFile(file.getPath(), "rw");
        FileChannel fc = raf.getChannel();

        long size = fc.size();
        System.out.println("Starting to map " + size + " bytes to memory");
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
        if (mbb.remaining() > 0)    System.out.println("Mapping successful");

        System.out.println("MappedByteBuffer limit: " + mbb.limit());

        byte b1 = mbb.get(0);
        System.out.println((char) b1);

    }
}
