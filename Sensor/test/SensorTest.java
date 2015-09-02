
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;
public class SensorTest {

	/*
	 * Test the data send by one sensor using a ClientHandler.
	 */
	@Test
	public void testRun() {
		SensorConfig config = new SensorConfig(5, 500, 10);
		Sensor sensor = new Sensor(config, PortInfo.getAddress());
		ServerSocket server;
		Socket socket;

		try {
			server = new ServerSocket(PortInfo.getPort());
			sensor.start();
			socket = server.accept();

			ClientHandler handler = new ClientHandler(config, socket);
			//The assertions are in the thread.
			new Thread(handler).start();
			//Wait for the thread to end.
			Thread.sleep(config.itemNum * config.intermissionLength * 2);

			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
