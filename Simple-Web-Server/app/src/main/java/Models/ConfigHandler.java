/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author lenovo
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;

public class ConfigHandler {
    private static final String PORT_KEY = "port";
    private static final String WEB_DIR_KEY = "webDir";
    private static final String LOG_DIR_KEY = "logDir";
    
    public static void savePreferences(int port, String webDir, String logDir) {
        Preferences prefs = Preferences.userNodeForPackage(ConfigHandler.class);
        prefs.putInt(PORT_KEY, port);
        prefs.put(WEB_DIR_KEY, webDir);
        prefs.put(LOG_DIR_KEY, logDir);
    }
    
    public static int getPortPreference() {
        Preferences prefs = Preferences.userNodeForPackage(ConfigHandler.class);
        return prefs.getInt(PORT_KEY, 8080); // Default port is 8080
    }
    
    public static String getWebDirPreference() {
        Preferences prefs = Preferences.userNodeForPackage(ConfigHandler.class);
        return prefs.get(WEB_DIR_KEY, "D://"); // Default web directory is D://
    }
    
    public static String getLogDirPreference() {
        Preferences prefs = Preferences.userNodeForPackage(ConfigHandler.class);
        return prefs.get(LOG_DIR_KEY, "D://webserver/logs"); // Default log directory is D://webserver/logs
    }
}