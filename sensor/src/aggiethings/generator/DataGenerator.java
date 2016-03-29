package aggiethings.generator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import aggiethings.sensor.Sensor;
import aggiethings.sensor.SensorConfig;
import common.PortInfo;
import common.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * It creates and controls several sensors to generate and send data using socket.
 * @author Haifeng Jin
 */
public class DataGenerator {
	ArrayList<Sensor> sensor;
	int sensorNum;
	HashMap<String, SensorConfig> configHash;

	/**
	 * Creating all the sensors as required.
	 * @param configFileUrl
	 * @param aggregatorAddress
	 */
	public DataGenerator(String configFileUrl, String aggregatorAddress) {
		try {
			JSONObject json = JsonReader.readJsonFromUrl(configFileUrl);

			// Reading sensor types
			JSONArray jsonConfigArray = json.getJSONArray("sensorType");
			configHash = new HashMap<String, SensorConfig>();
			for (int i = 0; i < jsonConfigArray.length(); i++) {
				configHash.put(jsonConfigArray.getJSONObject(i).getString("name"),
						new SensorConfig(jsonConfigArray.getJSONObject(i)));
			}

			// Reading sensors
			JSONArray jsonSensorArray = json.getJSONArray("sensor");
			sensor = new ArrayList<Sensor>();
			for (int i = 0; i < jsonSensorArray.length(); i++) {
				JSONObject jsonSensorObject = jsonSensorArray.getJSONObject(i);
				SensorConfig config = configHash.get(jsonSensorObject.get("type"));
				for (int j = 0; j < jsonSensorObject.getInt("quantity"); j++) {
					sensor.add(new Sensor(config, aggregatorAddress));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

/*	protected Sensor createSensor(SensorConfig config) {
		return new Sensor(config, PortInfo.getAggregatorAddress());
	}
*/
	/**
	 * Start each sensor as a separate thread.
	 */
	public void start() {
		for (Sensor ins : sensor) {
			ins.start();
		}
	}
	
	public void stop() {
		for (Sensor ins : sensor) {
			ins.stop();
		}
	}

	public static void main(String[] args) throws IOException {
		DataGenerator dataGenerator = new DataGenerator(args[0], PortInfo.getAggregatorAddress(0));
		dataGenerator.start();
		System.in.read();
		dataGenerator.stop();
	}
}
