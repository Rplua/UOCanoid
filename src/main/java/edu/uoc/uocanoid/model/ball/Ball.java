package edu.uoc.uocanoid.ball;

import edu.uoc.uocanoid.model.Entity;
import edu.uoc.uocanoid.model.Movable;
import edu.uoc.uocanoid.model.XDirection;
import edu.uoc.uocanoid.model.YDirection;

public class Ball extends Entity implements Movable {
    private final int INIT_X;
    private final XDirection INIT_X_DIRECTION;
    private final int INIT_Y;
    private final YDirection INIT_Y_DIRECTION;
    private int speed;
    private XDirection xDirection;
    private  YDirection yDirection;

    public Ball(int x, int y, int radius, XDirection xDirection, YDirection yDirection, int speed){
        super();
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        this.speed = speed;
    }

    public int getINIT_X() {
        return INIT_X;
    }

    public XDirection getINIT_X_DIRECTION() {
        return INIT_X_DIRECTION;
    }

    public int getINIT_Y() {
        return INIT_Y;
    }

    public YDirection getINIT_Y_DIRECTION() {
        return INIT_Y_DIRECTION;
    }

    public XDirection getxDirection() {
        return xDirection;
    }

    public void setxDirection(XDirection xDirection) {
        this.xDirection = xDirection;
    }

    public YDirection getyDirection() {
        return yDirection;
    }

    public void setyDirection(YDirection yDirection) {
        this.yDirection = yDirection;
    }

    public void setSpeed()

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void move() {
        setX(getX() + xDirection.getOrientation() * speed);
        setY(getY() + yDirection.getOrientation() * speed) ;
    }

    @Override
    public void setSpeed(int speed) {
        if (speed > 0){
            this.speed  = speed;
        }

    }

    @Override
    public String toString() {
        return "position: (" + getX() + "," + getY() + ")" +
                ", size: " + getWidth() + "x" + getHeight() +
                ", direction: (" +getxDirection() +"," + getyDirection() + ")" +
                ", speed: " + speed;
    }
}
