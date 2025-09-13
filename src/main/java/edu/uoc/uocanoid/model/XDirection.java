package edu.uoc.uocanoid.model;

public enum XDirection {
    LEFT (-1),
    RIGHT (+1) ;
    private int orientation;

    XDirection(int orientation){
        this.orientation = orientation;
    }


    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }


    public  XDirection next(){
        if(this == LEFT){
            return RIGHT;
        }
        return LEFT;
    }

    public  static XDirection getValueByOrientation(int orientation){
        if (orientation == -1){
            return  LEFT;
        }
        if (orientation == +1){
            return  RIGHT;
        }
        return null;
    }




}
