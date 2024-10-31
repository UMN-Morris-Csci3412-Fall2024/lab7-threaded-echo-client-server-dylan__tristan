package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException, InterruptedException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		Thread inputThread = new Thread(() -> {
			try {
				int data;
				while ((data = System.in.read()) != -1) {
					socketOutputStream.write(data);
					socketOutputStream.flush();
				}
				socket.shutdownOutput(); // Signal the sever the we are done
			} catch (IOException e) {
				System.out.println("We caught an unexpected exception");
				System.err.println(e);
			}
		});

		Thread outputThread = new Thread(() -> {
			try {
				int data;
				while ((data = socketInputStream.read()) != -1) {
					System.out.write(data);
				}
				System.out.flush(); // Ensure the data is written out			
			} catch (IOException e) {
				System.out.println("We caught an unexpected exception");
				System.err.println(e);
			}
		});

		inputThread.start();
		outputThread.start();

		try {
			inputThread.join();
			outputThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}
	}
}