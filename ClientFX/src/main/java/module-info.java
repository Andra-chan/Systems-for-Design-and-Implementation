module clientfx.clientfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens clientfx.clientfx to javafx.fxml;
    exports clientfx.clientfx;
    exports clientfx.clientfx.gui;
    opens clientfx.clientfx.gui to javafx.fxml;
}