package aggiethings.config;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import aggiethings.tools.AddressManager;
import aggiethings.tools.AggregatorIdManager;
import aggiethings.tools.TimeManager;
import aggiethings.common.PortInfo;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	final static String addressFolderPath = "src/main/resources/";
	public final static String BASE_URI = PortInfo.baseURI;
	private static HttpServer server;
	static AddressManager addressManager;
	public static AggregatorIdManager aggregatorIdManager;
	public static TimeManager timeManager;

	public static void start() {
		server = startServer();
	}

	public static HttpServer startServer() {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in aggiethings.aggregator package
		final ResourceConfig rc = new ResourceConfig().packages("aggiethings.config");
		addressManager = new AddressManager(addressFolderPath);
		aggregatorIdManager = new AggregatorIdManager();

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}


	public static void stop() {
		server.shutdown();
	}

	public static void main(String[] args) throws IOException {
		start();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		stop();
	}
}
