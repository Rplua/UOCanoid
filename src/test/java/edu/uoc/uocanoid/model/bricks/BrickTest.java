package edu.uoc.uocanoid.model.bricks;

import edu.uoc.uocanoid.model.Entity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class BrickTest {


    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<Brick> brickClass = Brick.class;
        final Class<Entity> entityClass = Entity.class;

        assertTrue(Modifier.isAbstract(brickClass.getModifiers()));
        assertTrue(entityClass.isAssignableFrom(brickClass));
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<Brick> ownClass = Brick.class;

        //check attribute fields
        assertEquals(3, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("numHitsToBreak").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("numHitsToBreak").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("currentHits").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("currentHits").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("points").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("points").getType()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
        }
    }

    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<Brick> ownClass = Brick.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        assertEquals(9, ownClass.getDeclaredMethods().length);


        try {
            assertTrue(Modifier.isProtected(ownClass.getDeclaredConstructor(int.class, int.class, int.class, int.class, int.class, int.class).getModifiers()));

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getNumHitsToBreak").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getNumHitsToBreak").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setNumHitsToBreak", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setNumHitsToBreak", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getCurrentHits").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getCurrentHits").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setCurrentHits", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setCurrentHits", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("incCurrentHits").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("incCurrentHits").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("isBroken").getModifiers()));
            assertEquals(boolean.class, ownClass.getDeclaredMethod("isBroken").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getPoints").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getPoints").getReturnType());

            assertTrue(Modifier.isPrivate(ownClass.getDeclaredMethod("setPoints", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setPoints", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("toString").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("toString").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}
