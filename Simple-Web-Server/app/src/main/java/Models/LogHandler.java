/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;

/**
 *
 * @author lenovo
 */

public class LogHandler {
    private String logDirectory;
    
    public LogHandler(String logDirectory){
        this.logDirectory = logDirectory;
    }
    
    public void log(JTextArea textArea, String requestPath){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String logFileName = dateFormat.format(new Date()) + ".log";
        String logFilePath = Paths.get(logDirectory, logFileName).toString();
        
        try {
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            String logEntry = String.format("[%s] - %s\n", new Date(), requestPath);
            textArea.append(logEntry);
            Files.write(Paths.get(logFilePath), logEntry.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            System.out.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void log(JTextArea textArea, String requestPath, String ipAddress){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String logFileName = dateFormat.format(new Date()) + ".log";
        String logFilePath = Paths.get(logDirectory, logFileName).toString();
        
        try {
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            String logEntry = String.format("[%s] %s - %s\n", new Date(), ipAddress, requestPath);
            textArea.append(logEntry);
            Files.write(Paths.get(logFilePath), logEntry.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            System.out.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
