package encryption;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class SimpleEncryptionMapper extends FilterOutputStream {
	
	private int[] map;

	public SimpleEncryptionMapper(OutputStream out, int[] map) {
		super(out);
		if (map == null)
			throw new NullPointerException("mapper cannot be null");
		if (map.length != 256)
			throw new IllegalArgumentException("mapper length should be 256");
		
		this.map = map;
	}
	
	public void write(int b, boolean forSignature) throws IOException {
		write(map[b]);
	}
}
