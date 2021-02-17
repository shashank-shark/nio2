package file;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileExamples {
    public static void main(String[] args) throws IOException {

        File file = new File("data/patrons.ldif");
        System.out.println(Arrays.toString(file.getParentFile().listFiles()));
    }
}
