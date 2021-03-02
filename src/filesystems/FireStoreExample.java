package filesystems;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FireStoreExample {
    public static void main(String[] args) throws IOException {

        FileStore fs = Files.getFileStore(Paths.get("data/enc.txt"));
        System.out.printf("Total space: %d%n", fs.getTotalSpace());
        System.out.printf("Unallocated space: %d%n", fs.getUnallocatedSpace());
        System.out.printf("Usable space: %d%n", fs.getUsableSpace());
        System.out.printf("Is ReadOnly ? : %b%n", fs.isReadOnly());
        System.out.printf("File System type: %s%n", fs.type());
    }
}
