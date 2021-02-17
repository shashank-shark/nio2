package file;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputStreamTryCatch {
    public static void main(String[] args) {

        try (FileInputStream fis = new FileInputStream("data/patrons.ldif")) {
            int _byte = 0;

            while ((_byte = fis.read()) != -1) {
                System.out.print((char) _byte);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
