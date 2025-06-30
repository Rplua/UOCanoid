package edu.uoc.uocanoid.view;

import edu.uoc.uocanoid.UOCanoid;
import edu.uoc.uocanoid.controller.GameController;
import edu.uoc.uocanoid.model.XDirection;
import edu.uoc.uocanoid.model.YDirection;
import edu.uoc.uocanoid.model.bricks.BrickBasic;
import edu.uoc.uocanoid.model.bricks.BrickUnbreakable;
import edu.uoc.uocanoid.model.levels.LevelException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Map;

/**
 * PlayViewController class related to "play.fxml".
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class PlayViewController {

    /**
     * Game object that allows to manage the game.
     */
    private GameController game;

    /**
     * UI object that displays game's board.
     */
    @FXML
    private Pane canvas;

    /**
     * UI object that displays game's score.
     */
    @FXML
    private Label score;

    /**
     * UI object that displays game's lives.
     */
    @FXML
    private Label lives;

    /**
     * UI object that displays game's ball on the canvas.
     */
    private Circle ball;

    /**
     * UI object that displays game's paddle on the canvas.
     */
    private Rectangle paddle;

    /**
     * Stage/canvas's width.
     */
    private final static int WIDTH = UOCanoid.WIDTH;

    /**
     * Canvas's width.
     */
    private final static int HEIGHT = UOCanoid.HEIGHT - 75; //remove top bar.

    /**
     * Initial data for the ball related to the view that is important for the model.
     */
    private final Map<String, Integer> BALL_INIT_DATA = Map.of("x", 300, "y", 300, "radius", 5,
             "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.UP.getOrientation(), "speed", 2);

    /**
     * Initial data for the paddle related to the view that is important for the model.
     */
    private final Map<String, Integer> PADDLE_INIT_DATA = Map.of("x", 256, "y", 400, "width", 88,
            "height", 10, "xDirection", XDirection.RIGHT.getOrientation(), "speed", 10);

    /**
     * Initial data for the brick related to the view that is important for the model.
     */
    private final Map<String, Integer> BRICK_INIT_DATA = Map.of("width", 50, "height", 30);

    /**
     * Initial data for the ball, paddle and brick related to the view that is important for the model.
     */
    private final Map<String, Map<String, Integer>> INIT_DATA = Map.of("ball", BALL_INIT_DATA,
            "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

    /**
     * It allows us to manage the alert message which displays when a level is solved or the ball falls.
     */
    @FXML
    private Label alert;

    /**
     * It indicates if the ball fell out of the stage and as a consequence the player died (s/he lost one life).
     */
    private boolean died = true;

    /**
     * It is a thread that executes the game cycle each 10 milliseconds.
     */
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {

            if (!alert.isVisible()) {
                died = game.update();
                updateCanvas();

                if (died) {
                    if (game.hasLost()) {
                        go2GameOver();
                    } else {
                        game.restore();
                        showAlert("You lost one life.\nPress 'R' to continue");
                    }

                } else if (game.isLevelCompleted()) {
                    showAlert("Level " + (game.getCurrentLevel() + 1) + "\nPress 'C' to start");
                    try {
                        startLevel();
                    } catch (LevelException e) {
                        System.exit(-1);
                    }
                }
            }
        }
    }));

    /**
     * It stops teh game and shows the {@code msg} in a Label in the middle of the {@code canvas}.
     * @param msg Text to display.
     */
    private void showAlert(String msg) {
        timeline.stop();
        alert.setVisible(true);
        alert.setText(msg);
    }

    /**
     * It hides the alert and replays the game.
     */
    private void hideAlert() {
        alert.setVisible(false);
        timeline.play();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @throws IOException    When there is a problem while loading the game.
     * @throws LevelException When there is a problem while loading a new level.
     */
    @FXML
    private void initialize() throws IOException, LevelException {

        canvas.getParent().setOnKeyPressed(e -> {
            if (alert.isVisible() && e.getCode() == KeyCode.C && !died) {
                hideAlert();
            } else if (alert.isVisible() && e.getCode() == KeyCode.R && died) {
                hideAlert();
            } else if (e.getCode() == KeyCode.A) {
                movePaddle(XDirection.LEFT);
            } else if (e.getCode() == KeyCode.S) {
                movePaddle(XDirection.RIGHT);
            }
        });
        //Need to catch key pressed event!!
        canvas.requestFocus();

        game = new GameController("/levels/", WIDTH, HEIGHT, INIT_DATA);
        score.setText("0");
        lives.setText("3");
        timeline.setCycleCount(Animation.INDEFINITE);
        startLevel();
    }

    /**
     * It starts a new level if it exists. Otherwise, it stops the game and goes to the "gameover" scene.
     * @throws LevelException When there is a problem while loading the new level.
     */
    private void startLevel() throws LevelException {
        if (game.nextLevel()) {
            canvas.getChildren().clear();
            paintInitCanvas();
            this.died = false;
            timeline.play();
        } else {
            timeline.stop();
            go2GameOver();
        }
    }

    /**
     * It stops the timeline, collects data related to the score and the number of the current level,
     * and it finally goes to "gameover" scene.
     */
    private void go2GameOver() {
        try {
            timeline.stop();
            UOCanoid.main.data.put("score", game.getScore());
            UOCanoid.main.data.put("level", game.getCurrentLevel());
            UOCanoid.main.goScene("gameover");
        } catch (IOException e) {
            System.exit(1);
        }
    }

    /**
     * It paints the initial status of the current level o {@code canvas}.
     */
    private void paintInitCanvas() {
        paintInitBall();
        paintInitPaddle();
        paintInitBricks();
    }

    /**
     * It paints the initial status of the current level's ball on the {@code canvas}.
     */
    private void paintInitBall() {
        Map<String, Integer> ballData = game.getBallData();
        this.ball = new Circle(ballData.get("radius"), Color.valueOf("DODGERBLUE"));
        this.ball.setLayoutX(ballData.get("x"));
        this.ball.setLayoutY(ballData.get("y"));
        this.ball.setId("ball");
        canvas.getChildren().add(this.ball);
    }

    /**
     * It paints the initial status of the current level's paddle on the {@code canvas}.
     */
    private void paintInitPaddle() {
        Map<String, Integer> paddleData = game.getPaddleData();
        this.paddle = new Rectangle(paddleData.get("width"), paddleData.get("height"));
        this.paddle.setLayoutX(paddleData.get("x"));
        this.paddle.setLayoutY(paddleData.get("y"));
        this.paddle.setFill(Color.valueOf("DODGERBLUE"));
        this.paddle.setArcHeight(5);
        this.paddle.setArcWidth(5);
        this.paddle.setStroke(Color.BLACK);
        this.paddle.setId("paddle");
        canvas.getChildren().add(this.paddle);
    }

    /**
     * It paints the initial status of the current level's brick on the {@code canvas}.
     */
    public void paintInitBricks() {
        String color;

        for (var brick : game.getBricks()) {
            Rectangle rec = new Rectangle(brick.getWidth(), brick.getHeight());
            rec.setLayoutX(brick.getX());
            rec.setLayoutY(brick.getY());


            if (brick instanceof BrickBasic) {
                color = "#ff0000";
            } else if (brick instanceof BrickUnbreakable) {
                color = "#000000";
            }else{
                color = "#00ff00";
            }

            rec.setFill(Color.valueOf(color));

            rec.setStroke(Color.valueOf("#000000"));
            canvas.getChildren().add(rec);
        }
    }

    /**
     * It updates the bricks, ball, score and lives on the canvas.
     */
    private void updateCanvas() {
        updateBricks();
        updateBall();
        updateScore();
        updateLives();
    }

    /**
     * It updates by painting the bricks on the {@code canvas}.
     */
    private void updateBricks() {
        //null means that it is a brick, because "ball" and "paddle" are the only objects with ID
        canvas.getChildren().removeIf(brick -> brick.getId() == null && game.getBricks().stream().noneMatch(b ->
                b.getX() == brick.getLayoutX()
                        && b.getY() == brick.getLayoutY()));
    }

    /**
     * It updates by painting the ball on the {@code canvas}.
     */
    private void updateBall() {
        var ballData = game.getBallData();
        ball.setLayoutX(ballData.get("x"));
        ball.setLayoutY(ballData.get("y"));
    }

    /**
     * It updates the UI component related to score.
     */
    private void updateScore() {
        score.setText(game.getScore() + "");
    }

    /**
     * It updates the UI component related to lives.
     */
    private void updateLives() {
        lives.setText(game.getNumLives() + "");
    }

    /**
     * It moves the paddle in the direction indicated by parameters.
     * @param direction Direction in which the paddle must be moved.
     */
    public void movePaddle(XDirection direction) {
        Map<String, Integer> paddleData = game.getPaddleData();

        game.movePaddle(direction);
        paddle.setLayoutX(paddleData.get("x"));
        paddle.setLayoutY(paddleData.get("y"));
    }

    /**
     * It goes to the "main" scene.
     */
    @FXML
    public void backMain() {
        try {
            timeline.stop();
            UOCanoid.main.goScene("main");
        } catch (IOException e) {
            System.exit(1);
        }
    }

}
