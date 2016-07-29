/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.fileio;

import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author Jeremy Blanchard
 */
public class FileIO {

    private File encFile, decFile;

    /**
     * This method sets the Decrypted File
     *
     * @param newFile File object to be stored
     */
    public void setDecFile(File newFile) {
        this.decFile = newFile;
    }

    /**
     * This method sets the Decrypted File based on a Path object to the file
     *
     * @param path The absolute path to the file
     */
    public void setDecFile(Path path) {
        this.decFile = new File(path.toString());
    }

    /**
     * This method sets the Encrypted File based on the file passed
     *
     * @param newFile The file object specifying the Encrypted file
     */
    public void setEncFile(File newFile) {
        this.encFile = newFile;
    }

    /**
     * This method sets the Encrypted File using a Path object
     *
     * @param path The Path object specifying the absolute path to the Encrypted
     * File
     */
    public void setEncFile(Path path) {
        this.encFile = new File(path.toString());
    }

    /**
     * This method returns the File object corresponding to the Decrypted File
     *
     * @return The Decrypted File Object
     */
    public File getDecFile() {
        return this.decFile;
    }

    /**
     * This method returns the File object corresponding to the Encrypted File
     *
     * @return The Encrypted File Object
     */
    public File getEncFile() {
        return this.encFile;
    }

    /**
     * This method clears the File objects stored in this class to prevent
     * potential modification from other sources.
     */
    public void cleanup() {
        this.decFile = null;
        this.encFile = null;
    }

}
