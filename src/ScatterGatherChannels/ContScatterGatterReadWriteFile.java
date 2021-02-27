package ScatterGatherChannels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class ContScatterGatterReadWriteFile {

    public static void main(String[] args) throws IOException {

        ScatteringByteChannel sourceOfByteScatter;
        File inputFile = new File("data/patrons.ldif");
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        sourceOfByteScatter = (ScatteringByteChannel) Channels.newChannel(fileInputStream);

        // create 4 ByteBuffers of each 2048 bytes
        ByteBuffer appOneBuffer = ByteBuffer.allocateDirect(2048);
        ByteBuffer appTwoBuffer = ByteBuffer.allocateDirect(4096);
        ByteBuffer[] byteBuffers = { appOneBuffer, appTwoBuffer };

        GatheringByteChannel destinationForByteScatter;
        File outputFile = new File("data/newScatterFile.txt");
        if (! outputFile.exists()) outputFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true);
        destinationForByteScatter = (GatheringByteChannel) Channels.newChannel(fileOutputStream);

        while (sourceOfByteScatter.isOpen() && sourceOfByteScatter.read(byteBuffers) != -1) {
            byteBuffers[0] = appOneBuffer;
            byteBuffers[1] = appTwoBuffer;
            destinationForByteScatter.write(byteBuffers);
            byteBuffers[0].clear(); byteBuffers[1].clear();
        }

    }
}
