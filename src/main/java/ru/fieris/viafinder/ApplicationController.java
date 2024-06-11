package ru.fieris.viafinder;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView<ExcelRow> onlyInFirstTable;
    @FXML
    private TableView<ExcelRow> onlyInSecondTable;

    @FXML
    private TextField firstTableCounter;

    @FXML
    private TextField secondTableCounter;

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

    /**
     * Открывает эксель документ и делает всю необходимую работу
     * @param file ссылка на эксель файл
     * @param tableNumber0_1 значение 0 - 1, номер таблицы из которого вызывается команда, например:
     *                      0 для первой таблицы или 1 для второй
     */
    private void openFileLogic(File file, int tableNumber0_1){
        ExcelFileProcessor excelFileProcessor = new ExcelFileProcessor(file);


        //Очистка таблиц и сохранение листа с виа и вва в переменную
        if (tableNumber0_1 == 0) {
            onlyInFirstTable.getColumns().clear();
            firstList = excelFileProcessor.getvIAandVVARowList();
        } else {
            onlyInSecondTable.getColumns().clear();
            secondList = excelFileProcessor.getvIAandVVARowList();
        }

        //заполнение таблицы рядами
        LinkedList<String> titles = excelFileProcessor.getTitleCellList();

        TableColumn<ExcelRow, String> magazinColumn = new TableColumn<>(titles.getFirst());
        magazinColumn.setCellValueFactory(new PropertyValueFactory<>("magazin"));

        TableColumn<ExcelRow, String> naSkladeColumn = new TableColumn<>(titles.get(1));
        naSkladeColumn.setCellValueFactory(new PropertyValueFactory<>("na_sklade"));

        TableColumn<ExcelRow, String> prodanoColumn = new TableColumn<>(titles.get(2));
        prodanoColumn.setCellValueFactory(new PropertyValueFactory<>("prodano"));

        TableColumn<ExcelRow, String> articulColumn = new TableColumn<>(titles.get(3));
        articulColumn.setCellValueFactory(new PropertyValueFactory<>("articul"));

        TableColumn<ExcelRow, String> naimenovanieColumn = new TableColumn<>(titles.get(4));
        naimenovanieColumn.setCellValueFactory(new PropertyValueFactory<>("naimenovanie"));

        TableColumn<ExcelRow, String> proizvoditelColumn = new TableColumn<>(titles.get(5));
        proizvoditelColumn.setCellValueFactory(new PropertyValueFactory<>("proizvoditel"));

        TableColumn<ExcelRow,String> massaColumn = new TableColumn<>(titles.get(6));
        massaColumn.setCellValueFactory(new PropertyValueFactory<>("massa"));

        TableColumn<ExcelRow, String> shtrihKodColumn = new TableColumn<>(titles.get(7));
        shtrihKodColumn.setCellValueFactory(new PropertyValueFactory<>("shtrih_kod"));

        TableColumn<ExcelRow, String> poMatriceColumn = new TableColumn<>(titles.get(8));
        poMatriceColumn.setCellValueFactory(new PropertyValueFactory<>("po_matrice"));



        //Добавление всех выше созданных столбцов в сами таблицы
        if(tableNumber0_1 == 0){
            onlyInFirstTable.getColumns().addAll(magazinColumn,naSkladeColumn,prodanoColumn,articulColumn,naimenovanieColumn,
                    proizvoditelColumn, massaColumn, shtrihKodColumn, poMatriceColumn);
        } else {
            onlyInSecondTable.getColumns().addAll(magazinColumn,naSkladeColumn,prodanoColumn,articulColumn,naimenovanieColumn,
                    proizvoditelColumn, massaColumn, shtrihKodColumn, poMatriceColumn);
        }

    }
    @FXML
    private void firstTableFileOpenButton(){
        file1 =  fileChooser.showOpenDialog(Application.getMainStage());
        //Если файл не выбран, выход из метода
        if(Objects.isNull(file1)){
            return;
        }
        openFileLogic(file1, 0);
    }
    @FXML
    private void secondTableFileOpenButton(){
        file2 =  fileChooser.showOpenDialog(Application.getMainStage());
        //Если файл не выбран, выход из метода
        if(Objects.isNull(file2)){
            return;
        }
        openFileLogic(file2,1);
    }

    /**
     * Сравнивает две таблицы друг с другом и сохраняет в onlyInFirst и onlyInSecond артикулы.
     * Также заполняет сами таблицы данными
     */
    @FXML
    private void compare(){
        //Очистка итемов из таблиц, на случай повторного сравнения
        onlyInFirstTable.getItems().clear();
        onlyInSecondTable.getItems().clear();


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

        //Заполняет первую таблицу данными
        int counter = 0;
        for(ExcelRow excelRow : firstList){
            for(String string : onlyInFirst){
                if(excelRow.getArticul().equals(string)){
                    onlyInFirstTable.getItems().add(excelRow);
                    counter++;
                    firstTableCounter.setText(String.valueOf(counter));
                }
            }
        }

        //Заполняет вторую таблицу данными
        counter = 0;
        for(ExcelRow excelRow : secondList){
            for(String string : onlyInSecond){
                if(excelRow.getArticul().equals(string)){
                    onlyInSecondTable.getItems().add(excelRow);
                    counter++;
                    secondTableCounter.setText(String.valueOf(counter));
                }
            }
        }

    }

    @FXML
    private void copyFirstTableArticles(){
        copyArticles(0);
    }

    @FXML
    private  void copySecondTableArticles(){
        copyArticles(1);
    }

    /**
     * Логика копирования
     * @param tableNumber0_1 номер таблицы 0 или 1
     */
    private void copyArticles(int tableNumber0_1){
        ClipboardContent clipboardContent = new ClipboardContent();
        StringBuilder stringBuilder = new StringBuilder();
        if(tableNumber0_1 == 0){
            for(ExcelRow row : onlyInFirstTable.getItems()){
                stringBuilder.append(row.getArticul());
                stringBuilder.append(" ");
            }
        } else {
            for(ExcelRow row : onlyInSecondTable.getItems()){
                stringBuilder.append(row.getArticul());
                stringBuilder.append(" ");
            }
        }

        clipboardContent.putString(stringBuilder.toString());
        clipboard.setContent(clipboardContent);
    }

}