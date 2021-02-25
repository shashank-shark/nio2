package file;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedStreamNumberBroadcaster {
	
	public static void main(String[] args) throws IOException {
		
		final PipedOutputStream pos = new PipedOutputStream();
		final PipedInputStream pis = new PipedInputStream(pos);
		
		Runnable broadcasterTask = () -> {
			
			for (int i = 0; i <= 100; i++) {
				try {
					pos.write((byte) i);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				pos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		};
		
		Runnable recieverTask = () -> {
			
			int b;
			try {
				while ((b = pis.read()) != -1) {
					System.out.println("Recieved from first connector [" + b + "]");
				}
			} catch (IOException e) {
				if (e.getMessage().equals("Pipe closed")) {
					System.out.println("All items are broadcasted");
				}
			}
			try {
				pis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		
		Runnable recieverTask2 = () -> {
					
					int b;
					try {
						while ((b = pis.read()) != -1) {
							System.out.println("Recieved from second connector [" + b + "]");
						}
					} catch (IOException e) {
						if (e.getMessage().equals("Pipe closed")) {
							System.out.println("All items are broadcasted");
						}
					}
					try {
						pis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
		
		Thread broadcastThread = new Thread(broadcasterTask);
		Thread recieverThread = new Thread(recieverTask);
		Thread recieverThread2 = new Thread(recieverTask2);
		
		recieverThread.start();
		broadcastThread.start();
		recieverThread2.start();
		
	}

}
