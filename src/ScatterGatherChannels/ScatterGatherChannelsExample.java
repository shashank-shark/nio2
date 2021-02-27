package ScatterGatherChannels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class ScatterGatherChannelsExample {
	
	public static void main(String[] args) throws IOException {
		
		ScatteringByteChannel src;
		
		File file = new File("data/patrons.ldif");
		FileInputStream fis = new FileInputStream(file);
		src = (ScatteringByteChannel) Channels.newChannel(fis);

		System.out.println("Total size of the file is : " + file.length());
		ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(300);
		ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(200);
		
		ByteBuffer[] byteBuffers = {byteBuffer1, byteBuffer2};
		src.read(byteBuffers);

		byteBuffer1.flip();
		while (byteBuffer1.hasRemaining()) {
			System.out.print((char) byteBuffer1.get());
		}
		System.out.println();

		byteBuffer2.flip();
		while (byteBuffer2.hasRemaining()) {
			System.out.print((char) byteBuffer2.get());
		}
		System.out.println();

		byteBuffer1.rewind();
		byteBuffer2.rewind();

		GatheringByteChannel dest;
		File outputFile = new File("data/scatter_gather.txt");
		if (! outputFile.exists()) outputFile.createNewFile();

		FileOutputStream fos = new FileOutputStream(outputFile);
		dest = (GatheringByteChannel) Channels.newChannel(fos);
		byteBuffers[0] = byteBuffer1;
		byteBuffers[1] = byteBuffer2;

		dest.write(byteBuffers);
		
	}
}
