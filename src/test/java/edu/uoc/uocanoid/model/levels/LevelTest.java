package edu.uoc.uocanoid.model.levels;

import edu.uoc.uocanoid.model.XDirection;
import edu.uoc.uocanoid.model.YDirection;
import edu.uoc.uocanoid.model.ball.Ball;
import edu.uoc.uocanoid.model.bricks.Brick;
import edu.uoc.uocanoid.model.bricks.BrickUnbreakable;
import edu.uoc.uocanoid.model.paddle.Paddle;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    private Map<String, Integer> BALL_INIT_DATA = Map.of("x", 300, "y", 300, "radius", 5,
            "xDirection", XDirection.RIGHT.getOrientation(), "yDirection", YDirection.UP.getOrientation(), "speed", 2);
    private Map<String, Integer> PADDLE_INIT_DATA = Map.of("x", 256, "y", 400, "width", 88, "height", 10,
            "xDirection", XDirection.RIGHT.getOrientation(), "speed", 5);
    private Map<String, Integer> BRICK_INIT_DATA = Map.of("width", 50, "height", 30);
    private Map<String, Map<String, Integer>> INIT_DATA = Map.of("ball", BALL_INIT_DATA,
            "paddle", PADDLE_INIT_DATA, "brick", BRICK_INIT_DATA);


    @Test
    @Tag("basic")
    void isCompleted() {
        try {
            Level level = new Level("/levels/level1.txt", INIT_DATA);
            assertFalse(level.isCompleted());

            level = new Level("/levels/level3.txt", INIT_DATA);
            assertTrue(level.isCompleted());
        } catch (LevelException e) {
            fail("[ERROR] There is some problem with the Level's isCompleted method");
        }
    }

    @Test
    @Tag("basic")
    void getBricks() {
        try {
            Level level = new Level("/levels/level1.txt", INIT_DATA);
            List<Brick> bricks = level.getBricks();

            assertEquals(450, bricks.get(0).getX());
            assertEquals(0, bricks.get(0).getY());
            assertInstanceOf(BrickUnbreakable.class, bricks.get(0));

        } catch (LevelException e) {
            fail("[ERROR] There is some problem with the Level's getBricks method");
        }
    }

    @Test
    @Tag("basic")
    void getBall() {
        try {
            Level level = new Level("/levels/level1.txt", INIT_DATA);
            Ball ball = level.getBall();

            assertEquals(300, ball.getX());
            assertEquals(300, ball.getY());
            assertEquals(5, ball.getWidth());
            assertEquals(5, ball.getHeight());
            assertEquals(XDirection.RIGHT, ball.getXDirection());
            assertEquals(YDirection.UP, ball.getYDirection());
            assertEquals(2, ball.getSpeed());
        } catch (LevelException e) {
            fail("[ERROR] There is some problem with the Level's getBall method");
        }
    }

    @Test
    @Tag("basic")
    void getPaddle() {
        try {
            Level level = new Level("/levels/level1.txt", INIT_DATA);
            Paddle paddle = level.getPaddle();

            assertEquals(256, paddle.getX());
            assertEquals(400, paddle.getY());
            assertEquals(88, paddle.getWidth());
            assertEquals(10, paddle.getHeight());
            assertEquals(XDirection.RIGHT, paddle.getDirection());
            assertEquals(10, paddle.getSpeed());


        } catch (LevelException e) {
            fail("[ERROR] There is some problem with the Level's getPaddle method");
        }
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<Level> ownClass = Level.class;

        //check attribute fields
        assertEquals(4, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("bricks").getModifiers()));
            assertEquals(List.class, (ownClass.getDeclaredField("bricks").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("ball").getModifiers()));
            assertEquals(Ball.class, (ownClass.getDeclaredField("ball").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("paddle").getModifiers()));
            assertEquals(Paddle.class, (ownClass.getDeclaredField("paddle").getType()));
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
        final Class<Level> ownClass = Level.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredConstructor(String.class, Map.class).getModifiers()));

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("parse", String.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("parse", String.class).getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("getFirstNonEmptyLine", BufferedReader.class).getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("getFirstNonEmptyLine", BufferedReader.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("isCompleted").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("isCompleted").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getBricks").getModifiers()));
            assertEquals(List.class, ownClass.getDeclaredMethod("getBricks").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getBall").getModifiers()));
            assertEquals(Ball.class, ownClass.getDeclaredMethod("getBall").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getPaddle").getModifiers()));
            assertEquals(Paddle.class, ownClass.getDeclaredMethod("getPaddle").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}