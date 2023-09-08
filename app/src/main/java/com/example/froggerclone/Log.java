package com.example.froggerclone;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Log extends MovingEntity implements Updatable, Collidable {

    public Log(ImageView spriteSlot, String type, boolean goLeft, Drawable[] images) {
        super(spriteSlot, goLeft);
        switch (type) {
        case "d_log":
            size = 2;
            speed = 2;
            spriteSlot.setImageDrawable(images[1]);
            break;
        case "g_log":
            size = 1;
            speed = 3;
            spriteSlot.setImageDrawable(images[2]);
            break;
        case "b_log":
            size = 3;
            speed = 1;
            spriteSlot.setImageDrawable(images[3]);
            break;
        default:
            size = 1;
            speed = 2;
            spriteSlot.setImageDrawable(images[0]);
        }
    }

    public Log(
            ImageView spriteSlot,
            String type,
            boolean goLeft,
            Drawable[] images,
            int slotTileNum,
            int slotHeight
    ) {
        this(spriteSlot, type, goLeft, images);
        y = slotTileNum * slotHeight;
        System.out.println(y);
    }

    public float getTranslation() {
        return speed * direction * 2;
    }

    public void update() {
        xOffset += getTranslation();
        if (Math.abs(xOffset) > maxXOffset) {
            xOffset = maxXOffset * -direction;
        }
        sprite.setTranslationX(xOffset);
    }

    @Override
    public boolean isCollidingWithPlayer(Player player) {
        // Depends on player sprite height because logs should be the same height as the player.
        int playerSize = player.getSpriteHeight();
        int width = playerSize * this.size;
        float logLBound = this.xOffset - width / 2;
        float logRBound = this.xOffset + width / 2;
        float logTBound = this.y - playerSize;
        float logBBound = this.y;

        float playerLBound = player.getPosition()[0] - playerSize / 2;
        float playerRBound = player.getPosition()[0] + playerSize / 2;
        float playerTBound = player.getPosition()[1] - playerSize;
        float playerBBound = player.getPosition()[1];

        boolean overlapH = (playerLBound < logRBound && playerRBound > logLBound);
        boolean overlapV = (playerBBound > logTBound && playerTBound < logBBound);
        return overlapH && overlapV;
    }

    @Override
    public void handleCollisionWithPlayer(Player player) {
        if (!isCollidingWithPlayer(player)) {
            return;
        }
        float[] playerPos = player.getPosition();
        player.setPosition(playerPos[0] + getTranslation(), playerPos[1]);

        // If trying to move the player failed, the player was unable to be moved offscreen.
        if (player.getPosition()[0] == playerPos[0]) {
            player.loseLife();
        }
    }
}
