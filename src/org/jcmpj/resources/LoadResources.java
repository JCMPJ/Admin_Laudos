/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.jcmpj.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author jcmpj
 */
public class LoadResources {

    public LoadResources() {
    }
    
    public Properties loadPropertiesFile(String filePath) {

        Properties prop = new Properties();

        try {
            // InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(filePath);
            // InputStream resourceAsStream = new FileInputStream(filePath);
            InputStream resourceAsStream = getClass().getResourceAsStream(filePath);
            prop.load(resourceAsStream);
        } catch (IOException e) {
            System.err.println("Unable to load properties file : " + filePath);
        }

        return prop;

    }
}
