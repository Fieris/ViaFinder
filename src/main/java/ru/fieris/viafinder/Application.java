package ru.fieris.viafinder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.extractor.ExcelExtractor;

import java.io.File;
import java.io.IOException;

public class Application extends javafx.application.Application {
    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stageInitializer(stage);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage getMainStage() {
        return mainStage;
    }


    private void stageInitializer(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Application-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Поиск новых ВИА и ВВА");
        stage.setResizable(false);
        stage.setScene(scene);
    }
}