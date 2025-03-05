/**
 * @author Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.takeit.entity;

/**
 * Class for storing file data
 */
public class TakeItFile {

    public String fileName;
    public String fileExtension;
    public String fileCreationDate;
    public String fileAbsolutePath;

    /**
     * Constructor for the TakeItFile class.
     * @param fileName the name of the file
     * @param fileExtension the extension of the file
     * @param fileCreationDate the creation date of the file
     * @param fileAbsolutePath the absolute path of the file
     */
    public TakeItFile(String fileName, String fileExtension, String fileCreationDate, String fileAbsolutePath){
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileCreationDate = fileCreationDate;
        this.fileAbsolutePath = fileAbsolutePath;
    }
    
}
