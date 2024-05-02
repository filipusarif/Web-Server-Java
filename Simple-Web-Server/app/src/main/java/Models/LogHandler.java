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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
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
        textArea.setEditable(false);
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
            scrollToBottom(textArea);
            Files.write(Paths.get(logFilePath), logEntry.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            System.out.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void log(JTextArea textArea, String requestPath, String ipAddress){
        textArea.setEditable(false);
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
            scrollToBottom(textArea);
            Files.write(Paths.get(logFilePath), logEntry.getBytes(), java.nio.file.StandardOpenOption.APPEND);
            System.out.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void scrollToBottom(JTextArea textArea) {
        // Get the scroll pane that contains the text area
        JScrollPane scrollPane = (JScrollPane) textArea.getParent().getParent();
        
        // Get the vertical scroll bar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        
        // Set the value of the vertical scroll bar to its maximum
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }
    
}

    
