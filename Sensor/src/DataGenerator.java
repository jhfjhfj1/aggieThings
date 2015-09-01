import java.util.ArrayList;

/*
 * It creates and controls several sensors to generate and send data using socket.
 */
public class DataGenerator {
	ArrayList<Sensor> sensorList;
	int sensorNum;

	/*
	 * Creating all the sensors as required.
	 */
	DataGenerator(SensorConfig config, int sensorNum) {
		sensorList = new ArrayList<Sensor>();
		for (int i = 0; i < sensorNum; i++) {
			Sensor sensor = new Sensor(config, PortInfo.getAddress());
			sensorList.add(sensor);
		}
	}

	/*
	 * Start each sensor as a separate thread.
	 */
	public void start() {
		for (Sensor sensor: sensorList) {
			sensor.start();
		}
	}
}