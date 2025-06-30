package edu.uoc.uocanoid.model.paddle;

import edu.uoc.uocanoid.model.Entity;
import edu.uoc.uocanoid.model.Movable;
import edu.uoc.uocanoid.model.XDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {

    private Paddle paddle;

    @BeforeEach
    void setUp() {
        paddle = new Paddle(8, 20, 10, 5, 2);
    }

    @Test
    @Tag("basic")
    void getX() {
        assertEquals(8, paddle.getX());
    }

    @Test
    @Tag("basic")
    void setX() {
        paddle.setX(10);
        assertEquals(10, paddle.getX());

        paddle.setX(-10);
        assertEquals(-10, paddle.getX());

        paddle.setX(0);
        assertEquals(0, paddle.getX());
    }

    @Test
    @Tag("basic")
    void getY() {
        assertEquals(20, paddle.getY());
    }

    @Test
    @Tag("basic")
    void setY() {
        paddle.setY(100);
        assertEquals(100, paddle.getY());

        paddle.setY(-5);
        assertEquals(-5, paddle.getY());

        paddle.setY(0);
        assertEquals(0, paddle.getY());
    }

    @Test
    @Tag("basic")
    void getWidth() {
        assertEquals(10, paddle.getWidth());
    }

    @Test
    @Tag("basic")
    void setWidth() {
        paddle.setWidth(10);
        assertEquals(10, paddle.getWidth());

        paddle.setWidth(-10);
        assertEquals(1, paddle.getWidth());

        paddle.setWidth(50);
        assertEquals(50, paddle.getWidth());

        paddle.setWidth(0);
        assertEquals(1, paddle.getWidth());

        paddle.setWidth(25);
        assertEquals(25, paddle.getWidth());

        paddle.setWidth(1);
        assertEquals(1, paddle.getWidth());
    }

    @Test
    @Tag("basic")
    void getHeight() {
        assertEquals(5, paddle.getHeight());
    }

    @Test
    @Tag("basic")
    void setHeight() {
        paddle.setHeight(10);
        assertEquals(10, paddle.getHeight());

        paddle.setHeight(-10);
        assertEquals(1, paddle.getHeight());

        paddle.setHeight(50);
        assertEquals(50, paddle.getHeight());

        paddle.setHeight(0);
        assertEquals(1, paddle.getHeight());

        paddle.setHeight(25);
        assertEquals(25, paddle.getHeight());

        paddle.setHeight(1);
        assertEquals(1, paddle.getHeight());
    }

    @Test
    @Tag("basic")
    void getSpeed() {
        assertEquals(2, paddle.getSpeed());
    }

    @Test
    @Tag("basic")
    void setSpeed() {
        paddle = new Paddle(8, 20, 10, 5, 12);
        assertEquals(12, paddle.getSpeed());

        paddle = new Paddle(8, 20, 10, 5, -10);
        assertEquals(0, paddle.getSpeed());

        paddle.setSpeed(100);
        assertEquals(100, paddle.getSpeed());

        paddle.setSpeed(0);
        assertEquals(100, paddle.getSpeed());

        paddle.setSpeed(-55);
        assertEquals(100, paddle.getSpeed());
    }

    @Test
    @Tag("advanced")
    void move() {
        paddle.move();
        assertEquals(10, paddle.getX());
        assertEquals(20, paddle.getY());

        paddle.move();
        assertEquals(12, paddle.getX());
        assertEquals(20, paddle.getY());

        paddle.setDirection(XDirection.LEFT);
        paddle.move();
        assertEquals(10, paddle.getX());
        assertEquals(20, paddle.getY());

        paddle.move();
        assertEquals(8, paddle.getX());
        assertEquals(20, paddle.getY());

        paddle.setSpeed(5);
        paddle.move();
        assertEquals(3, paddle.getX());
        assertEquals(20, paddle.getY());
    }

    @Test
    @Tag("basic")
    void getDirection() {
        assertEquals(XDirection.RIGHT, paddle.getDirection());
    }

    @Test
    @Tag("basic")
    void setDirection() {
        paddle.setDirection(XDirection.LEFT);
        assertEquals(XDirection.LEFT, paddle.getDirection());

        paddle.setDirection(XDirection.RIGHT);
        assertEquals(XDirection.RIGHT, paddle.getDirection());
    }

    @Test
    @Tag("basic")
    void testToString() {
        assertEquals("position: (8,20), size: 10x5, direction: RIGHT, speed: 2", paddle.toString());
    }

    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<Paddle> ownClass = Paddle.class;
        final Class<Entity> entityClass = Entity.class;
        final Class<Movable> movableInterface = Movable.class;

        assertTrue(entityClass.isAssignableFrom(ownClass));
        assertTrue(movableInterface.isAssignableFrom(ownClass));
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<Paddle> ownClass = Paddle.class;

        //check attribute fields
        assertEquals(2, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("speed").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("speed").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("direction").getModifiers()));
            assertEquals(XDirection.class, (ownClass.getDeclaredField("direction").getType()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
        }
    }

    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<Paddle> ownClass = Paddle.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        assertEquals(6, ownClass.getDeclaredMethods().length);


        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredConstructor(int.class, int.class, int.class, int.class, int.class).getModifiers()));

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getSpeed").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getSpeed").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setSpeed", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setSpeed", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("move").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("move").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getDirection").getModifiers()));
            assertEquals(XDirection.class, ownClass.getDeclaredMethod("getDirection").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setDirection", XDirection.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setDirection", XDirection.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("toString").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("toString").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}