package com.jakubwawak.takeit.properties_engine;
/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */

/**
 * Object for storing .properties file fields
 */
public class PropertiesField {
    public String propertiesKey;
    public String propertiesValue;

    /**
     * Constructor
     * @param key
     * @param value
     */
    public PropertiesField(String key, String value){
        this.propertiesKey = key;
        this.propertiesValue = value;
    }
}