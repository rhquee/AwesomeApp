package pl.kfrak.awesomeapp.models;

import java.io.File;

/**
 * Created by RENT on 2017-07-25.
 */

public class FileItem {

    String name;
    long lastModified;
    long fileSizeInBytes;
    boolean isDirectory;
    String path;

    public FileItem(File currentFile) {
        name = currentFile.getName();
        lastModified = currentFile.lastModified();
        fileSizeInBytes = currentFile.length();
        isDirectory = currentFile.isDirectory();
        path = currentFile.getPath();
    }

    public FileItem(String parentFilePath) {
        path = parentFilePath;
        isDirectory = true;
        name = ".....";
    }

    public String getName() {
        return name;
    }

    public long getLastModified() {
        return lastModified;
    }

    public long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public String getPath() {
        return path;
    }
}