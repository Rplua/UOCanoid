package edu.uoc.uocanoid.model.bricks;

public class BrickBasic extends Brick{
    private static final int NUM_HITS_TO_BREAK = 1;
    private static final int POINTS = 100;


    public BrickBasic(int x, int y, int width, int height) {
        super(x,y,width,height,NUM_HITS_TO_BREAK,POINTS);
    }
}
