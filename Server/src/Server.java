import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * It is a socket server which creates threads to handle each client request.
 */

public class Server {
	void start() {
		ServerSocket server;
		Socket socket;

		try {
			server = new ServerSocket(PortInfo.getPort());
			int handlerCount = 0;

			// Handle one sensor in a separate thread at a time.
			while (true) {
				socket = server.accept();
				ClientHandler handler = new ClientHandler(socket, handlerCount++);
				new Thread(handler).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}