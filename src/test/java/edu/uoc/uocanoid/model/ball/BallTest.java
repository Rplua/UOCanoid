package edu.uoc.uocanoid.model.ball;

import edu.uoc.uocanoid.model.Entity;
import edu.uoc.uocanoid.model.Movable;
import edu.uoc.uocanoid.model.XDirection;
import edu.uoc.uocanoid.model.YDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    private Ball ball;

    @BeforeEach
    void setUp() {
        ball = new Ball(10, 20, 8, XDirection.RIGHT, YDirection.UP,2);
    }

    @Test
    @Tag("advanced")
    void move() {
        ball.move();
        assertEquals(12,ball.getX());
        assertEquals(18,ball.getY());

        ball.changeXDirection();
        ball.move();
        assertEquals(10,ball.getX());
        assertEquals(16,ball.getY());

        ball.changeYDirection();
        ball.move();
        assertEquals(8,ball.getX());
        assertEquals(18,ball.getY());

        ball.move();
        assertEquals(6,ball.getX());
        assertEquals(20,ball.getY());

        ball.move();
        assertEquals(4,ball.getX());
        assertEquals(22,ball.getY());
    }

    @Test
    @Tag("basic")
    void getX() {
        assertEquals(10, ball.getX());
    }

    @Test
    @Tag("basic")
    void setX() {
        ball.setX(10);
        assertEquals(10, ball.getX());

        ball.setX(-10);
        assertEquals(-10, ball.getX());

        ball.setX(0);
        assertEquals(0, ball.getX());
    }

    @Test
    @Tag("basic")
    void getY() {
        assertEquals(20, ball.getY());
    }

    @Test
    @Tag("basic")
    void setY() {
        ball.setY(100);
        assertEquals(100, ball.getY());

        ball.setY(-5);
        assertEquals(-5, ball.getY());

        ball.setY(0);
        assertEquals(0, ball.getY());
    }

    @Test
    @Tag("basic")
    void getWidth() {
        assertEquals(8, ball.getWidth());
    }

    @Test
    @Tag("basic")
    void setWidth() {
        ball.setWidth(10);
        assertEquals(10, ball.getWidth());

        ball.setWidth(-10);
        assertEquals(1, ball.getWidth());

        ball.setWidth(50);
        assertEquals(50, ball.getWidth());

        ball.setWidth(0);
        assertEquals(1, ball.getWidth());

        ball.setWidth(25);
        assertEquals(25, ball.getWidth());

        ball.setWidth(1);
        assertEquals(1, ball.getWidth());
    }

    @Test
    @Tag("basic")
    void getHeight() {
        assertEquals(8, ball.getHeight());
    }

    @Test
    @Tag("basic")
    void setHeight() {
        ball.setHeight(10);
        assertEquals(10, ball.getHeight());

        ball.setHeight(-10);
        assertEquals(1, ball.getHeight());

        ball.setHeight(50);
        assertEquals(50, ball.getHeight());

        ball.setHeight(0);
        assertEquals(1, ball.getHeight());

        ball.setHeight(25);
        assertEquals(25, ball.getHeight());

        ball.setHeight(1);
        assertEquals(1, ball.getHeight());
    }

    @Test
    @Tag("basic")
    void getSpeed() {
        assertEquals(2, ball.getSpeed());
    }

    @Test
    @Tag("basic")
    void setSpeed() {
        ball = new Ball(8, 20, 10, XDirection.LEFT, YDirection.UP, 12);
        assertEquals(12, ball.getSpeed());

        ball = new Ball(8, 20, 10,  XDirection.LEFT, YDirection.UP,-10);
        assertEquals(0, ball.getSpeed());

        ball.setSpeed(27);
        assertEquals(27, ball.getSpeed());

        ball.setSpeed(0);
        assertEquals(27, ball.getSpeed());

        ball.setSpeed(-55);
        assertEquals(27, ball.getSpeed());
    }

    @Test
    @Tag("basic")
    void getXDirection() {
        assertEquals(XDirection.RIGHT, ball.getXDirection());
    }

    @Test
    @Tag("basic")
    void setXDirection() {
        try {
            Method method = Ball.class.getDeclaredMethod("setXDirection", XDirection.class);
            method.setAccessible(true);

            method.invoke(ball, XDirection.LEFT);
            assertEquals(XDirection.LEFT, ball.getXDirection());

            method.invoke(ball, XDirection.RIGHT);
            assertEquals(XDirection.RIGHT, ball.getXDirection());

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            fail("[ERROR] There is some problem with 'setCurrentHits'");
        }
    }

    @Test
    @Tag("basic")
    void changeXDirection() {
        ball.changeXDirection();
        assertEquals(XDirection.LEFT, ball.getXDirection());

        ball.changeXDirection();
        assertEquals(XDirection.RIGHT, ball.getXDirection());
    }

    @Test
    @Tag("basic")
    void getYDirection() {
        assertEquals(YDirection.UP, ball.getYDirection());
    }

    @Test
    @Tag("basic")
    void setYDirection() {
        try {
            Method method = Ball.class.getDeclaredMethod("setYDirection", YDirection.class);
            method.setAccessible(true);

            method.invoke(ball, YDirection.DOWN);
            assertEquals(YDirection.DOWN, ball.getYDirection());

            method.invoke(ball, YDirection.UP);
            assertEquals(YDirection.UP, ball.getYDirection());

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            fail("[ERROR] There is some problem with 'setCurrentHits'");
        }
    }

    @Test
    @Tag("basic")
    void changeYDirection() {
        ball.changeYDirection();
        assertEquals(YDirection.DOWN, ball.getYDirection());

        ball.changeYDirection();
        assertEquals(YDirection.UP, ball.getYDirection());
    }

    @Test
    @Tag("basic")
    void restore() {
        ball.setX(25);
        ball.setY(80);
        ball.changeXDirection();
        ball.changeYDirection();
        assertEquals(25, ball.getX());
        assertEquals(80, ball.getY());
        assertEquals(XDirection.LEFT, ball.getXDirection());
        assertEquals(YDirection.DOWN, ball.getYDirection());

        ball.restore();
        assertEquals(10, ball.getX());
        assertEquals(20, ball.getY());
        assertEquals(XDirection.RIGHT, ball.getXDirection());
        assertEquals(YDirection.UP, ball.getYDirection());
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("position: (10,20), size: 8x8, direction: (RIGHT,UP), speed: 2", ball.toString());
    }


    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<Ball> ownClass = Ball.class;
        final Class<Entity> entityClass = Entity.class;
        final Class<Movable> movableInterface = Movable.class;

        assertTrue(entityClass.isAssignableFrom(ownClass));
        assertTrue(movableInterface.isAssignableFrom(ownClass));
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<Ball> ownClass = Ball.class;

        //check attribute fields
        assertEquals(7, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("speed").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("speed").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("xDirection").getModifiers()));
            assertEquals(XDirection.class, (ownClass.getDeclaredField("xDirection").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("yDirection").getModifiers()));
            assertEquals(YDirection.class, (ownClass.getDeclaredField("yDirection").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("INIT_X").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("INIT_X").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("INIT_X").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("INIT_Y").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("INIT_Y").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("INIT_Y").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("INIT_X_DIRECTION").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("INIT_X_DIRECTION").getModifiers()));
            assertEquals(XDirection.class, (ownClass.getDeclaredField("INIT_X_DIRECTION").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("INIT_Y_DIRECTION").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("INIT_Y_DIRECTION").getModifiers()));
            assertEquals(YDirection.class, (ownClass.getDeclaredField("INIT_Y_DIRECTION").getType()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
        }
    }

    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<Ball> ownClass = Ball.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        assertEquals(11, ownClass.getDeclaredMethods().length);


        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredConstructor(int.class, int.class, int.class, XDirection.class, YDirection.class, int.class).getModifiers()));

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getSpeed").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getSpeed").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setSpeed", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setSpeed", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("move").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("move").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getXDirection").getModifiers()));
            assertEquals(XDirection.class, ownClass.getDeclaredMethod("getXDirection").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setXDirection", XDirection.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setXDirection", XDirection.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("changeXDirection").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("changeXDirection").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getYDirection").getModifiers()));
            assertEquals(YDirection.class, ownClass.getDeclaredMethod("getYDirection").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setYDirection", YDirection.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setYDirection", YDirection.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("changeXDirection").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("changeXDirection").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("restore").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("restore").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("toString").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("toString").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}