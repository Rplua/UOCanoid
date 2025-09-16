package edu.uoc.uocanoid.model.paddle;

import edu.uoc.uocanoid.model.Entity;
import edu.uoc.uocanoid.model.Movable;
import edu.uoc.uocanoid.model.XDirection;

public class Paddle extends Entity implements Movable {
    private XDirection direction;
    private int speed;

    public Paddle(int x, int y, int width, int height, int speed){
        super(x, y, width, height);
        this.direction = XDirection.RIGHT;
        setSpeed(speed);
    }

    public XDirection getDirection() {
        return direction;
    }

    public void setDirection(XDirection direction) {
        this.direction = direction;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        if (speed > 0){
            this.speed = speed;
        }
    }

    @Override
    public void move(){
        setX(getX() + direction.getOrientation()*speed);
    }

    @Override
    public String toString() {
        return "position: (" + getX() + "," + getY() + ")" +
                ", size: " + getWidth() + "x" + getHeight() +
                ", direction: " + direction +
                ", speed: " + speed;
    }

}
