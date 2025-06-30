package edu.uoc.uocanoid.controller;

import edu.uoc.uocanoid.model.XDirection;
import edu.uoc.uocanoid.model.ball.Ball;
import edu.uoc.uocanoid.model.bricks.Brick;
import edu.uoc.uocanoid.model.levels.Level;
import edu.uoc.uocanoid.model.levels.LevelException;
import edu.uoc.uocanoid.model.paddle.Paddle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;

/**
 * GameController class.
 * <p>
 * This class is the controller of the game. It is the middleware between the model and the view,.
 * </p>
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class GameController {

    /**
     * Name of the folder in which level files are
     */
    private String fileFolder;

    /**
     * Stage's width
     */
    private final int WIDTH;

    /**
     * Stage's height
     */
    private final int HEIGHT;

    /**
     * Number of the current level. Init value = 0.
     */
    private int currentLevel = 0;

    /**
     * Maximum quantity of levels that the game has.
     */
    private final int MAX_LEVELS;

    /**
     * Total score of the game, i.e. the sum of the levels' scores. Init value = 0.
     */
    private int score;

    /**
     * Number of lives that the player has at present.
     */
    private int numLives;

    /**
     * Initial value for the attribute {@code numLives}. Value = 3.
     */
    private final int INIT_NUM_LIVES = 3;

    /**
     * Level object that contains the information of the current level.
     */
    private Level level;

    /**
     * Initial data related to the view that is needed in the controller and model.
     */
    private final Map<String, Map<String, Integer>> INIT_DATA;

    /**
     * Constructor with arguments.
     *
     * @param fileFolder Folder name where the configuration/level files are.
     * @param width      Stage's width.
     * @param height     Stage's height.
     * @param initData   Initial data related to the view that is needed in the controller and model.
     * @throws IOException When there is a problem while retrieving number of levels
     */
    public GameController(String fileFolder, int width, int height,
                          Map<String, Map<String, Integer>> initData) throws IOException {
        int num;
        WIDTH = width;
        HEIGHT = height;
        INIT_DATA = initData;

        setFileFolder(fileFolder);

        String path = null;
        try {
            path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException e) {
            System.out.println("ERROR: Game Constructor");
            System.exit(-1);
        }

        if (path.endsWith(".jar")) {
            URI uri = URI.create("jar:file:" + path);

            //Get the number of files that are in the fileFolder, i.e. the number of levels.
            try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                num = (int) Files.walk(fs.getPath(getFileFolder()))
                        .filter(Files::isRegularFile).count();
            }
        } else {
            //run in ide for the tests
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream;
            inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(getFileFolder().substring(1)));

            try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {
                num = (int) reader.lines().count();
            }
        }

        setScore(0);
        setNumLives(INIT_NUM_LIVES);
        MAX_LEVELS = num;
    }

    /**
     * Setter of the attribute {@code fileFolder}.
     *
     * @param fileFolder Folder name where the configuration/level files are.
     */
    private void setFileFolder(String fileFolder) {
        this.fileFolder = fileFolder;
    }

    /**
     * Getter of the attribute {@code fileFolder}.
     *
     * @return Value of the attribute {@code fileFolder}.
     */
    private String getFileFolder() {
        return fileFolder;
    }

    /**
     * Getter of the attribute {@code currentLevel}.
     *
     * @return Value of the attribute {@code currentLevel} that indicates which level the player is playing.
     */
    public int getCurrentLevel() {
        //TODO
    }

    /**
     * Setter of the attribute {@code currentLevel}.<br/>
     * The new value is assigned as long as it is greater than 0. Otherwise, the current value remains.
     *
     * @param currentLevel New value for the attribute {@code currentLevel}.
     */
    private void setCurrentLevel(int currentLevel) {
        if (currentLevel > 0)
            this.currentLevel = currentLevel;
    }

    /**
     * Getter of the attribute {@code score}.
     *
     * @return Value of the attribute {@code score}.
     */
    public int getScore() {
        //TODO
    }

    /**
     * Setter of the attribute {@code score}.<br/>
     * The new value is assigned as long as it is 0 or a positive number. Otherwise, the current value remains.
     *
     * @param score New value for the attribute {@code points}.
     */
    private void setScore(int score) {
        if (score >= 0)
            this.score = score;
    }

    /**
     * Getter of the attribute {@code numLives}.
     *
     * @return Value of the attribute {@code numLives}.
     */
    public int getNumLives() {
        //TODO
    }

    /**
     * Setter of the attribute {@code numLives}.<br/>
     * The new value is assigned as long as it is 0 or a positive number. Otherwise, the current value remains.
     *
     * @param numLives New value for the attribute {@code numLives}.
     */
    public void setNumLives(int numLives) {
        //TODO
    }

    /**
     * Getter of the attribute {@code maxLevels}.
     *
     * @return Value of the attribute {@code maxLevels}.
     */
    public int getMAX_LEVELS() {
        //TODO
    }

    /**
     * Indicates if the game is finished ({@code true}) or not ({@code false}).
     * <p>The game is finished when the attribute {@code currentLevel} is equal to the attribute {@code maxLevels}.
     * </p>
     *
     * @return True if there are no more levels and therefore the game is finished. Otherwise, false.
     */
    public boolean isGameFinished() {
        //TODO
    }

    /**
     * Checks if the level is completed.
     *
     * @return {@code true} if this level is beaten, otherwise {@code false}.
     */
    public boolean isLevelCompleted() {
        //TODO
    }


    /**
     * Checks if the player has lost, i.e. the number of lives is zero.
     *
     * @return {@code true} if this the player has lost, otherwise {@code false}.
     */
    public boolean hasLost() {
        //TODO
    }

    /**
     * Checks if there is a new level to play and loads it.<br/>
     * If the game is finished, it returns {@code false}. Otherwise, it updates {@code currentLevel}, loads
     * the new level and returns {@code true}.
     *
     * @return {@code true} if there is a next level, and it has been loaded correctly. Otherwise, it returns {@code false}.
     * @throws LevelException When there is a level exception/problem loading the new level.
     */
    public boolean nextLevel() throws LevelException {
        //TODO
    }

    /**
     * Loads a new level by using the values of the attributes {@code currentLevel} and {@code INIT_DATA}.
     * <p>
     * The pattern of the filename is: fileFolder+"level" + numberLevel + ".txt".
     * </p>
     *
     * @throws LevelException When there is a level exception/problem.
     */
    private void loadLevel() throws LevelException {
        level = new Level(getFileFolder() + "level" + currentLevel + ".txt", INIT_DATA);
    }

    /**
     * It returns the {@link Brick} objects of the current level as a {@link List}.
     *
     * @return {@link Brick} objects of the current level.
     */
    public List<Brick> getBricks() {
        //TODO
    }

    /**
     * It returns, as a {@link Map}, the values of the following ball's attributes of the current level:<br/>
     * <ul>
     *     <li>x</li>
     *     <li>y</li>
     *     <li>width</li>
     *     <li>height</li>
     *     <li>xDirection</li>
     *     <li>yDirection</li>
     *     <li>speed</li>
     * </ul>
     * With the object that is returned, it is possible to do: obj.get("x") and retrieve the value of the ball's x.
     *
     * @return {@link Ball}'s data of the current level in a {@link Map} format.
     */
    public Map<String, Integer> getBallData() {
        //TODO
    }

    /**
     * It returns, as a {@link Map}, the values of the following paddle's attributes of the current level:<br/>
     * <ul>
     *     <li>x</li>
     *     <li>y</li>
     *     <li>width</li>
     *     <li>height</li>
     *     <li>direction</li>
     *     <li>speed</li>
     * </ul>
     * With the object that is returned, it is possible to do: obj.get("x") and retrieve the value of the paddle's x.
     *
     * @return {@link Paddle}'s data of the current level in a {@link Map} format.
     */
    public Map<String, Integer> getPaddleData() {
        //TODO
    }

    /**
     * Change the direction of the ball in x-axis.
     */
    private void swapBallXDirection() {
        //TODO
    }

    /**
     * Change the direction of the ball in y-axis.
     */
    private void swapBallYDirection() {
        //TODO
    }

    /**
     * For each brick which is alive in the level, it checks if the ball is in its range. If the ball is in the brick's range,
     * then the ball's y direction must be changed and the brick's current number of hits must be also updated. Moreover,
     * if as a result of the hit the brick is broken, then the brick's points must be added to the game's score and the
     * brick must be removed from the level.
     * <p>
     * Regarding the ball's x direction, this must be changed if the ball hits the brick in the border. This means that:
     * On the left, the ball's x is equal to or less than the brick's x.
     * On the right, the ball's x is equal to or greater than the brick's most-righted x minus the ball's width.
     */
    private void checkCollisionBricks() {
        //TODO
    }

    /**
     * It checks if the ball exceeds the stage (right, left or top).
     * <p>
     * If the ball exceeds in the x-axis (right/left), then {@code swapBallXDirection} must be invoked.
     * If the ball exceeds in the y-axis (top), then {@code swapBallYDirection} must be invoked.
     * </p>
     * Hint: you should notice that ball's (x,y) are the top-left corner of the ball.
     */
    private void checkCollisionScene() {
        //TODO
    }

    /**
     * It checks if the ball is in paddle's range. If the ball is in the paddle's range,
     * then the ball's y direction must be changed.
     * <p>
     * Regarding the ball's x direction, this must be changed if the ball hits the paddle in the border. This means that:
     * On the left, the ball's x is equal to less than the paddle's x.
     * On the right, the ball's x is equal to or greater than the paddle's most-righted x minus the ball's width.
     */
    private void checkCollisionPaddle() {
        //TODO
    }

    /**
     * It checks if the ball exceeds the paddle's y.<br/>
     * If the ball exceeds the paddle's y, then the player must lose one life. Moreover, the game must be restored.
     *
     * @return {@code true} if the ball exceeds the paddle's y. Otherwise, it returns {@code false}.
     */
    private boolean checkCollisionBottom() {
        //TODO
    }

    /**
     * It moves the {@link Paddle} object of the current in the {@code direction} indicated by parameter.
     * <p>
     * This method moves the paddle if any part of the paddle in the future position is not out of the stage.
     * Remember that size of the stage are 0 (left) and {@code WIDTH}.
     * Likewise, the new position of the paddle it will be the result of adding its {@code speed} in its current {@code direction}.
     * </p>
     *
     * @param direction Direction in which the player wants the paddle to move.
     */
    public void movePaddle(XDirection direction) {
        //TODO
    }

    /**
     * It invokes the {@link Ball}'s {@code move} method.
     */
    private void moveBall() {
        Ball ball = level.getBall();
        ball.move();
    }

    /**
     * If the level is completed, then it does nothing and returns {@code false}. Otherwise, it invokes the following
     * methods in this order: {@code checkCollisionPaddle}, {@code moveBall}, {@code checkCollisionBricks}, {@code checkCollisionScene} and
     * {@code checkCollisionBottom}. In this case, this method returns the value returned by {@code checkCollisionBottom}.
     *
     * @return The value returned by {@code checkCollisionBottom}.
     */
    public boolean update() {
        //TODO
    }

    /**
     * It invokes the {@link Ball}'s {@code restore} method.
     */
    public void restore() {
        //TODO
    }
}
