package ru.fieris.viafinder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.fieris.viafinder.Json.JsonProperties;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private static Stage mainStage;
    private static final String programDirectory = System.getProperty("user.home");
    private static JsonProperties jsonProperties;
    @Override
    public void start(Stage stage){
        mainStage = stage;
        jsonProperties = new JsonProperties();
        stageInitializer(stage);
        fileSystemInitializer();
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
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(Objects.requireNonNull(Application.class.getResourceAsStream("icons/box.png")));
        stage.getIcons().add(image);

        stage.setTitle("Поиск ВИА и ВВА");
        stage.setResizable(false);
        stage.setScene(scene);
    }


    /**
     * Создает необходимые папки для хранения файлов
     */
    private void fileSystemInitializer() {
    }

    public static String getProgramDirectory(){
        return programDirectory;
    }

    public static JsonProperties getJsonProperties() {
        return jsonProperties;
    }
}