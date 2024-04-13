package ru.fieris.viafinder;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import ru.fieris.viafinder.Excel.ExcelProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ApplicationController {
    private final FileChooser fileChooser;
    private File file1;
    private File file2;
    private ArrayList<ExcelProcessor.ExcelRow> firstList = new ArrayList<>();
    private ArrayList<ExcelProcessor.ExcelRow> secondList = new ArrayList<>();

    public ApplicationController(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel files", "*.xlsx");
        fileChooser.getExtensionFilters().add(0,extensionFilter);
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
        ArrayList<String> articles1 = new ArrayList<>();
        ArrayList<String> articles2 = new ArrayList<>();

        for(ExcelProcessor.ExcelRow row : firstList){
            articles1.add(row.getCells().get(3).toString());
        }
        for(ExcelProcessor.ExcelRow row : secondList){
            articles2.add(row.getCells().get(3).toString());
        }



    }
}