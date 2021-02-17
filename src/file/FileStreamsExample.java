package file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileStreamsExample {
    public static void main(String[] args) {

        FileInputStream fis = null;

        try {
            fis = new FileInputStream("data/patrons.ldif");

            int _byte;
            while ((_byte = fis.read()) != -1) {
                System.out.print(_byte);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file is not found: " + fileNotFoundException.getMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

    }
}