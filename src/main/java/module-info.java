/**
 * Module of the program (based on Java 9's modules)
 */
open module edu.uoc.uocanoid{
    requires  javafx.controls;
    requires  javafx.fxml;
    exports edu.uoc.uocanoid.view;
}