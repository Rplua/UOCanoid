package edu.uoc.uocanoid.model.bricks;

public class BrickAdvanced extends Brick {
    private static final int NUM_HITS_TO_BREAK = 2;
    private static final int POINTS = 200;

    public BrickAdvanced(int x,int y, int width,int height){
        super(x,y,width,height,NUM_HITS_TO_BREAK,POINTS);
    }
}
