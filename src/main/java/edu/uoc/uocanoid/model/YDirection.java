package edu.uoc.uocanoid.model;

public enum YDirection {
    UP(-1),
    DOWN(+1);
    private int orientation;

    YDirection(int orientation){
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public YDirection next(){
        if(this == UP){
            return  DOWN;
        }
        return UP;
    }

    public static YDirection getValueByOrientation(int orientation){
        if (orientation == -1){
            return  DOWN;
        }
        if(orientation == + 1){
            return UP;
        }
        return null;
    }
}
