package filewatcher;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcherServiceExample {

    public static void main(String[] args) throws IOException, InterruptedException {

        FileSystem defaultFileSystem = FileSystems.getDefault();
        WatchService ws = defaultFileSystem.newWatchService();
        Path filePath = defaultFileSystem.getPath("C:\\Users\\hw1003685\\Desktop\\wow");
        filePath.register(ws, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        while (true) {
            WatchKey watchKey = null;
            try {
                watchKey = ws.take();
            } catch (InterruptedException interruptedException) {
                System.out.println("Exception Caught: " + interruptedException.getMessage());
            }

            TimeUnit.SECONDS.sleep(1);

            for (WatchEvent event: watchKey.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    System.out.println("Overflow");
                    continue;
                }

                if (kind == ENTRY_MODIFY) {
                    WatchEvent ev = (WatchEvent) event;
                    Path fileName = (Path) ev.context();
                    System.out.printf("%s: %S%n", ev.kind(), fileName);
                }
            }

            boolean valid = watchKey.reset();
            if (! valid)
                break;
        }

    }
}
