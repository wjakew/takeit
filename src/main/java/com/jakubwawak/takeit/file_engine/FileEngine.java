/**
 * @author Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.takeit.file_engine;

import java.util.ArrayList;
import java.io.File;

import com.jakubwawak.takeit.entity.TakeItFile;

/**
 * Class for managing files in the current path.
 */
public class FileEngine {

    public ArrayList<TakeItFile> filesCollection;
    public String filePath;

    /**
     * Constructor for the FileEngine class.
     * @param filePath the path to the file
     */
    public FileEngine(String filePath){
        this.filePath = filePath;
        this.filesCollection = new ArrayList<>();
    }

    /**
     * Verifies if the given filePath exists and contains any files.
     * @return true if the path exists and contains files, false otherwise
     */
    public boolean verifyPath() {
        File directory = new File(filePath);
        return directory.exists() && directory.isDirectory() && directory.listFiles() != null && directory.listFiles().length > 0;
    }

    /** 
     * Loads the files from the current path.
     */
    public void loadFiles() {
        File directory = new File(filePath);
        if (directory.exists() && directory.isDirectory()) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String fileExtension = getFileExtension(fileName);
                    String fileCreationDate = String.valueOf(file.lastModified());
                    String fileAbsolutePath = file.getAbsolutePath();
                    TakeItFile takeItFile = new TakeItFile(fileName, fileExtension, fileCreationDate, fileAbsolutePath);
                        filesCollection.add(takeItFile);
                    }
                }
            }
        }
    }

    /**
     * Gets the extension of the file.
     * @param fileName the name of the file
     * @return the extension of the file
     */
    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot > 0 && lastIndexOfDot < fileName.length() - 1) {
            return fileName.substring(lastIndexOfDot + 1);
        }
        return "";
    }
    
    
}
