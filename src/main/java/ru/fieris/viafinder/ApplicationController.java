package ru.fieris.viafinder;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import ru.fieris.viafinder.Excel.ExcelFileProcessor;
import ru.fieris.viafinder.Excel.ExcelRow;

import java.io.*;
import java.util.*;

public class ApplicationController {
    private final FileChooser fileChooser;
    public ToggleGroup toggleGroupViaCategory;
    public ToggleGroup toggleGroupQuantityCategory;
    public Menu quantitytoggleSubMenu;
    private LinkedList<ExcelRow> firstList = new LinkedList<>();
    private LinkedList<ExcelRow> secondList = new LinkedList<>();
    private final Clipboard clipboard;

    @FXML
    private Menu VIAandVVAtoggleSubMenu;



    @FXML
    private TableView<ExcelRow> onlyInFirstTable;
    @FXML
    private TableView<ExcelRow> onlyInSecondTable;

    @FXML
    private TextField firstTableCounter;

    @FXML
    private TextField secondTableCounter;
    @FXML
    public Menu firstTableRecentFilesMenu;
    @FXML
    public Menu secondTableRecentFilesMenu;

    @FXML
    public Label firstFileNameLabel;
    @FXML
    public Label secondFileNameLabel;

    public ApplicationController(){
        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Excel files", "*.xlsx");
        fileChooser.getExtensionFilters().addFirst(extensionFilter);

        clipboard = Clipboard.getSystemClipboard();
    }
    public void initialize(){
        initializeUpdateTableViews();
        //Плейсхолдеры
        onlyInFirstTable.setPlaceholder(new Label("Нет данных"));
        onlyInSecondTable.setPlaceholder(new Label("Нет данных"));

        //Listener на toggleGroupViaCategory
        toggleGroupViaCategory.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            VIAandVVAtoggleSubMenu.setText("Тип: " + ((RadioMenuItem)newValue).getText());
            compare();
        });

        //Listener на toggleGroupQuantityCategory
        toggleGroupQuantityCategory.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            quantitytoggleSubMenu.setText("Количество: " + ((MenuItem)newValue).getText());
            compare();
        });

    }

    /**
     * Инициализирует tableView
     */
    private void initializeUpdateTableViews(){
        firstTableRecentFilesMenu.getItems().clear();
        secondTableRecentFilesMenu.getItems().clear();
        Image excelImage = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/excel 16x16.png")));
        Image unknownImage = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/unknown16x16.png")));

        firstTableRecentFilesMenu.setId("0");
        secondTableRecentFilesMenu.setId("1");

        if(getRecentFiles().isEmpty()){
            firstTableRecentFilesMenu.getItems().add(new MenuItem("Пусто"));
            secondTableRecentFilesMenu.getItems().add(new MenuItem("Пусто"));
        } else {
            //first table
            for(File file : getRecentFiles()){
                if(file.getName().endsWith(".xlsx")){
                    MenuItem menuItem = new MenuItem(file.getName(), new ImageView(excelImage));
                    menuItem.setOnAction(event -> openFileLogic(file, 0));
                    firstTableRecentFilesMenu.getItems().add(menuItem);
                } else {
                    firstTableRecentFilesMenu.getItems().add(new MenuItem(file.getName(),new ImageView(unknownImage)));
                }
            }
            //second table
            for(File file : getRecentFiles()){
                if(file.getName().endsWith(".xlsx")){
                    MenuItem menuItem = new MenuItem(file.getName(), new ImageView(excelImage));
                    menuItem.setOnAction(event -> openFileLogic(file, 1));
                    secondTableRecentFilesMenu.getItems().add(menuItem);
                } else {
                    secondTableRecentFilesMenu.getItems().add(new MenuItem(file.getName(),new ImageView(unknownImage)));
                }
            }
        }
    }

    /**
     * Открывает эксель документ и делает всю необходимую работу
     * @param file ссылка на эксель файл
     * @param tableNumber0_1 значение 0 - 1, номер таблицы из которого вызывается команда, например:
     *                      0 для первой таблицы или 1 для второй
     */
    private void openFileLogic(File file, int tableNumber0_1){

        //Очистка/////////////////////
        onlyInFirstTable.getItems().clear();
        onlyInSecondTable.getItems().clear();
        firstTableCounter.setText("0");
        secondTableCounter.setText("0");
        //////////////////////////////


        //Работа с файлом
        if(!file.exists()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("");
            alert.setTitle("Ошибка");
            alert.setHeaderText("Файл:\r" + file + "\rне найден");
            alert.show();

            Application.getJsonProperties().removeRecentFile(file);
            initializeUpdateTableViews();

            return;
        }
        ExcelFileProcessor excelFileProcessor = new ExcelFileProcessor(file);

        //Заполнение лейбла с названием файла
        if(tableNumber0_1 == 0){
            firstFileNameLabel.setText(file.getName());
        } else {
            secondFileNameLabel.setText(file.getName());
        }

        //Добавление файла в RecentFiles
        Application.getJsonProperties().addRecentFile(file);
        initializeUpdateTableViews();

        //Очистка таблиц и сохранение листа с виа и вва в переменную
        if (tableNumber0_1 == 0) {
            onlyInFirstTable.getColumns().clear();
            firstList = excelFileProcessor.getVIAandVVARowList();
        } else {
            onlyInSecondTable.getColumns().clear();
            secondList = excelFileProcessor.getVIAandVVARowList();
        }

        //заполнение таблицы рядами
        LinkedList<String> titles = excelFileProcessor.getTitleCellList();

        TableColumn<ExcelRow, String> magazinColumn = new TableColumn<>(titles.getFirst());
        magazinColumn.setCellValueFactory(new PropertyValueFactory<>("magazin"));
        magazinColumn.setPrefWidth(125);
        magazinColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ExcelRow, String> naSkladeColumn = new TableColumn<>(titles.get(1));
        naSkladeColumn.setCellValueFactory(new PropertyValueFactory<>("na_sklade"));
        naSkladeColumn.setStyle("-fx-alignment: CENTER;");
        

        TableColumn<ExcelRow, String> prodanoColumn = new TableColumn<>(titles.get(2));
        prodanoColumn.setCellValueFactory(new PropertyValueFactory<>("prodano"));
        prodanoColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ExcelRow, String> articulColumn = new TableColumn<>(titles.get(3));
        articulColumn.setCellValueFactory(new PropertyValueFactory<>("articul"));
        articulColumn.setPrefWidth(110);
        articulColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ExcelRow, String> naimenovanieColumn = new TableColumn<>(titles.get(4));
        naimenovanieColumn.setCellValueFactory(new PropertyValueFactory<>("naimenovanie"));
        naimenovanieColumn.setPrefWidth(380);
        naimenovanieColumn.setStyle("-fx-alignment: CENTER-LEFT;");

        TableColumn<ExcelRow, String> proizvoditelColumn = new TableColumn<>(titles.get(5));
        proizvoditelColumn.setCellValueFactory(new PropertyValueFactory<>("proizvoditel"));
        proizvoditelColumn.setPrefWidth(150);
        proizvoditelColumn.setStyle("-fx-alignment: CENTER-LEFT;");

        TableColumn<ExcelRow,String> massaColumn = new TableColumn<>(titles.get(6));
        massaColumn.setCellValueFactory(new PropertyValueFactory<>("massa"));
        massaColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ExcelRow, String> shtrihKodColumn = new TableColumn<>(titles.get(7));
        shtrihKodColumn.setCellValueFactory(new PropertyValueFactory<>("shtrih_kod"));
        shtrihKodColumn.setPrefWidth(150);
        shtrihKodColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<ExcelRow, String> poMatriceColumn = new TableColumn<>(titles.get(8));
        poMatriceColumn.setCellValueFactory(new PropertyValueFactory<>("po_matrice"));
        poMatriceColumn.setStyle("-fx-alignment: CENTER;");



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

        File file1 = fileChooser.showOpenDialog(Application.getMainStage());
        //Если файл не выбран, выход из метода
        if(Objects.isNull(file1)){
            return;
        }
        openFileLogic(file1, 0);
    }
    @FXML
    private void secondTableFileOpenButton(){
        File file2 = fileChooser.showOpenDialog(Application.getMainStage());
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

        //Вывод списка с проверкой нескольких toggleGroup
        ArrayList<String> articles1 = new ArrayList<>();
        ArrayList<String> articles2 = new ArrayList<>();
        //Проверка toggleGroupViaCategory
        if(toggleGroupViaCategory.getToggles().getFirst().isSelected()){
            for (ExcelRow row : firstList){
                articles1.add(row.getArticul());
            }
            for(ExcelRow row : secondList){
                articles2.add(row.getArticul());
            }
        } else if(toggleGroupViaCategory.getToggles().get(1).isSelected()){
            for (ExcelRow row : firstList){
                if(row.getNaimenovanie().toUpperCase().contains("ВИА")){
                    articles1.add(row.getArticul());
                }
            }
            for(ExcelRow row : secondList){
                if(row.getNaimenovanie().toUpperCase().contains("ВИА")) {
                    articles2.add(row.getArticul());
                }
            }
        } else if(toggleGroupViaCategory.getToggles().get(2).isSelected()){
            for (ExcelRow row : firstList){
                if(row.getNaimenovanie().toUpperCase().contains("ВВА")){
                    articles1.add(row.getArticul());
                }
            }
            for(ExcelRow row : secondList){
                if(row.getNaimenovanie().toUpperCase().contains("ВВА")) {
                    articles2.add(row.getArticul());
                }
            }
        }
        //Проверка toggleGroupQuantityCategory
        ArrayList<String> articles1Copy = new ArrayList<>(articles1);
        ArrayList<String> articles2Copy = new ArrayList<>(articles2);
        if(toggleGroupQuantityCategory.getToggles().get(1).isSelected()){
            for(ExcelRow row : firstList){
                for(String string : articles1Copy){
                    if(row.getArticul().equals(string)){
                        if(row.getNa_sklade() < 1){
                            articles1.remove(string);
                            articles2.remove(string);
                        }
                    }
                }
            }
            for(ExcelRow row : secondList){
                for(String string : articles2Copy){
                    if(row.getArticul().equals(string)){
                        if(row.getNa_sklade() < 1){
                            articles1.remove(string);
                            articles2.remove(string);
                        }
                    }
                }
            }
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
     * Логика копирования артикулов
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

    /**
     * Логика копирования всей таблицы
     * @param tableNumber0_1 номер таблицы 0 или 1
     */
    private void copyTable(int tableNumber0_1) {
        ClipboardContent clipboardContent = new ClipboardContent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Магазин");
        stringBuilder.append("\t");
        stringBuilder.append("На складе");
        stringBuilder.append("\t");
        stringBuilder.append("Продано");
        stringBuilder.append("\t");
        stringBuilder.append("Артикул");
        stringBuilder.append("\t");
        stringBuilder.append("Наименование");
        stringBuilder.append("\t");
        stringBuilder.append("Производитель");
        stringBuilder.append("\t");
        stringBuilder.append("Масса, кг");
        stringBuilder.append("\t");
        stringBuilder.append("Штрих-код");
        stringBuilder.append("\t");
        stringBuilder.append("По матрице");
        stringBuilder.append("\n");
        if(tableNumber0_1 == 0){
            for(ExcelRow row : onlyInFirstTable.getItems()){
                stringBuilder.append(row.getMagazin());
                stringBuilder.append("\t");
                stringBuilder.append(row.getNa_sklade());
                stringBuilder.append("\t");
                stringBuilder.append(row.getProdano());
                stringBuilder.append("\t");
                stringBuilder.append(row.getArticul());
                stringBuilder.append("\t");
                stringBuilder.append(row.getNaimenovanie());
                stringBuilder.append("\t");
                stringBuilder.append(row.getProizvoditel());
                stringBuilder.append("\t");
                stringBuilder.append(row.getMassa());
                stringBuilder.append("\t");
                stringBuilder.append(row.getShtrih_kod());
                stringBuilder.append("\t");
                stringBuilder.append(row.getPo_matrice());
                stringBuilder.append("\n");
            }
        } else if (tableNumber0_1 == 1){
            for(ExcelRow row : onlyInSecondTable.getItems()){
                stringBuilder.append(row.getMagazin());
                stringBuilder.append("\t");
                stringBuilder.append(row.getNa_sklade());
                stringBuilder.append("\t");
                stringBuilder.append(row.getProdano());
                stringBuilder.append("\t");
                stringBuilder.append(row.getArticul());
                stringBuilder.append("\t");
                stringBuilder.append(row.getNaimenovanie());
                stringBuilder.append("\t");
                stringBuilder.append(row.getProizvoditel());
                stringBuilder.append("\t");
                stringBuilder.append(row.getMassa());
                stringBuilder.append("\t");
                stringBuilder.append(row.getShtrih_kod());
                stringBuilder.append("\t");
                stringBuilder.append(row.getPo_matrice());
                stringBuilder.append("\n");
            }
        }
        clipboardContent.putString(stringBuilder.toString());
        clipboard.setContent(clipboardContent);
    }
    @FXML
    private void copyFirstTable(){
        copyTable(0);
    }

    @FXML
    private void copySecondTable(){
        copyTable(1);
    }

    private List<File> getRecentFiles(){
        return Application.getJsonProperties().getReadOnlyRecentFiles();
    }

    @FXML
    private void clearRecentFiles(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Очистить недавние файлы?", ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("");

        Optional<ButtonType> optionalS = alert.showAndWait();
        if(optionalS.isPresent() && optionalS.get().equals(ButtonType.YES)){
            Application.getJsonProperties().clearRecentFiles();
            initializeUpdateTableViews();
        }
    }


}