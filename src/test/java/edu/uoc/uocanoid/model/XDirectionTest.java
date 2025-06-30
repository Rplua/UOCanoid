package edu.uoc.uocanoid.model;

import edu.uoc.uocanoid.model.bricks.Brick;
import edu.uoc.uocanoid.model.paddle.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class XDirectionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    @Tag("basic")
    void getOrientation() {
        assertEquals(-1, XDirection.LEFT.getOrientation());
        assertEquals(+1, XDirection.RIGHT.getOrientation());
    }

    @Test
    @Tag("basic")
    void setOrientation(){
        try {
            Method method = XDirection.class.getDeclaredMethod("setOrientation", int.class);
            method.setAccessible(true);

            method.invoke(XDirection.LEFT, 2);
            assertEquals(2, XDirection.LEFT.getOrientation());

            //restore ??
            method.invoke(XDirection.LEFT, -1);
            assertEquals(-1, XDirection.LEFT.getOrientation());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            fail("[ERROR] There is some problem with 'setOrientation'");
        }
    }

    @Test
    @Tag("basic")
    void next() {
        assertEquals(XDirection.RIGHT, XDirection.LEFT.next());
        assertEquals(XDirection.LEFT, XDirection.RIGHT.next());
    }

    @Test
    @Tag("basic")
    void getValueByOrientation(){
        assertEquals(XDirection.RIGHT, XDirection.getValueByOrientation(+1));
        assertEquals(XDirection.LEFT, XDirection.getValueByOrientation(-1));
        assertNull(XDirection.getValueByOrientation(2));
    }

    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<XDirection> ownClass = XDirection.class;

        assertTrue(ownClass.isEnum());
    }

    @Test
    @Tag("sanity")
    public void checkFields() {
        final Class<XDirection> ownClass = XDirection.class;

        //check attribute fields: 1 + 2 enum's values + "$VALUES"
        assertEquals(4, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("orientation").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("orientation").getType()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
        }
    }

    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<XDirection> ownClass = XDirection.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        //Includes inherited methods, such as "values()"
        assertEquals(7, ownClass.getDeclaredMethods().length);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getOrientation").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getOrientation").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setOrientation", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setOrientation", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("next").getModifiers()));
            assertEquals(XDirection.class, ownClass.getDeclaredMethod("next").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getValueByOrientation", int.class).getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredMethod("getValueByOrientation", int.class).getModifiers()));
            assertEquals(XDirection.class, ownClass.getDeclaredMethod("getValueByOrientation", int.class).getReturnType());


        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}