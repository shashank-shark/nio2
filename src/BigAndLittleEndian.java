import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class BigAndLittleEndian {

	public static void main(String[] args) {
		
		System.out.println("Default Byte Order is :  " + ByteOrder.nativeOrder());
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
		byteBuffer.putInt(100);
		byte[] result = byteBuffer.array();
		System.out.println("BIG_ENDIAN Order : " + Arrays.toString(result));
		
		byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putInt(100);
		result = byteBuffer.array();
		System.out.println("LITTLE_ENDIAN Order : " + Arrays.toString(result));
	}
}
