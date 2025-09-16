package edu.uoc.uocanoid.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * CreditsViewController class related to "credits.fxml".
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class CreditsViewController {

    @FXML
    private void goToMain(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/main.fxml")
        );



        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
