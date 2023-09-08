package com.example.froggerclone;

import android.widget.ImageView;

/**
 * The player.
 */
public class Player {

    private int score = 0;
    private int highest_score = 0; // Safe the highest score in case lose before getting to the end
    private float mostProgress = 0;  // Used to only gain score when moving past where you've been.
    private float x;
    private float y;
    private int maxXOffset;
    private int maxY;

    private boolean isRidingLog = false;

    private int lives;
    private ImageView sprite;

    // A pixel upper bound to prevent the player from going past the bar at the top.
    static final int UPPER_BOUND = 440;

    public Player(ImageView sprite) {
        this.x = 0;
        this.y = 0;
        this.sprite = sprite;
        this.maxXOffset = 540;  // Pixel 4 width / 2  (the player will start centered)
        this.maxY = 2214;  // Pixel 4 height
        this.lives = 3;
    }

    public Player(ImageView sprite, int maxXOffset, int maxY) {
        this(sprite);
        this.maxXOffset = maxXOffset;
        this.maxY = maxY;
    }

    public Player(ImageView sprite, int maxXOffset, int maxY, int lives) {
        this(sprite, maxXOffset, maxY);
        this.lives = lives;
    }

    /**
     * Getter for the player's position. The player's start position is considered (0, 0).
     *
     * @return An array with the x and y player offset.
     */
    public float[] getPosition() {
        return new float[]{this.x, this.y};
    }

    public int getSpriteHeight() {
        return sprite.getHeight();
    }

    /**
     * Setter for the player's position. Will prevent setting if it will put the player offscreen.
     *
     * @param x - The x position to set.
     * @param y - The y position to set.  Positive is down, negative is up.
     */
    public void setPosition(float x, float y) {
        // Check if the sprite will be offscreen with the new position
        int halfWidth = sprite.getWidth() / 2;
        if (x + halfWidth < maxXOffset
                && x - halfWidth > -maxXOffset
                && y >= -maxY + UPPER_BOUND
                && y <= 0
        ) {
            this.x = x;
            this.y = y;
        }

        sprite.setTranslationX(this.x);
        sprite.setTranslationY(this.y);

        if (this.y < mostProgress) {
            mostProgress = this.y;
            // This contains all the information about the score to reward for each tile type.
            switch (getTileType()) {
            case "road":
                score += 10;
                break;
            case "safe":
                score += 5;
                break;
            case "river":
                score += 20;
                break;
            case "goal":
                score += 50;
                break;
            default:
                break;
            }
            highest_score = score;
        }
    }
    public void setHighest_score(int highest_score) {this.highest_score = highest_score;}
    public void setScore(int score) {this.score = score;}
    /**
     * Get the type of tile that the player is currently on.
     *
     * @return the tile type as a string. This may be changed to a tile class later.
     */
    public String getTileType() {
        int height = sprite.getHeight();  // Tiles should be multiples of the player's height.
        // The layout of the level will be like:
        // - 1 tile  = safe (starting tile)
        // - 5 tiles = road
        // - 1 tile  = safe
        // - 4 tiles = river
        // - 2 tiles = goal

        int tileIndex = 1;
        if (y > -height) {
            return "start";
        }

        tileIndex += 5;
        if (y > -height * tileIndex) {
            return "road";
        }

        tileIndex += 1;
        if (y > -height * tileIndex) {
            return "safe";
        }

        tileIndex += 4;
        if (y > -height * tileIndex) {
            return "river";
        }

        return "goal";
    }

    public int getScore() {
        return score;
    }
    public int getHighest_score() { return highest_score; }

    public void loseLife() {
        setPosition(0, 0);
        lives -= 1;
        mostProgress = 0;
        highest_score = score > highest_score ? score : highest_score;
        score = 0;

    }

    int getLives() {
        return this.lives;
    }
    void handleInput(int keyCode) {
        // Whenever a directional input is pressed, try to move the player.
        // The player size determines how far it moves in a step-
        // the player should be the same size as a tile.
        int movementStep = sprite.getHeight();

        switch (keyCode) {
        case 19:  // UP
            setPosition(x, y - movementStep);
            break;
        case 20:  // DOWN
            setPosition(x, y + movementStep);
            break;
        case 21:  // LEFT
            setPosition(x - movementStep, y);
            break;
        case 22:  // RIGHT
            setPosition(x + movementStep, y);
            break;
        default:
            break;
        }
    }

    public void setIsRidingLog(boolean value) {
        isRidingLog = value;
    }
}
