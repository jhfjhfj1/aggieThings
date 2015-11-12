package server;
import handler.AggregatorHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import common.PortInfo;

/*
 * It is a socket server which creates threads to handle each client request.
 */

public class Server {
	void start() {
		ServerSocket server;
		Socket socket;

		try {
			server = new ServerSocket(PortInfo.getServerPort());
			int handlerCount = 0;

			// Handle one sensor in a separate thread at a time.
			while (true) {
				socket = server.accept();
				AggregatorHandler handler = new AggregatorHandler(socket, handlerCount++);
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
