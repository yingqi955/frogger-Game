package com.example.froggerclone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogUnitTest {

    @Test
    public void logPosition_wrapsAboveMax() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        Log log = new Log(sprite, "default", false, images);

        // The log is going right so going above the max offset should set to -maxOffset
        log.setXOffset(log.getMaxXOffset() + 1);
        log.update();

        assertEquals(log.getXOffset(), -log.getMaxXOffset(), 1);
    }

    @Test
    public void logPosition_wrapsBelowMax() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        Log log = new Log(sprite, "default", true, images);

        // The log is going LEFT so going above the max offset should set to -maxOffset
        log.setXOffset(-log.getMaxXOffset() - 1);
        log.update();

        assertEquals(log.getXOffset(), log.getMaxXOffset(), 1);

    }

    @Test
    public void logSize_basedOnType() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        Log log = new Log(sprite, "g_log", false, images);
        assertEquals(log.getSize(), 1);
        Log log2 = new Log(sprite, "d_log", false, images);
        assertEquals(log2.getSize(), 2);
        Log log3 = new Log(sprite, "b_log", false, images);
        assertEquals(log3.getSize(), 3);
    }

    @Test
    public void logSpeed_basedOnType() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        Log log = new Log(sprite, "d_log", false, images);
        assertEquals(log.getSpeed(), 2, 0.1);
        Log log2 = new Log(sprite, "g_log", false, images);
        assertEquals(log2.getSpeed(), 3, 0.1);
        Log log3 = new Log(sprite, "b_log", false, images);
        assertEquals(log3.getSpeed(), 1, 0.1);
    }

    @Test
    public void logUpdate_basedOnSpeed() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        Log log = new Log(sprite, "default", false, images);

        float posDifference = log.getXOffset();
        log.update();
        posDifference = log.getXOffset() - posDifference;
        assertEquals(posDifference, 4, 0.1);

        Log log2 = new Log(sprite, "g_log", false, images);
        posDifference = log2.getXOffset();
        log2.update();
        posDifference = log2.getXOffset() - posDifference;
        assertEquals(posDifference, 6, 0.1);

        Log log3 = new Log(sprite, "b_log", false, images);
        posDifference = log3.getXOffset();
        log3.update();
        posDifference = log3.getXOffset() - posDifference;
        assertEquals(posDifference, 2, 0.1);
    }

    @Test
    public void logUpdate_basedOnDirection() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        Log log = new Log(sprite, "default", false, images);
        Log log2 = new Log(sprite, "default", true, images);

        float posBefore = log.getXOffset();
        log.update();
        assertEquals(log.getXOffset(), posBefore + 4, 0.1);

        posBefore = log2.getXOffset();
        log2.update();
        assertEquals(log2.getXOffset(), posBefore - 4, 0.1);
    }

    @Test
    public void logCollide_handleCollisionWithPlayer() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);

        ImageView logSprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        // This log is overlapping the player, it starts at 0, 0.
        Log log = new Log(logSprite, "default", false, images, 0, 50);

        float playerX = player.getPosition()[0];
        log.handleCollisionWithPlayer(player);
        assertEquals(playerX + log.getTranslation(), player.getPosition()[0], 0.1);
    }

    @Test
    public void logCollide_handleCollisionWithPlayer_checksCollision() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);
        player.setPosition(0, -300);

        ImageView logSprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        // This log should not be overlapping the player, since the player is moved from 0, 0.
        Log log = new Log(logSprite, "default", false, images, 0, 50);

        float playerX = player.getPosition()[0];
        log.handleCollisionWithPlayer(player);
        assertEquals(player.getPosition()[0], playerX, 0.1);
    }

    @Test
    public void logCollide_isCollidingWithPlayer_handles_g_log() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);

        ImageView logSprite = mock();
        Drawable[] images = {mock(), mock(), mock(), mock()};
        // This log is overlapping the player, it starts at 0, 0.
        Log log = new Log(logSprite, "g_log", false, images, 0, 50);
        assertTrue(log.isCollidingWithPlayer(player));

        log.setXOffset(50);
        assertFalse(log.isCollidingWithPlayer(player));
    }

    @Test
    public void logCollide_isCollidingWithPlayer_handles_d_log() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);

        ImageView logSprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        // This vehicle is overlapping the player, it starts at 0, 0.
        Log log = new Log(logSprite, "d_log", false, images, 0, 50);
        assertTrue(log.isCollidingWithPlayer(player));

        log.setXOffset(50);
        assertTrue(log.isCollidingWithPlayer(player));

        log.setXOffset(75);
        assertFalse(log.isCollidingWithPlayer(player));
    }
}