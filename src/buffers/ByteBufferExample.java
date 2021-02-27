package buffers;

import java.nio.IntBuffer;

public class ByteBufferExample {
	
	public static void main(String[] args) {
		
		int[] nonDirectByteArray = new int[10];
		IntBuffer buffer = IntBuffer.wrap(nonDirectByteArray);		
		
		if (buffer.hasArray()) {
			System.out.println("Byte array starts from : " + buffer.array());
			System.out.println("Byte array offset : " + buffer.arrayOffset());
			System.out.println("Byte array limit : " + buffer.limit());
			System.out.println("Byte array positiion : " + buffer.position());
			System.out.println("Byte array remaining : " + buffer.remaining());
		}
		
		// change content of nonDirectByteArray
		nonDirectByteArray[2] = 100;
		
		System.out.println("Element at position 2 : " + buffer.get(2));
		
	}
}
