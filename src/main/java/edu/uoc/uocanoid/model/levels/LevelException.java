package edu.uoc.uocanoid.model.levels;

public class LevelException extends Exception {

    static final String MIN_BRICKS_ERROR = "[ERROR] The level must have one brick at least!!";
    static final String PARSING_LEVEL_FILE_ERROR = "[ERROR] It was a problem while reading the level file!!";
    static final String SPEED_ERROR = "[ERROR] Speed must be equal to or greater than 1!!";

    LevelException(String message) {}


}
