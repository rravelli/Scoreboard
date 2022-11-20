/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obsplugin.scoreboard;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author Rémi
 */
public class ConfigManager {
    private static final String PATH = "config.properties";
    private static final String[] PROPERTIES_NAME = new String[] {"periodDuration","teamAName","teamAColor","teamBName","teamBColor"};
    
    public static void saveConfig(String key, String value) throws IOException {
        Properties properties = new Properties();
        Boolean save = false;
        try (InputStream in = new FileInputStream(PATH)) {
            properties.load(in);
        } catch (Exception e){
            save = true;
        }
        properties.setProperty(key, value);
        FileOutputStream out = new FileOutputStream(PATH);
        properties.store(out, null);
        out.close();
    }
    
    public static void initConfig() throws IOException {
        Properties properties = new Properties(); 
        FileOutputStream out = new FileOutputStream(PATH);
        properties.store(out, null);
        out.close();
    }
    
    public static String loadConfig(String key) throws IOException {
        Properties properties = new Properties();
        String value;
        InputStream in = new FileInputStream(PATH);
        properties.load(in);
        value = properties.getProperty(key);
        return value;
    }
}
