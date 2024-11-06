module com.example.java2labfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.java2labfx to javafx.fxml;
    exports com.example.java2labfx;
}