module org.observations {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.observations to javafx.fxml;
    exports org.observations;
    exports org.observations.gui;
    opens org.observations.gui to javafx.fxml;
    exports org.observations.controllers;
    opens org.observations.controllers to javafx.fxml;
    exports org.observations.gui.popup;
    opens org.observations.gui.popup to javafx.fxml;
}