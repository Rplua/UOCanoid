package edu.uoc.uocanoid.model.levels;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class LevelExceptionTest {

    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<LevelException> ownClass = LevelException.class;
        final Class<Exception> exceptionClass = Exception.class;

        assertTrue(exceptionClass.isAssignableFrom(ownClass));
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<LevelException> ownClass = LevelException.class;

        //check attribute fields
        assertEquals(3, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredField("PARSING_LEVEL_FILE_ERROR").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("PARSING_LEVEL_FILE_ERROR").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("PARSING_LEVEL_FILE_ERROR").getModifiers()));
            assertEquals(String.class, (ownClass.getDeclaredField("PARSING_LEVEL_FILE_ERROR").getType()));

            assertTrue(Modifier.isPublic(ownClass.getDeclaredField("MIN_BRICKS_ERROR").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("MIN_BRICKS_ERROR").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("MIN_BRICKS_ERROR").getModifiers()));
            assertEquals(String.class, (ownClass.getDeclaredField("MIN_BRICKS_ERROR").getType()));

            assertTrue(Modifier.isPublic(ownClass.getDeclaredField("SPEED_ERROR").getModifiers()));
            assertTrue(Modifier.isStatic(ownClass.getDeclaredField("SPEED_ERROR").getModifiers()));
            assertTrue(Modifier.isFinal(ownClass.getDeclaredField("SPEED_ERROR").getModifiers()));
            assertEquals(String.class, (ownClass.getDeclaredField("SPEED_ERROR").getType()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
        }
    }


    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<LevelException> ownClass = LevelException.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        assertEquals(0, ownClass.getDeclaredMethods().length);
        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredConstructor(String.class).getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}
