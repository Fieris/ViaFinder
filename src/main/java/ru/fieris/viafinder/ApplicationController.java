package ru.fieris.viafinder;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import ru.fieris.viafinder.Excel.ExcelProcessor;

import java.io.File;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ApplicationController {
    private final FileChooser fileChooser;
    private File file1;
    private File file2;
    private ArrayList<ExcelProcessor.ExcelRow> firstList = new ArrayList<>();
    private ArrayList<ExcelProcessor.ExcelRow> secondList = new ArrayList<>();
    private  Clipboard clipboard;

    @FXML
    private ListView<String> newArticles;
    @FXML
    private ListView<String> deletedArticles;

    public ApplicationController(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel files", "*.xlsx");
        fileChooser.getExtensionFilters().add(0,extensionFilter);

        clipboard = Clipboard.getSystemClipboard();
    }
    @FXML
    private void openFile1MenuButton(){
        file1 =  fileChooser.showOpenDialog(Application.getMainStage());
        //Если файл не выбран, выход из метода
        if(Objects.isNull(file1)){
            return;
        }

        ExcelProcessor excelProcessor = new ExcelProcessor(file1);
        firstList = excelProcessor.getVIAandVVARowArrayList();
    }
    @FXML
    private void openFile2MenuButton(){

        file2 =  fileChooser.showOpenDialog(Application.getMainStage());
        //Если файл не выбран, выход из метода
        if(Objects.isNull(file2)){
            return;
        }

        ExcelProcessor excelProcessor = new ExcelProcessor(file2);
        secondList = excelProcessor.getVIAandVVARowArrayList();
    }

    @FXML
    private void compare(){
        LinkedList<String> onlyInFirst = new LinkedList<>();
        LinkedList<String> onlyInSecond = new LinkedList<>();
//        for(int i = 0; i < firstList.size(); i++){
//            for(int j = 0; j < secondList.size();){
//                if(firstList.get(i).getCells().get(3).toString().equals(secondList.get(j).getCells().get(3).toString())){
//                    break;
//                } else {
//                    j++;
//                }
//                if(j == secondList.size()){
//                    onlyInFirst.add(firstList.get(i));
//                }
//            }
//        }
//        for(ExcelProcessor.ExcelRow row: onlyInFirst){
//            System.out.println(row.getCells().get(3));
//        }
        ArrayList<String> articles1 = new ArrayList<>();
        for (ExcelProcessor.ExcelRow row : firstList){
            articles1.add(row.getCells().get(3).toString());
        }
        ArrayList<String> articles2 = new ArrayList<>();
        for(ExcelProcessor.ExcelRow row : secondList){
            articles2.add(row.getCells().get(3).toString());
        }

        for(String string : articles1){
            if(!articles2.contains(string)){
                onlyInFirst.add(string);
            }
        }

        for(String string : articles2){
            if(!articles1.contains(string)){
                onlyInSecond.add(string);
            }
        }

        newArticles.getItems().addAll(onlyInSecond);
        deletedArticles.getItems().addAll(onlyInFirst);
    }

    @FXML
    private void copyNewArticles(){
        ClipboardContent clipboardContent = new ClipboardContent();
        StringBuilder stringBuilder = new StringBuilder();
        for(String string : newArticles.getItems()){
            stringBuilder.append(string);
            stringBuilder.append("\n");
        }
        clipboardContent.putString(stringBuilder.toString());
        clipboard.setContent(clipboardContent);
    }

    @FXML
    private  void copyDeletedArticles(){
        ClipboardContent clipboardContent = new ClipboardContent();
        StringBuilder stringBuilder = new StringBuilder();
        for(String string : deletedArticles.getItems()){
            stringBuilder.append(string);
            stringBuilder.append("\n");
        }
        clipboardContent.putString(stringBuilder.toString());
        clipboard.setContent(clipboardContent);
    }
}