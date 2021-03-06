package aggiethings.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * JsonReader reads json objects from varies sources.
 * It uses a library of json objects in java-json.jar
 */

public class JsonReader {

	private static String readAll(Reader rd) {
		StringBuilder sb = new StringBuilder();
		int cp;
		try {
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) {
		InputStream is = null;
		JSONObject json = null;
		try {
			is = new URL(url).openStream();
			json = readJsonFromInputStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONObject readJsonFromFile(String path) {
		InputStream is = null;
		JSONObject json = null;
		
		try {
			is = new FileInputStream(path);
			json = readJsonFromInputStream(is);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static JSONObject readJsonFromInputStream(InputStream is) {
		JSONObject json = null;
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONObject(jsonText);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
