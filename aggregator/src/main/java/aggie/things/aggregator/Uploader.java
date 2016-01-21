package aggie.things.aggregator;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import common.DataItem;
import common.PortInfo;

/*
 * The uploader is a single thread running in parallel with Aggregator.
 * It uploads what in the buffer as soon as possible.
 */

public class Uploader implements Runnable {

	//uploadBuffer refers to the buffer in the Aggregator.
	BlockingQueue<DataItem> uploadBuffer;

	public Uploader(BlockingQueue<DataItem> uploadBuffer) {
		this.uploadBuffer = uploadBuffer;
	}

	public void run() {
		//Upload one DataItem at a time.
		while (true) {
			try {
				//take() would block with nothing to take.
				DataItem item = uploadBuffer.take();
				Socket socketClient = new Socket(PortInfo.getAddress(),
						PortInfo.getServerPort());
				ObjectOutputStream outputStream = new ObjectOutputStream(
						socketClient.getOutputStream());

				outputStream.writeObject(item);
				// null indicating the end of the data
				outputStream.writeObject(null);

				outputStream.close();
				socketClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}