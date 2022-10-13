module org.observations {
    requires javafx.graphics;
    requires javafx.controls;
    requires org.testng;

    exports org.observations;
    opens org.observations to javafx.graphics;
}