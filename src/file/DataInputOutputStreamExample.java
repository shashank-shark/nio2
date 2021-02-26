package file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataInputOutputStreamExample {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		try (FileOutputStream fos = new FileOutputStream(new File("data/simple.txt"));
			 DataOutputStream dos = new DataOutputStream(fos)) {
			
			dos.writeUTF("Hello Shashank");
			dos.writeFloat(1.2F);
			dos.writeBytes("Written in Bytes");
			dos.writeUTF("Simple World");
		}
		
		try (FileInputStream fis = new FileInputStream(new File("data/simple.txt"));
				DataInputStream dis = new DataInputStream(fis)) {
			System.out.println(dis.readUTF());
			System.out.println(dis.readFloat());
			
			int _byte;
			
			try {
				while ( (dis.available() > 0) && (_byte = dis.readByte()) != -1) {
					System.out.print((char) _byte);
				}
				
			} catch (EOFException e) {
				System.out.println();
			}
			
			
		}
	}
}
