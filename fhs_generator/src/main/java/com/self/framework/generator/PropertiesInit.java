package com.self.framework.generator;

import java.io.IOException;
import java.util.Properties;

public class PropertiesInit {
    public static Properties properties;

    public PropertiesInit(){
        properties = new Properties();
        try {
            properties.load( getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object obtainValue(String key){
        return properties.getProperty(key);
    }
}
