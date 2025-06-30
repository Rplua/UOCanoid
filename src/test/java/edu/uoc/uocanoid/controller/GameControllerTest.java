package edu.uoc.uocanoid.controller;

import edu.uoc.uocanoid.UOCanoid;
import edu.uoc.uocanoid.model.XDirection;
import edu.uoc.uocanoid.model.YDirection;
import edu.uoc.uocanoid.model.bricks.Brick;
import edu.uoc.uocanoid.model.bricks.BrickAdvanced;
import edu.uoc.uocanoid.model.bricks.BrickBasic;
import edu.uoc.uocanoid.model.bricks.BrickUnbreakable;
import edu.uoc.uocanoid.model.levels.Level;
import edu.uoc.uocanoid.model.levels.LevelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController game;

    private Map<String, Integer> BALL_INIT_DATA = Map.of("x", 300, "y", 300, "radius", 5,
             "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.UP.getOrientation(), "speed", 2);
    private Map<String, Integer> PADDLE_INIT_DATA = Map.of("x", 256, "y", 400, "width", 88, "height", 10,
             "xDirection", XDirection.RIGHT.getOrientation(), "speed", 5);
    private Map<String, Integer> BRICK_INIT_DATA = Map.of("width", 50, "height", 30);
    private Map<String, Map<String, Integer>> INIT_DATA = Map.of("ball", BALL_INIT_DATA,
            "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

    @BeforeEach
    void setUp() {
        try {
            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, INIT_DATA);
        } catch (IOException e) {
            fail("[ERROR] There is some problem with the GameController's constructor");
        }
    }

    @Test
    @Tag("basic")
    void getCurrentLevel() {
        assertEquals(0, game.getCurrentLevel());

        try {
            game.nextLevel();
            assertEquals(1, game.getCurrentLevel());
        } catch (LevelException e) {
            fail("getCurrentLevel failed");
        }
    }

    @Test
    @Tag("basic")
    void getScore() {
        assertEquals(0, game.getScore());
    }

    @Test
    @Tag("basic")
    void getNumLives() {
        assertEquals(3, game.getNumLives());
    }

    @Test
    @Tag("basic")
    void setNumLives() {
        game.setNumLives(5);
        assertEquals(5, game.getNumLives());

        game.setNumLives(-4);
        assertEquals(5, game.getNumLives());

        game.setNumLives(0);
        assertEquals(0, game.getNumLives());
    }

    @Test
    @Tag("basic")
    void getMaxLevels() {
        assertEquals(3, game.getMAX_LEVELS());
    }

    @Test
    @Tag("basic")
    void isGameFinished() {
        try {
            game.nextLevel();
            assertFalse(game.isGameFinished());
            game.nextLevel();
            assertFalse(game.isGameFinished());
            game.nextLevel();
            assertTrue(game.isGameFinished());
        } catch (LevelException e) {
            fail("isFinished failed");
        }
    }

    @Test
    @Tag("basic")
    void isLevelCompleted() {
        try {
            assertTrue(game.nextLevel());
            assertFalse(game.isLevelCompleted());
            assertTrue(game.nextLevel());
            assertFalse(game.isLevelCompleted());
            assertTrue(game.nextLevel());
            assertTrue(game.isLevelCompleted()); //1 unbreakable brick
        } catch (LevelException e) {
            fail("isLevelCompleted failed");
        }
    }

    @Test
    @Tag("basic")
    void hasLost() {
        try {
            game.nextLevel();
            assertFalse(game.hasLost());
            game.setNumLives(0);
            assertTrue(game.hasLost());
        } catch (LevelException e) {
            fail("hasLost failed");
        }
    }

    @Test
    @Tag("basic")
    void nextLevel() {
        try {
            assertTrue(game.nextLevel());
            assertEquals(1, game.getCurrentLevel());
            assertTrue(game.nextLevel());
            assertEquals(2, game.getCurrentLevel());
            assertTrue(game.nextLevel());
            assertEquals(3, game.getCurrentLevel());
            assertFalse(game.nextLevel());
            assertEquals(3, game.getCurrentLevel());
        } catch (LevelException e) {
            fail("nextLevel failed");
        }
    }

    @Test
    @Tag("basic")
    void getBricks() {
        try {
            game.nextLevel();
            List<Brick> bricks = game.getBricks();

            assertEquals(450, bricks.get(0).getX());
            assertEquals(0, bricks.get(0).getY());
            assertInstanceOf(BrickUnbreakable.class, bricks.get(0));

            assertEquals(500, bricks.get(1).getX());
            assertEquals(0, bricks.get(1).getY());
            assertInstanceOf(BrickBasic.class, bricks.get(1));

            assertEquals(550, bricks.get(2).getX());
            assertEquals(0, bricks.get(2).getY());
            assertInstanceOf(BrickAdvanced.class, bricks.get(2));
        } catch (LevelException e) {
            fail("getBricks failed");
        }
    }

    @Test
    @Tag("basic")
    void getBallData() {
        try {
            game.nextLevel();
            var data = game.getBallData();
            assertEquals(300, data.get("x"));
            assertEquals(300, data.get("y"));
            assertEquals(5, data.get("radius"));
            assertEquals(XDirection.RIGHT.getOrientation(), data.get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), data.get("yDirection"));
            assertEquals(2, data.get("speed"));  //indicated in level1.txt
        } catch (LevelException e) {
            fail("getBallData failed");
        }
    }

    @Test
    @Tag("basic")
    void getPaddleData() {
        try {
            game.nextLevel();
            var data = game.getPaddleData();
            assertEquals(256, data.get("x"));
            assertEquals(400, data.get("y"));
            assertEquals(88, data.get("width"));
            assertEquals(10, data.get("height"));
            assertEquals(XDirection.RIGHT.getOrientation(), data.get("direction"));
            assertEquals(10, data.get("speed")); //indicated in level1.txt
        } catch (LevelException e) {
            fail("getPaddleData failed");
        }
    }

    @Test
    @Tag("basic")
    void movePaddle1() {
        try {
            game.nextLevel();
            game.movePaddle(XDirection.RIGHT);
            assertEquals(266, game.getPaddleData().get("x"));

            game.movePaddle(XDirection.RIGHT);
            assertEquals(276, game.getPaddleData().get("x"));

            game.movePaddle(XDirection.LEFT);
            assertEquals(266, game.getPaddleData().get("x"));

            for (int i = 10; i < 300; i += 10) {
                game.movePaddle(XDirection.RIGHT);
                assertEquals(266 + i, game.getPaddleData().get("x"));
            }

            //Limit -> 556 + 88 = 644 < 650
            game.movePaddle(XDirection.RIGHT);
            assertEquals(556, game.getPaddleData().get("x"));

            for (int i = 10; i <= 550; i += 10) {
                game.movePaddle(XDirection.LEFT);
                assertEquals(556 - i, game.getPaddleData().get("x"));
            }

            //Limit -> 6 - 10 = -4 < 0 -> remains 6
            game.movePaddle(XDirection.LEFT);
            assertEquals(6, game.getPaddleData().get("x"));

        } catch (LevelException e) {
            fail("movePaddle failed");
        }
    }

    @Test
    @Tag("basic")
    public void movePaddle2() {

        try {
            var BALL_INIT_DATA = Map.of("x", 550, "y", 380, "radius", 5,
                    "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);

            Map<String, Map<String, Integer>> _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);
            Method method = GameController.class.getDeclaredMethod("checkCollisionBottom");
            method.setAccessible(true);

            game.nextLevel();

            for (int i = 0; i < 10; i++) {
                assertFalse(game.update());
                assertFalse((boolean) method.invoke(game));
                assertEquals(3, game.getNumLives());
            }

            assertTrue(game.update());
            assertEquals(2, game.getNumLives());
        } catch (LevelException | IOException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            fail("[ERROR] There is some problem with 'setPoints'");
        }
    }

    @Test
    @Tag("advanced")
    void updateTwoMoves() {
        try {
            game.nextLevel();
            game.update();
            var data = game.getBallData();
            assertEquals(302, data.get("x"));
            assertEquals(298, data.get("y"));

            game.update();
            data = game.getBallData();
            assertEquals(304, data.get("x"));
            assertEquals(296, data.get("y"));
        } catch (LevelException e) {
            fail("update failed");
        }
    }

    @Test
    @Tag("basic")
    void restore() {
        try {
            game.nextLevel();
            game.update();
            var data = game.getBallData();
            assertEquals(302, data.get("x"));
            assertEquals(298, data.get("y"));
            game.restore();
            data = game.getBallData();
            assertEquals(300, data.get("x"));
            assertEquals(300, data.get("y"));
            assertEquals(5, data.get("radius"));
            assertEquals(XDirection.RIGHT.getOrientation(), data.get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), data.get("yDirection"));
            assertEquals(2, data.get("speed"));  //indicated in level1.txt
        } catch (LevelException e) {
            fail("restore failed");
        }
    }

    @Test
    @Tag("advanced")
    void updateBreakAllBricks() {
        try {
            var BALL_INIT_DATA = Map.of("x", 550, "y", 32, "radius", 5,
                    "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.UP.getOrientation(), "speed", 2);

            Map<String, Map<String, Integer>> INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, INIT_DATA);
            game.nextLevel();
            game.nextLevel();
            assertFalse(game.isLevelCompleted());
            assertEquals(1, game.getBricks().size());

            game.update();
            assertTrue(game.isLevelCompleted());
            var data = game.getBallData();
            assertEquals(552, data.get("x"));
            assertEquals(30, data.get("y"));
            assertEquals(0, game.getBricks().size());
            assertEquals(100, game.getScore());
            assertEquals(3, game.getNumLives());
        } catch (IOException | LevelException e) {
            fail("update failed");
        }
    }

    @Test
    @Tag("advanced")
    void updateLose1Life() {
        try {
            var BALL_INIT_DATA = Map.of("x", 550, "y", 403, "radius", 5,
                    "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);

            Map<String, Map<String, Integer>> _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);
            game.nextLevel();
            assertTrue(game.update());
            assertFalse(game.isLevelCompleted());
            assertEquals(3, game.getBricks().size());
            assertEquals(2, game.getNumLives());


            var data = game.getBallData();
            assertEquals(550, data.get("x"));
            assertEquals(403, data.get("y"));


        } catch (IOException | LevelException e) {
            fail("update failed");
        }
    }


    @Test
    @Tag("advanced")
    public void checkCollisionPaddle() {
        try {

            //LEFT - Middle --> remains the orientation in x-axis
            Map<String, Integer> BALL_INIT_DATA = Map.of("x", 120, "y", 386, "radius", 5,
                     "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);


            Map<String, Integer> PADDLE_INIT_DATA = Map.of("x", 120, "y", 400, "width", 88,
                    "height", 10, "xDirection", XDirection.RIGHT.getOrientation(), "speed", 10);


            Map<String, Map<String, Integer>> _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);


            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);
            game.nextLevel();

            assertEquals(120, game.getBallData().get("x"));
            assertEquals(386, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(122, game.getBallData().get("x"));
            assertEquals(388, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(124, game.getBallData().get("x"));
            assertEquals(390, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(126, game.getBallData().get("x"));
            assertEquals(392, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(128, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(130, game.getBallData().get("x"));
            assertEquals(396, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(132, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), game.getBallData().get("yDirection"));


            //LEFT - Border --> changes the orientation in x-axis
            BALL_INIT_DATA = Map.of("x", 116, "y", 394, "radius", 5,
                     "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);


            PADDLE_INIT_DATA = Map.of("x", 120, "y", 400, "width", 88,
                    "height", 10, "xDirection", XDirection.RIGHT.getOrientation(), "speed", 10);


            _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);


            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);
            game.nextLevel();

            assertEquals(116, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(118, game.getBallData().get("x"));
            assertEquals(396, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(116, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.LEFT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(114, game.getBallData().get("x"));
            assertEquals(392, game.getBallData().get("y"));
            assertEquals(XDirection.LEFT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), game.getBallData().get("yDirection"));


            //RIGHT - Middle --> remains the orientation in x-axis
            BALL_INIT_DATA = Map.of("x", 200, "y", 394, "radius", 5,
                     "xDirection", XDirection.LEFT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);


            PADDLE_INIT_DATA = Map.of("x", 120, "y", 400, "width", 88,
                    "height", 10, "xDirection", XDirection.RIGHT.getOrientation(), "speed", 10);


            _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);


            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);
            game.nextLevel();

            assertEquals(200, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.LEFT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(198, game.getBallData().get("x"));
            assertEquals(396, game.getBallData().get("y"));
            assertEquals(XDirection.LEFT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(196, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.LEFT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), game.getBallData().get("yDirection"));


            //RIGHT - Border --> changes the orientation in x-axis
            BALL_INIT_DATA = Map.of("x", 210, "y", 394, "radius", 5,
                    "xDirection", XDirection.LEFT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);


            PADDLE_INIT_DATA = Map.of("x", 120, "y", 400, "width", 88, "height", 10,
                     "xDirection", XDirection.RIGHT.getOrientation(), "speed", 10);


            _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);


            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);
            game.nextLevel();

            assertEquals(210, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.LEFT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(208, game.getBallData().get("x"));
            assertEquals(396, game.getBallData().get("y"));
            assertEquals(XDirection.LEFT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.DOWN.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(210, game.getBallData().get("x"));
            assertEquals(394, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), game.getBallData().get("yDirection"));
            game.update();
            assertEquals(212, game.getBallData().get("x"));
            assertEquals(392, game.getBallData().get("y"));
            assertEquals(XDirection.RIGHT.getOrientation(), game.getBallData().get("xDirection"));
            assertEquals(YDirection.UP.getOrientation(), game.getBallData().get("yDirection"));
        } catch (LevelException | IOException e) {
            fail("[ERROR] There is some problem with 'checkCollisionPaddle'");
        }
    }

    @Test
    @Tag("advanced")
    public void checkCollisionScene() {
        try {
            var BALL_INIT_DATA = Map.of("x", 640, "y", 380, "radius", 5,
                    "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);

            Map<String, Map<String, Integer>> _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);

            //Right
            game.nextLevel();

            game.update();
            assertEquals(1, game.getBallData().get("xDirection"));

            game.update();
            assertEquals(1, game.getBallData().get("xDirection"));

            game.update();
            assertEquals(-1, game.getBallData().get("xDirection"));


            //Left
            BALL_INIT_DATA = Map.of("x", 10, "y", 380, "radius", 5,
                     "xDirection", XDirection.LEFT.getOrientation(), "yDirection", YDirection.DOWN.getOrientation(), "speed", 2);

            _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);

            game.nextLevel();

            //8
            game.update();
            assertEquals(-1, game.getBallData().get("xDirection"));

            //6
            game.update();
            assertEquals(-1, game.getBallData().get("xDirection"));

            //4
            game.update();
            assertEquals(-1, game.getBallData().get("xDirection"));

            //2
            game.update();
            assertEquals(-1, game.getBallData().get("xDirection"));

            //0
            game.update();
            assertEquals(+1, game.getBallData().get("xDirection"));


            //Top
            BALL_INIT_DATA = Map.of("x", 10, "y", 4, "radius", 5,
                     "xDirection", XDirection.LEFT.getOrientation(), "yDirection", YDirection.UP.getOrientation(), "speed", 2);

            _INIT_DATA = Map.of("ball", BALL_INIT_DATA,
                    "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);

            game = new GameController("/levels/", UOCanoid.WIDTH, UOCanoid.HEIGHT - 75, _INIT_DATA);

            game.nextLevel();

            //2
            game.update();
            assertEquals(-1, game.getBallData().get("yDirection"));

            //0
            game.update();
            assertEquals(+1, game.getBallData().get("yDirection"));
        } catch (LevelException | IOException e) {
            fail("[ERROR] There is some problem with 'checkCollisionScene'");
        }
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<GameController> ownClass = GameController.class;

        //check attribute fields
        assertEquals(10, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("fileFolder").getModifiers()));
            assertEquals(String.class, (ownClass.getDeclaredField("fileFolder").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("WIDTH").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("WIDTH").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("WIDTH").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("HEIGHT").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("HEIGHT").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("HEIGHT").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("currentLevel").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("currentLevel").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("MAX_LEVELS").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("MAX_LEVELS").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("MAX_LEVELS").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("score").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("score").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("numLives").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("numLives").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("INIT_NUM_LIVES").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("INIT_NUM_LIVES").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("INIT_NUM_LIVES").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("level").getModifiers()));
            assertEquals(Level.class, (ownClass.getDeclaredField("level").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("INIT_DATA").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("INIT_DATA").getModifiers()));
            assertEquals(Map.class, (ownClass.getDeclaredField("INIT_DATA").getType()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
        }
    }

    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<GameController> ownClass = GameController.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        assertEquals(28, ownClass.getDeclaredMethods().length);


        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredConstructor(String.class, int.class, int.class, Map.class).getModifiers()));

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("getFileFolder").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("getFileFolder").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setFileFolder", String.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setFileFolder", String.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getCurrentLevel").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getCurrentLevel").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setCurrentLevel", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setCurrentLevel", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getScore").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getScore").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setScore", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setScore", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getNumLives").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getNumLives").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setNumLives", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setNumLives", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getMAX_LEVELS").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getMAX_LEVELS").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("isGameFinished").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("isGameFinished").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("isLevelCompleted").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("isLevelCompleted").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("hasLost").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("hasLost").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("nextLevel").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("nextLevel").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("loadLevel").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("loadLevel").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getBricks").getModifiers()));
            assertEquals(List.class, ownClass.getDeclaredMethod("getBricks").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getBallData").getModifiers()));
            assertEquals(Map.class, ownClass.getDeclaredMethod("getBallData").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getPaddleData").getModifiers()));
            assertEquals(Map.class, ownClass.getDeclaredMethod("getPaddleData").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("swapBallXDirection").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("swapBallXDirection").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("swapBallYDirection").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("swapBallYDirection").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("checkCollisionBricks").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("checkCollisionBricks").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("checkCollisionScene").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("checkCollisionScene").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("checkCollisionPaddle").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("checkCollisionPaddle").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("checkCollisionBottom").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("checkCollisionBottom").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("movePaddle", XDirection.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("movePaddle", XDirection.class).getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("moveBall").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("moveBall").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("update").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("update").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("restore").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("restore").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}
