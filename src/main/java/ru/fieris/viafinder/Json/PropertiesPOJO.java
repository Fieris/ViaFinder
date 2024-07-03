package ru.fieris.viafinder.Json;

import java.io.File;
import java.util.ArrayList;

/**
 * POJO CLASS
 */
public class PropertiesPOJO {
    private ArrayList<File> recentFiles = new ArrayList<>();

    public ArrayList<File> getRecentFiles() {
        return recentFiles;
    }

    public void setRecentFiles(ArrayList<File> recentFiles) {
        this.recentFiles = recentFiles;
    }
}
