package edu.uoc.uocanoid.model.bricks;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class BrickFactoryTest {

    @Test
    @Tag("basic")
    void getBrickInstance() {
        assertInstanceOf(BrickBasic.class, BrickFactory.getBrickInstance('B', 1, 2, 3, 4));
        assertInstanceOf(BrickAdvanced.class, BrickFactory.getBrickInstance('A', 1, 2, 3, 4));
        assertInstanceOf(BrickUnbreakable.class, BrickFactory.getBrickInstance('U', 1, 2, 3, 4));
        assertNull(BrickFactory.getBrickInstance('C', 1, 2, 3, 4));
    }

    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<BrickFactory> ownClass = BrickFactory.class;

        assertTrue(Modifier.isAbstract(ownClass.getModifiers()));
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<BrickFactory> ownClass = BrickFactory.class;

        //check attribute fields
        assertEquals(0, ownClass.getDeclaredFields().length);
    }

    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<BrickFactory> ownClass = BrickFactory.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        assertEquals(1, ownClass.getDeclaredMethods().length);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredConstructor().getModifiers()));
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getBrickInstance", char.class, int.class, int.class, int.class, int.class).getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredMethod("getBrickInstance", char.class, int.class, int.class, int.class, int.class).getModifiers()));
            assertEquals(Brick.class, ownClass.getDeclaredMethod("getBrickInstance", char.class, int.class, int.class, int.class, int.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}