package it.uniba.lacasadicenere.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;

import java.io.IOException;

/**
 * Server REST basato su Grizzly HTTP Server.
 */
public class RestServer {
    
    private HttpServer server;
    
    /**
     * Avvia il server REST sulla porta 8080.
     * @throws IOException
     */
    public void startServer() throws IOException {
        server = HttpServer.createSimpleServer("/", 8080);
        ServerConfiguration config = server.getServerConfiguration();
        
        config.addHttpHandler(new CreditsHandler(), "/api/credits");
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdownNow();
        }));

        new Thread(() -> {
            try {
                server.start();
                Thread.currentThread().join();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    /**
     * Ferma il server.
     */
    public void stopServer() {
        if (server != null) {
            server.shutdownNow();
        }
    }
}