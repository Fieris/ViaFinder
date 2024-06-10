package ru.fieris.viafinder;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import ru.fieris.viafinder.Excel.ExcelFileProcessor;
import ru.fieris.viafinder.Excel.ExcelRow;

import java.io.File;
import java.util.*;

public class ApplicationController {
    private final FileChooser fileChooser;
    private File file1;
    private File file2;
    private LinkedList<ExcelRow> firstList = new LinkedList<>();
    private LinkedList<ExcelRow> secondList = new LinkedList<>();
    private  Clipboard clipboard;


    @FXML
    private ListView<String> newArticles;
    @FXML
    private ListView<String> deletedArticles;

    @FXML
    private TableView<ExcelRow> newArticlesTable;
    @FXML
    private TableView<ExcelRow> deletedArticlesTable;

    public ApplicationController(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel files", "*.xlsx");
        fileChooser.getExtensionFilters().addFirst(extensionFilter);

        clipboard = Clipboard.getSystemClipboard();
        initializeTableViews();
    }

    /**
     * Инициализирует tableView
     */
    private void initializeTableViews(){

    }
    @FXML
    private void openFile1MenuButton(){
        file1 =  fileChooser.showOpenDialog(Application.getMainStage());
        //Если файл не выбран, выход из метода
        if(Objects.isNull(file1)){
            return;
        }

        ExcelFileProcessor excelFileProcessor = new ExcelFileProcessor(file1);
        firstList = excelFileProcessor.getvIAandVVARowList();

        //заполнение таблицы рядами
        newArticlesTable.getColumns().clear();
        LinkedList<String> titles = excelFileProcessor.getTitleCellList();

        TableColumn<ExcelRow, String> magazinColumn = new TableColumn<>(titles.getFirst());
        magazinColumn.setCellValueFactory(new PropertyValueFactory<>("magazin"));

        TableColumn<ExcelRow, String> naSkladeColumn = new TableColumn<>(titles.get(1));
        naSkladeColumn.setCellValueFactory(new PropertyValueFactory<>("na_sklade"));

        TableColumn<ExcelRow, String> prodanoColumn = new TableColumn<>(titles.get(2));
        prodanoColumn.setCellValueFactory(new PropertyValueFactory<>("prodano"));

        //TODO Закончить тут


        newArticlesTable.getColumns().addAll(magazinColumn,naSkladeColumn,prodanoColumn);
    }
    @FXML
    private void openFile2MenuButton(){
        file2 =  fileChooser.showOpenDialog(Application.getMainStage());
        //Если файл не выбран, выход из метода
        if(Objects.isNull(file2)){
            return;
        }

        ExcelFileProcessor excelFileProcessor = new ExcelFileProcessor(file2);
        secondList = excelFileProcessor.getvIAandVVARowList();

        //заполнение таблицы рядами
        deletedArticlesTable.getColumns().clear();
        for(String string : excelFileProcessor.getTitleCellList()){
            deletedArticlesTable.getColumns().add(new TableColumn<>(string));
        }
    }

    @FXML
    private void compare(){
        LinkedList<String> onlyInFirst = new LinkedList<>();
        LinkedList<String> onlyInSecond = new LinkedList<>();




        ArrayList<String> articles1 = new ArrayList<>();
        for (ExcelRow row : firstList){
            articles1.add(row.getArticul());
        }
        ArrayList<String> articles2 = new ArrayList<>();
        for(ExcelRow row : secondList){
            articles2.add(row.getArticul());
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

        newArticlesTable.getItems().addAll(firstList);


//        newArticles.getItems().clear();
//        deletedArticles.getItems().clear();
//        newArticles.getItems().addAll(onlyInSecond);
//        deletedArticles.getItems().addAll(onlyInFirst);
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