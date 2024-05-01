/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String logFileName = dateFormat.format(new Date()) + ".log";
            String logFilePath = Paths.get(logDirectory, logFileName).toString();
            try {
            File logFile = new File(logFilePath);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }
                String logEntry = String.format("[%s] %s - %s\n", new Date(),":0:0:0:0:0:0:1", "Status change detected : server start on port "+port);
                Files.write(Paths.get(logFilePath), logEntry.getBytes(), java.nio.file.StandardOpenOption.APPEND);
                System.out.println(logEntry);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String logFileName = dateFormat.format(new Date()) + ".log";
            String logFilePath = Paths.get(logDirectory, logFileName).toString();
            try {
            File logFile = new File(logFilePath);
                if (!logFile.exists()) {
                    logFile.createNewFile();
                }

                String logEntry = String.format("[%s] %s - %s\n", new Date(),"192.168.1.1", "Status change detected : server shutdown on port "+port);
                Files.write(Paths.get(logFilePath), logEntry.getBytes(), java.nio.file.StandardOpenOption.APPEND);
                System.out.println(logEntry);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
}