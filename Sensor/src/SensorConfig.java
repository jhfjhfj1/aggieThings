/*
 * It stores the configuration information for a sensor to generate data.
 */

public class SensorConfig {
	// Number of bytes in each item.
	public int byteNum;
	// The length of the intermission between items transference in milliseconds.
	public int intermissionLength;
	// The total number of items need to be send.
	public int itemNum;

	SensorConfig(int byteNum, int intervalLength, int itemNum) {
		this.byteNum = byteNum;
		this.intermissionLength = intervalLength;
		this.itemNum = itemNum;
	}

}
