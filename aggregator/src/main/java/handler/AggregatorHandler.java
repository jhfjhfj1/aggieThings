package handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import common.DataItem;

/*
 * It handles all the data from one Aggregator.
 * It runs as a separate thread.
 */
public class AggregatorHandler extends Handler {
	

	public AggregatorHandler(Socket client, int id) {
		super(client, id);
	}


	void handle(ObjectInputStream input) {
		// One item at a time.
		while (true) {
			DataItem dataItem;
			try {
				dataItem = (DataItem) input.readObject();
				if (dataItem == null) {
					return;
				}
				output(id, dataItem);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static synchronized void output(int id, DataItem dataItem) {
		System.out.print("On Cloud: Handler " + id + " received ");
		System.out.println(dataItem.getData().length + " bytes at "
				+ dataItem.getTimestamp() + ".");
	}
}
