package edu.uoc.uocanoid.model.bricks;

/**
 * Brick Simple Factory class.
 *
 * @author David García Solórzano
 * @version 1.0
 */
public abstract class BrickFactory {

    /**
     * Returns a new {@link Brick} object.
     *
     * @param symbol String value of the {@link Brick} object, i.e. 'B' (basic), 'A' (advanced) and 'U' (unbreakable)
     * @param x      Column of the coordinate/position in which the brick is in the board.
     * @param y      Row of the coordinate/position in which the brick is in the board.
     * @param width  Width of the brick to be instantiated.
     * @param height Height of the brick to be instantiated.
     * @return {@link Brick} object.
     */
    public static Brick getBrickInstance(char symbol, int x, int y, int width, int height) {

        return switch (symbol) {
            case 'B' -> new BrickBasic(x, y, width, height);
            case 'A' -> new BrickAdvanced(x, y, width, height);
            case 'U' -> new BrickUnbreakable(x, y, width, height);
            default -> null;
        };
    }
}
