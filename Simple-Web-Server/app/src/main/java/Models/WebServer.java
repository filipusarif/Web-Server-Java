/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 *
 * @author lenovo
 */
public class WebServer {
    private int port;
    private String webDirectory;
    private String logDirectory;
    private HttpServer server;
    private boolean running;

    public WebServer(int port, String webDirectory, String logDirectory) {
        this.port = port;
        this.webDirectory = webDirectory;
        this.logDirectory = logDirectory;
    }

    public void start() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new RequestHandler(webDirectory, logDirectory));
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();
            running = true;
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void stop(){
        if (server != null) {
            server.stop(0); // Stop the server immediately
            running = false;
            System.out.println("Server stop on port " + port);
        }
    }
    
    
}
