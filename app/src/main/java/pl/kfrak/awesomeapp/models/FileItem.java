package pl.kfrak.awesomeapp.models;

import java.io.File;

/**
 * Created by RENT on 2017-07-25.
 */

public class FileItem {

    private String name;
    private long lastModified;
    private long fileSizeInBytes;
    private boolean directory;
    private String path;

    public FileItem(String parentFilePath) {
        path = parentFilePath;
        directory = true;
        name = "..";
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
        return directory;
    }

    public String getPath() {
        return path;
    }

    public FileItem(File currentFile) {
        name = currentFile.getName();
        lastModified = currentFile.lastModified();
        fileSizeInBytes = currentFile.length();
        directory = currentFile.isDirectory();
        path = currentFile.getPath();

    }
}
