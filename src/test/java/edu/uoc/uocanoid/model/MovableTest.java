package edu.uoc.uocanoid.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class MovableTest {

    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<Movable> ownClass = Movable.class;
        assertTrue(ownClass.isInterface());
    }

    @Test
    @Tag("sanity")
    public void checkFields(){
        final Class<Movable> ownClass = Movable.class;
        //check attribute fields
        assertEquals(0, ownClass.getDeclaredFields().length);
    }

    @Test
    @Tag("sanity")
    public void checkMethods(){
        final Class<Movable> ownClass = Movable.class;

        assertEquals(3, ownClass.getDeclaredMethods().length);

        try {
            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("move").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("move").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getSpeed").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getSpeed").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setSpeed", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setSpeed", int.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}