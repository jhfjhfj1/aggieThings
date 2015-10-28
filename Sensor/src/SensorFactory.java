


public class SensorFactory {
	public static Sensor getSensor(SensorConfig config, String sensorType) {
		if (sensorType.equals("Sensor")) {
			return new Sensor(config, PortInfo.getAddress());
		}
		if (sensorType.equals("VideoSensor")) {
			return new VideoSensor(config, PortInfo.getAddress());
		}
		if (sensorType.equals("WearableSensor")) {
			return new WearableSensor(config, PortInfo.getAddress());
		}
		if (sensorType.equals("ActuatableSensor")) {
			return new ActuatableSensor(config, PortInfo.getAddress());
		}
		return null;
		
	}
}
