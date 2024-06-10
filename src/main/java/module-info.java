module ru.fieris.viafinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;

    requires org.kordamp.ikonli.javafx;

    opens ru.fieris.viafinder to javafx.fxml, java.base;
    opens ru.fieris.viafinder.Excel to java.base;
    exports ru.fieris.viafinder;
    exports ru.fieris.viafinder.Excel;
}