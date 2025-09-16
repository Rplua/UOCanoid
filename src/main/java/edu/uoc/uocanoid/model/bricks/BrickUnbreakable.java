package edu.uoc.uocanoid.model.bricks;

public class BrickUnbreakable extends Brick {
    private static final int NUM_HITS_TO_BREAK = Integer.MAX_VALUE;
    private static final int POINTS = 0;

    public BrickUnbreakable(int x, int y , int width, int height){
        super(x,y,width,height,NUM_HITS_TO_BREAK,POINTS);
    }
}
