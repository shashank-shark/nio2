package file;

public class IOUtils {
	
	
	public static 
	byte[] toByteArray(int value) {
	    return new byte[] { (byte)(value >> 24),  (byte)(value >> 16), (byte)(value >> 8),  (byte)(value) };
	}

}
