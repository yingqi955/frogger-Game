package com.example.froggerclone;

import android.widget.ImageView;

/**
 * Base entity class for all moving elements in the game.
 */
public abstract class MovingEntity {
    protected final ImageView sprite;
    protected float speed;
    protected float xOffset;  // Distance from the center
    protected float y;
    protected int size;
    protected final float maxXOffset;  // Should be screen width + some buffer.
    protected final int direction;  // This will be -1 to move left, or positive 1 to move right

    protected MovingEntity(ImageView sprite, boolean goLeft) {
        xOffset = 0;
        y = 0;
        this.sprite = sprite;
        direction = goLeft ? -1 : 1;
        this.maxXOffset = 540 + 200;  // Pixel 4 width / 2
    }
    public float getSpeed() {
        return speed;
    }

    public float getXOffset() {
        return xOffset;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public int getSize() {
        return size;
    }


    public float getMaxXOffset() {
        return maxXOffset;
    }


}
