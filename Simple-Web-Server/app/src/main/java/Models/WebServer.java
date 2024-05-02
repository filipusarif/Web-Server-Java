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
import javax.swing.JTextArea;


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
    private JTextArea textArea;
    private LogHandler logHandle;

    public WebServer(int port, String webDirectory, String logDirectory, JTextArea textArea) {
        this.port = port;
        this.webDirectory = webDirectory;
        this.logDirectory = logDirectory;
        this.textArea = textArea;
        this.logHandle = new LogHandler(logDirectory);
    }

    public void start() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new RequestHandler(webDirectory, logDirectory,textArea));
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();
            running = true;
            String logEntry = "Status change detected : server start on port "+port;
            logHandle.log(textArea,logEntry);
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
            String logEntry = "Status change detected : server shutdown on port "+port;
            logHandle.log(textArea,logEntry);
        }
    }
    
    
}