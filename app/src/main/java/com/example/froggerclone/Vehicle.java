package com.example.froggerclone;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Vehicle extends MovingEntity implements Updatable, Collidable {

    public Vehicle(ImageView spriteSlot, String type, boolean goLeft, Drawable[] images) {
        super(spriteSlot, goLeft);
        switch (type) {
        case "md":
            size = 2;
            speed = 2;
            spriteSlot.setImageDrawable(images[1]);
            break;
        case "lg":
            size = 3;
            speed = 1;
            spriteSlot.setImageDrawable(images[2]);
            break;
        default:
            size = 1;
            speed = 3;
            spriteSlot.setImageDrawable(images[0]);
        }
    }

    public Vehicle(
        ImageView spriteSlot,
        String type,
        boolean goLeft,
        Drawable[] images,
        int slotTileNum,
        int slotHeight
    ) {
        this(spriteSlot, type, goLeft, images);
        y = slotTileNum * slotHeight;
    }

    public void update() {
        xOffset += speed * direction * 5;

        if (Math.abs(xOffset) > maxXOffset) {
            xOffset = maxXOffset * -direction;  // Reset to other side of screen
        }

        sprite.setTranslationX(xOffset);
    }

    public boolean isCollidingWithPlayer(Player player) {
        // Depends on player sprite height because vehicles should be the same height as the player.
        int playerSize = player.getSpriteHeight();
        int width = playerSize * this.size;
        float vehicleLBound = this.xOffset - width / 2;
        float vehicleRBound = this.xOffset + width / 2;
        float vehicleTBound = this.y - playerSize;
        float vehicleBBound = this.y;

        float playerLBound = player.getPosition()[0] - playerSize / 2;
        float playerRBound = player.getPosition()[0] + playerSize / 2;
        float playerTBound = player.getPosition()[1] - playerSize;
        float playerBBound = player.getPosition()[1];

        boolean overlapH = (playerLBound < vehicleRBound && playerRBound > vehicleLBound);
        boolean overlapV = (playerBBound > vehicleTBound && playerTBound < vehicleBBound);
        return overlapH && overlapV;
    }

    public void handleCollisionWithPlayer(Player player) {
        if (isCollidingWithPlayer(player)) {
            player.loseLife();
        }
    }
}
