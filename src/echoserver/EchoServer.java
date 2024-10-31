package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013;
	public static final int THREADPOOL_SIZE = 10;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		ExecutorService threadpool = Executors.newFixedThreadPool(THREADPOOL_SIZE);
		while (true) {
			Socket socket = serverSocket.accept();
			//ExecutorService threadpool = Executors.newFixedThreadPool(THREADPOOL_SIZE);

			ClientHandler clientHandler = new ClientHandler(socket);
			//Thread thread = new Thread(clientHandler);
			//thread.start();
			threadpool.submit(clientHandler);
		}
	}

	private static class ClientHandler implements Runnable {
		private final Socket socket;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try (InputStream input = socket.getInputStream();
				OutputStream output = socket.getOutputStream()) {
				byte[] buffer = new byte[1024]; // added a buffer and commented out the flush
				int nextByte;
				while ((nextByte = input.read(buffer)) != -1) {
					output.write(buffer, 0, nextByte);
				}
				//output.flush();
				 } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
