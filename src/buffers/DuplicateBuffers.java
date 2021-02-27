package buffers;

import java.nio.IntBuffer;

public class DuplicateBuffers {
	
	public static void main(String[] args) {
		
		
		int[] integerArray = new int[]{10,20,30,40,50};
		IntBuffer mainIntBuffer = IntBuffer.wrap(integerArray);
		System.out.println("Order : " + mainIntBuffer.order());
		
		IntBuffer secondBuffer = mainIntBuffer.duplicate();
		IntBuffer thirdBuffer = mainIntBuffer.duplicate();
		
		System.out.println("Setting position of secondBuffer to 2 : " + secondBuffer.position(2));
		System.out.println("The thirdBuffer is : " + thirdBuffer);
		
	}
}
