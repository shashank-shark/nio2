package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;


public class EncryptionMapperExample {
	
	public static void main(String[] args) throws IOException {
		
		File outputFile = new File("data/enc.txt");
		if (! outputFile.exists()) outputFile.createNewFile();
		FileInputStream fis = new FileInputStream(new File("data/patrons.ldif"));
		FileChannel fc = fis.getChannel();
		FileOutputStream fos = new FileOutputStream(outputFile);
		int[] map = new Random().ints(256, 0, 256).toArray();
		
		SimpleEncryptionMapper sEncyMapper = new SimpleEncryptionMapper(fos, map);
		int _byte;
		ByteBuffer bf = ByteBuffer.allocate(4); 
		
		while ((_byte = fis.read()) > -1) {
			sEncyMapper.write(_byte, false);
		}
		
		fis.close();
		fos.close();
		sEncyMapper.close();
		
	}
}
