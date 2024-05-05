/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;


import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
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
            String logEntry1 = "Attempting to start Web Server";
            logHandle.log(textArea,logEntry1);
            String logEntry2 = "Status change detected : server start at port "+ port;
            logHandle.log(textArea,logEntry2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isRunning() {
        return running;
    }
    
    public void stop(){
        if (server != null) {
            server.stop(0); // Stop server
            running = false;
            String logEntry1 = "Attempting to shutdown Web Server";
            logHandle.log(textArea,logEntry1);
            String logEntry2 = "Status change detected : server off at port "+ port;
            logHandle.log(textArea,logEntry2);
        }
    }
    
    
}