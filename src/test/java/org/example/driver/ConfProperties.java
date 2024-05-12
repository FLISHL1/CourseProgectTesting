package org.example.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfProperties {
    protected static Properties PROPERTIES;


    static {
        try(FileInputStream fileInputStream = new FileInputStream("src/test/resources/conf.properties")){
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }
}
