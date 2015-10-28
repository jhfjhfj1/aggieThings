
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;


public class DataGeneratorTest {
	/*
	 * Test the DataGenerator.
	 * Handle each sensor using a separate thread of ClientHandler.
	 */
	@Test
	public void test() {
		SensorConfig config = new SensorConfig(10, 500, 5);
		int sensorNum = 10;
		DataGenerator generator = new DataGenerator(config, sensorNum, "Sensor");

		ServerSocket server;
		Socket socket;

		try {
			server = new ServerSocket(PortInfo.getPort());
			generator.start();
			ClientHandler[] handler = new ClientHandler[sensorNum];

			//Handle one sensor in a separate thread at a time.
			for (int i = 0; i < sensorNum; i++) {
				socket = server.accept();
				//The assertions are in the thread.
				handler[i] = new ClientHandler(config, socket);
				new Thread(handler[i]).start();
			}
			//Wait the threads to end.
			Thread.sleep(config.itemNum * config.intermissionLength * 2);

			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
