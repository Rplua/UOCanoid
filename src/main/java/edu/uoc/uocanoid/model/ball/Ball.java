package edu.uoc.uocanoid.model.ball;

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

    public Ball(int x, int y, int radius, XDirection XDirection, YDirection YDirection, int speed){
        super(x, y, radius, radius );
        this.INIT_X = x;
        this.INIT_Y = y;
        this.INIT_X_DIRECTION = XDirection;
        this.INIT_Y_DIRECTION = YDirection;
        this.xDirection = XDirection;
        this.yDirection = YDirection;
        setSpeed(speed);
    }


    public XDirection getXDirection() {
        return xDirection;
    }

    private void setXDirection(XDirection xDirection) {
        this.xDirection = xDirection;
    }

    public void changeXDirection(){
        if(xDirection.equals(XDirection.LEFT) ){
            xDirection = XDirection.RIGHT;
        }else{
            xDirection = XDirection.LEFT;
        }
    }

    public YDirection getYDirection() {
        return yDirection;
    }

    private void setYDirection(YDirection yDirection) {
        this.yDirection = yDirection;
    }



    public void changeYDirection(){
        if (yDirection.equals(YDirection.UP)) {
            yDirection = YDirection.DOWN;
        } else {
            yDirection = YDirection.UP;
        }
    }

    public void restore(){
        setX(INIT_X);
        setY(INIT_Y);
        setXDirection(INIT_X_DIRECTION);
        setYDirection(INIT_Y_DIRECTION);
    }

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
                ", direction: (" +getXDirection() +"," + getYDirection() + ")" +
                ", speed: " + speed;
    }
}
