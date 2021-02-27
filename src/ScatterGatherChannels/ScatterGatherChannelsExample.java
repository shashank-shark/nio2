package ScatterGatherChannels;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ScatteringByteChannel;

public class ScatterGatherChannelsExample {
	
	public static void main(String[] args) throws IOException {
		
		ScatteringByteChannel src;
		
		FileInputStream fis = new FileInputStream("data/simple.txt");
		src = (ScatteringByteChannel) Channels.newChannel(fis);
		
		ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(5);
		ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(3);
		
		ByteBuffer[] byteBuffers = {byteBuffer1, byteBuffer2};
		src.read(byteBuffers);
		
		/**
		 * Now the file is read into these two buffers namely:
		 * - buffer1
		 * - buffer2
		 */
		byteBuffer1.flip();
		while (byteBuffer1.hasRemaining()) {
			System.out.print((char) byteBuffer1.get());
		}
		
	}
}
