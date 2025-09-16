package edu.uoc.uocanoid.model.bricks;

import edu.uoc.uocanoid.model.Entity;

public abstract class Brick extends Entity {
    private int numHitsToBreak;
    private int currentHits = 0;
    private int points;

    protected  Brick (int x, int y, int width,int height,int numHitsToBreak, int points){
        super(x, y, width, height);
        setNumHitsToBreak(numHitsToBreak);
        setPoints(points);
        setCurrentHits(currentHits);

    }

    public int getCurrentHits() {
        return currentHits;
    }

    private void setCurrentHits(int currentHits) {
        if(currentHits < 0){
            this.currentHits = 0;
            return;
        }
         this.currentHits = currentHits;
    }

    public int getNumHitsToBreak() {
        return numHitsToBreak;
    }

    private void setNumHitsToBreak(int numHitsToBreak) {
        if(numHitsToBreak < 1){
            this.numHitsToBreak = 1;
            return;
        }
        this.numHitsToBreak = numHitsToBreak;
    }

    public int getPoints() {
        return points;
    }

    private void setPoints(int points) {
        if (points < 0){
            this.points = 0;
            return;
        }
        this.points = points;
    }

    public void incCurrentHits(){
        if (!isBroken()){
            currentHits ++;
        }
    }

    public boolean isBroken(){
        return currentHits >= numHitsToBreak;
    }

    @Override
    public String toString() {
        return "position: (" + getX() + "," + getY() + "), size: " + getWidth() + "x" + getHeight()
                + ", hits to break: " + numHitsToBreak
                + ", current hits: " + currentHits
                + ", points: " + points;
    }
}
