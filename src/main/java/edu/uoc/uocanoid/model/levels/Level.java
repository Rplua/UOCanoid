package edu.uoc.uocanoid.model.levels;

import edu.uoc.uocanoid.model.XDirection;
import edu.uoc.uocanoid.model.YDirection;
import edu.uoc.uocanoid.model.ball.Ball;
import edu.uoc.uocanoid.model.bricks.Brick;
import edu.uoc.uocanoid.model.bricks.BrickFactory;
import edu.uoc.uocanoid.model.bricks.BrickUnbreakable;
import edu.uoc.uocanoid.model.paddle.Paddle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Level class.
 *
 * @author David García Solórzano
 * @version 1.0
 */
public class Level {

    /**
     * List of bricks that are part of the level.
     */
    private final List<Brick> bricks;

    /**
     * Reference to the {@code Ball} object that represents the ball in the level.
     */
    private Ball ball;

    /**
     * Reference to the {@code Paddle} object that represents the paddle in the level.
     */
    private Paddle paddle;

    /**
     * Initial data for ball, paddle and bricks that are related to the view.
     */
    private final Map<String, Map<String, Integer>> INIT_DATA;


    /**
     * Constructor
     *
     * @param fileName Name of the file that contains level's data.
     * @param initData Initial data for ball, paddle and bricks that are related to the view.
     * @throws LevelException When there is any error while parsing the file.
     */
    public Level(String fileName, Map<String, Map<String, Integer>> initData) throws LevelException {
        bricks = new ArrayList<>();
        INIT_DATA = initData;
        parse(fileName);
    }

    /**
     * Parses/Reads level's data from the given file.<br/>
     * It also checks which the board's requirements are met.
     *
     * @param fileName Name of the file that contains level's data.
     * @throws LevelException When there is any error while parsing the file
     *                        or some board's requirement is not satisfied.     *
     */
    private void parse(String fileName) throws LevelException {
        String line;

        InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(fileName));

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            int row = 0;

            var data = INIT_DATA.get("ball");
            line = getFirstNonEmptyLine(reader);
            int speed = (line != null) ? Integer.parseInt(line) : data.get("speed");

            if (speed < 1)
                throw new LevelException(LevelException.SPEED_ERROR);

            ball = new Ball(data.get("x"), data.get("y"), data.get("radius"),
                    XDirection.getValueByOrientation(data.get("xDirection")),
                    YDirection.getValueByOrientation(data.get("yDirection")), speed);

            data = INIT_DATA.get("paddle");
            line = getFirstNonEmptyLine(reader);
            speed = (line != null) ? Integer.parseInt(line) : data.get("speed");

            if (speed < 1)
                throw new LevelException(LevelException.SPEED_ERROR);

            paddle = new Paddle(data.get("x"), data.get("y"), data.get("width"), data.get("height"), speed);

            data = INIT_DATA.get("brick");

            while ((line = getFirstNonEmptyLine(reader)) != null) {

                var bricksLine = line.toCharArray();

                for (int i = 0; i < bricksLine.length; i++) {
                    var brick = BrickFactory.getBrickInstance(bricksLine[i], i * data.get("width"), row * data.get("height"), data.get("width"), data.get("height"));
                    if (brick != null)
                        bricks.add(brick);
                }
                row++;
            }

            //Checks if there are one brick at least.
            if (bricks.isEmpty()) {
                throw new LevelException(LevelException.MIN_BRICKS_ERROR);
            }
        } catch (IllegalArgumentException | IOException e) {
            throw new LevelException(LevelException.PARSING_LEVEL_FILE_ERROR);
        }
    }

    /**
     * This is a helper method for {@link #parse(String fileName)} which returns
     * the first non-empty and non-comment line from the reader.
     *
     * @param br BufferedReader object to read from.
     * @return First line that is a parsable line, or {@code null} there are no lines to read.
     * @throws IOException if the reader fails to read a line.
     */
    private String getFirstNonEmptyLine(final BufferedReader br) throws IOException {
        do {

            String s = br.readLine();

            if (s == null) {
                return null;
            }
            if (s.isBlank() || s.startsWith("/")) {
                continue;
            }

            return s;
        } while (true);
    }

    /**
     * Checks if the level is completed, i.e. the player has broken all the bricks of the board
     * or the only bricks that remain are unbreakable.
     *
     * @return {@code true} if this level is beaten, otherwise {@code false}.
     */
    public boolean isCompleted() {
        //TODO
    }

    /**
     * Getter of the attribute {@code bricks}.
     *
     * @return Current value of the attribute {@code bricks}.
     */
    public List<Brick> getBricks() {
        //TODO
    }

    /**
     * Getter of the attribute {@code ball}.
     *
     * @return Current value of the attribute {@code ball}.
     */
    public Ball getBall() {
        //TODO
    }

    /**
     * Getter of the attribute {@code paddle}.
     *
     * @return Current value of the attribute {@code paddle}.
     */
    public Paddle getPaddle() {
        //TODO
    }
}
