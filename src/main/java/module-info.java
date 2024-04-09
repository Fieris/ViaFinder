module ru.fieris.viafinder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens ru.fieris.viafinder to javafx.fxml;
    exports ru.fieris.viafinder;
}