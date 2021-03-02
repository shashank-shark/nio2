package filesystems;

import java.nio.file.spi.FileSystemProvider;
import java.util.List;

public class FileSystemProvidersExample {
    public static void main(String[] args) {

        List<FileSystemProvider> fileSystemProviderList = FileSystemProvider.installedProviders();
        for (FileSystemProvider fileSystemProvider : fileSystemProviderList) {
            System.out.println("******************************************");
            System.out.println("Scheme: " + fileSystemProvider.getScheme());
            System.out.println("Provider: " + fileSystemProvider);
        }
    }
}
