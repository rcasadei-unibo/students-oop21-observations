module org.observations {
    requires javafx.graphics;
    requires javafx.controls;
    requires org.testng;
    requires org.apache.pdfbox;
    requires org.apache.fontbox;

    exports org.observations;
    opens org.observations to javafx.graphics;
    exports org.observations.utility;
    opens org.observations.utility to javafx.graphics;
}