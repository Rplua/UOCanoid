package edu.uoc.uocanoid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * UOCanoid class.
 * <p>
 *  This is the main class when a jar file is not used.
 * </p>
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class UOCanoid extends Application {

    /**
     * Window/stage's width.
     */
    public final static int WIDTH = 650;

    /**
     * Window/stage's height.
     */
    public final static int HEIGHT = 500;

    /**
     * Game's title.
     */
    private final static String TITLE = "UOCanoid";

    /**
     * Special object to transfer data between scenes/FXML.
     */
    public Map<String, Object> data;


    /**
     * It is a reference to this class so that other classes related to the different views can use it.
     */
    public static UOCanoid main;

    /**
     * It is the component where the GUI is displayed.
     */
    private Stage stage;

    /**
     * Overriden method.
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException When there is an error while loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        main = this;
        data = new HashMap<>();
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle(UOCanoid.TITLE);
        goScene("main");
    }

    /**
     * It shows the FXML view that is indicated.
     *
     * @param view Name of the FXML file.
     * @throws IOException When there is an error while loading the FXML file.
     */
    public void goScene(String view) throws IOException{
        // Load root layout from fxml file and show the scene containing the root layout.
        try{
            URL resource = UOCanoid.class.getResource("/fxml/"+view+".fxml");
            if(resource == null){
                throw new Exception();
            }
            Scene scene = new Scene(FXMLLoader.load(resource), WIDTH, HEIGHT);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            System.err.println("______ERROR________"+e.getMessage());
        }

    }

    /**
     * Main method
     * @param args This param is by default, but it is not used.
     */
    public static void main(String[] args) {
        launch(args);
    }
}