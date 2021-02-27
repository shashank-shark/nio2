package channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class BasicInAndOutChannels {
	
	public static void main(String[] args) throws IOException {
		
		ReadableByteChannel src = Channels.newChannel(System.in);
		WritableByteChannel dest = Channels.newChannel(System.out);
		
		try {
			copy(src, dest);
		} catch (IOException e) {
			System.out.println("I/O error : " + e.getMessage());
		} finally {
			src.close();
			dest.close();
		}
		
	}
	
	public static void copy (ReadableByteChannel src, WritableByteChannel dest) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
		
		/**
		 * Compaction ensures that unwritten
			buffer content isn’t overwritten during the next read
			operation.
			
			Consider the buffer of integers : [10 (pos), 20, 30, 40, 50, 60, 70 (limit)].
			Now we only write the first 4 : "10 20 30 40" which leaves the buffer as [~, ~, ~, ~, 50 (pos), 60, 70 (limit)].
			If we try to add some more integrs into the buffer say [12, 34, 3 , 121, 34] the 50 (pos) gets overwritten as [12, 34, 3, 121, 34, 60, 70].
			Here 50 is lost. To avoid this we use the method compact.
		 */
		
		while (src.read(buffer) != -1) {
			buffer.flip();
			dest.write(buffer);
			buffer.compact();
		}
		
		buffer.flip();
		
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
	
	public static void copyAlt(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
		
		while (src.read(buffer) != -1) {
			
			while (buffer.hasRemaining()) {
				dest.write(buffer);
			}
		}
	}
}
