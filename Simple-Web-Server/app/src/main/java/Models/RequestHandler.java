/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author lenovo
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class RequestHandler implements HttpHandler {
    private String webDirectory;
    private String logDirectory;

    public RequestHandler(String webDirectory, String logDirectory) {
        this.webDirectory = webDirectory;
        this.logDirectory = logDirectory;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String requestPath = exchange.getRequestURI().getPath();

        // Log access
        logAccess(exchange.getRemoteAddress().getAddress().getHostAddress(), requestPath);

        // Handle GET requests
        if (requestMethod.equalsIgnoreCase("GET")) {
            handleGetRequest(exchange, requestPath);
        } else {
            // Handle other request methods (POST, PUT, etc.)
            sendErrorResponse(exchange, HttpURLConnection.HTTP_BAD_METHOD, "Method Not Allowed");
        }
    }

    private void handleGetRequest(HttpExchange exchange, String requestPath) throws IOException {
        Path filePath = Paths.get(webDirectory, requestPath.substring(1));
        File file = filePath.toFile();

        if (file.exists() && file.isFile()) {
            // Send file content
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, file.length());
            OutputStream outputStream = exchange.getResponseBody();
            Files.copy(file.toPath(), outputStream);
            outputStream.close();
        } else if (file.exists() && file.isDirectory()) {
            // List files in directory
            File[] files = file.listFiles();
            StringBuilder response = new StringBuilder();
            response.append("<html><body><h1>Index of ").append(requestPath).append("</h1><ul>");
            // If requestPath is not root, append a link to go up one level
            if (!requestPath.equals("/")) {
                response.append("<li><a href=\"../\">.. (Up one level)</a></li>");
            }
            for (File f : files) {
                // Determine the link path based on whether it's a directory or a file
                String linkPath = f.isDirectory() ? requestPath + f.getName() + "/" : requestPath + f.getName();
                response.append("<li><a href=\"").append(linkPath).append("\">").append(f.getName()).append("</a></li>");
            }
            response.append("</ul></body></html>");
            sendResponse(exchange, HttpURLConnection.HTTP_OK, response.toString());
        } else {
            // File not found
            sendErrorResponse(exchange, HttpURLConnection.HTTP_NOT_FOUND, "Not Found");
        }
    }
    

    private void logAccess(String ipAddress, String requestPath) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String logFileName = dateFormat.format(new Date()) + ".log";
        String logFilePath = Paths.get(logDirectory, logFileName).toString();

        try {
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            String logEntry = String.format("[%s] %s - %s\n", new Date(), ipAddress, requestPath);
            Files.write(Paths.get(logFilePath), logEntry.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        sendResponse(exchange, statusCode, message);
    }
}

