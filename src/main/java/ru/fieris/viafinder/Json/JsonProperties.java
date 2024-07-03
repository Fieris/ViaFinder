package ru.fieris.viafinder.Json;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.fieris.viafinder.Application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonProperties {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File file = new File(Application.getProgramDirectory() + "\\ViaFinderProperties.json");
    private PropertiesPOJO propertiesPOJO;

    public JsonProperties(){
        //Создание файла
        try{
            if(file.exists()){
                System.out.println("файл существует");
            } else {
                System.out.println("файл не существует");
                boolean isFileCreated = file.createNewFile();
                if(isFileCreated){
                    System.out.println("файл успешно создан");
                    PropertiesPOJO propertiesPOJO = new PropertiesPOJO();
                    objectMapper.writeValue(file, propertiesPOJO);
                } else {
                    System.out.println("Ошибка создания файла " + file.getName());
                    throw new IOException();
                }
            }
        } catch (IOException exc){
            System.out.println("Ошибка создания файла:\r" + exc);
        }
        ////////////////////////////
        try{
            propertiesPOJO = objectMapper.readValue(file, PropertiesPOJO.class);
        } catch (Exception exc){
            exc.printStackTrace();
        }


    }

    public ArrayList<File> getReadOnlyRecentFiles(){
        return  propertiesPOJO.getRecentFiles();
    }

    public void addRecentFile(File recentFile){
        ArrayList<File> recentFiles = propertiesPOJO.getRecentFiles();
        if(recentFiles.contains(recentFile)){
            recentFiles.remove(recentFile);
        }

        recentFiles.addFirst(recentFile);
        propertiesPOJO.setRecentFiles(recentFiles);
        try {
            objectMapper.writeValue(file, propertiesPOJO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeRecentFile(File recentFile){
        ArrayList<File> recentFiles = propertiesPOJO.getRecentFiles();
        recentFiles.remove(recentFile);
        propertiesPOJO.setRecentFiles(recentFiles);
        try{
            objectMapper.writeValue(file,propertiesPOJO);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void clearRecentFiles(){
        ArrayList<File> recentFiles = propertiesPOJO.getRecentFiles();
        recentFiles.clear();
        propertiesPOJO.setRecentFiles(recentFiles);
        try{
            objectMapper.writeValue(file,propertiesPOJO);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


}
