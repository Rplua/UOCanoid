package edu.uoc.uocanoid.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    @Tag("sanity")
    public void checkClass() {
        final Class<Entity> entityClass = Entity.class;

        assertTrue(Modifier.isAbstract(entityClass.getModifiers()));
    }

    @Test
    @Tag("sanity")
    void checkFields() {
        final Class<Entity> ownClass = Entity.class;

        //check attribute fields
        assertEquals(4, ownClass.getDeclaredFields().length);

        try {
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("x").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("x").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("y").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("y").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("width").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("width").getType()));
            assertTrue(Modifier.isPrivate(ownClass.getDeclaredField("height").getModifiers()));
            assertEquals(int.class, (ownClass.getDeclaredField("height").getType()));
        } catch (NoSuchFieldException e) {
            fail("[ERROR] There is some problem with the definition of the attributes");
        }
    }

    @Test
    @Tag("sanity")
    public void checkMethodsSanity() {
        final Class<Entity> ownClass = Entity.class;

        //check constructors
        assertEquals(1, ownClass.getDeclaredConstructors().length);

        assertEquals(9, ownClass.getDeclaredMethods().length);


        try {
            assertTrue(Modifier.isProtected(ownClass.getDeclaredConstructor(int.class, int.class, int.class, int.class).getModifiers()));

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getX").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getX").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setX", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setX", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getY").getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setY", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getWidth").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getWidth").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setWidth", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setWidth", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("getHeight").getModifiers()));
            assertEquals(int.class, ownClass.getDeclaredMethod("getHeight").getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("setHeight", int.class).getModifiers()));
            assertEquals(void.class, ownClass.getDeclaredMethod("setHeight", int.class).getReturnType());

            assertTrue(Modifier.isPublic(ownClass.getDeclaredMethod("toString").getModifiers()));
            assertEquals(String.class, ownClass.getDeclaredMethod("toString").getReturnType());
        } catch (NoSuchMethodException e) {
            fail("[ERROR] There is some problem with the definition of the methods");
        }
    }
}