module ru.fieris.viafinder {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires org.apache.commons.io;
    requires com.fasterxml.jackson.databind;

    requires org.kordamp.ikonli.javafx;

    opens ru.fieris.viafinder to javafx.fxml, java.base;
    opens ru.fieris.viafinder.Excel to java.base;
    exports ru.fieris.viafinder;
    exports ru.fieris.viafinder.Excel;
    exports ru.fieris.viafinder.Json;
}