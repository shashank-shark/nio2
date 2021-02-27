package ScatterGatherChannels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class ContScatterGatterReadWriteFile {

    public static void main(String[] args) throws IOException {

        ScatteringByteChannel sourceOfByteScatter;
        File inputFile = new File("data/patrons.ldif");
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        sourceOfByteScatter = (ScatteringByteChannel) Channels.newChannel(fileInputStream);

        long totalNumberOfBytesToScatter = inputFile.length();

        // create 4 ByteBuffers of each 2048 bytes
        ByteBuffer[] byteBuffers = new ByteBuffer[4];
        for(int i = 0; i < 4; i ++) {
            byteBuffers[i] = ByteBuffer.allocateDirect(2048);
        }


        GatheringByteChannel destinationForByteScatter;
        File outputFile = new File("data/newScatterFile.txt");
        if (! outputFile.exists()) outputFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        destinationForByteScatter = (GatheringByteChannel) Channels.newChannel(fileOutputStream);

        long numberOfBytesRead = 0L;
        do {
            numberOfBytesRead += sourceOfByteScatter.read(byteBuffers);
            destinationForByteScatter.write(byteBuffers);
            for (int i = 0; i < 4; i ++) {
                byteBuffers[i].clear();
            }
        } while (numberOfBytesRead < totalNumberOfBytesToScatter);

    }
}
