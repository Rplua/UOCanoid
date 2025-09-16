package edu.uoc.uocanoid.model;

public abstract class Entity {
    private int height;
    private int width;
    private int x;
    private int y;

    protected Entity(int x, int y, int width, int height) {
        setWidth(width);
        setHeight(height);
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height < 1){
            this.height = 1;
            return;
        }
        this.height = height;

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width < 1){
            this.width = 1;
            return;
        }
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "position:" + "(" + getX() +"," + getY() + ")" + "size:" + getWidth()+"x"+ getHeight();
    }
}
