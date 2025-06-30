package edu.uoc.uocanoid.view;

import edu.uoc.uocanoid.UOCanoid;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Map;

/**
 * GameOverViewController class related to "gameover.fxml".
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class GameOverViewController {

    /**
     * UI object that displays game's score.
     */
    @FXML
    Label score;

    /**
     * UI object that displays game's current level.
     */
    @FXML
    Label level;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.

     */
    @FXML
    private void initialize(){
        Map<String, Object> data = UOCanoid.main.data;
        score.setText(data.get("score")+" points");
        level.setText("LEVEL "+data.get("level"));
    }


    /**
     * It goes to the "main" scene.
     */
    @FXML
    public void backMain(){
        try{
            UOCanoid.main.goScene("main");
        }catch(IOException e){
            System.exit(1);
        }
    }
}
