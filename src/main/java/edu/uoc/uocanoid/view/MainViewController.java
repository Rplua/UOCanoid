package edu.uoc.uocanoid.view;

import edu.uoc.uocanoid.UOCanoid;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * MainViewController class related to "main.fxml".
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class MainViewController {

    /**
     * It goes to the "play" scene.
     */
    @FXML
    public void newGame(){
        try{
            UOCanoid.main.goScene("play");
        }catch(IOException e){
            System.exit(1);
        }
    }

    /**
     * It goes to the "credits" scene.
     */
    @FXML
    public void readCredits(){
        try{
            UOCanoid.main.goScene("credits");
        }catch(IOException e){
            System.exit(1);
        }
    }
}
