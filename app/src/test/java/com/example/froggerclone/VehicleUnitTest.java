package com.example.froggerclone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleUnitTest {

    @Test
    public void vehiclePosition_wrapsAboveMax() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        Vehicle vehicle = new Vehicle(sprite, "sm", false, images);

        // The vehicle is going right so going above the max offset should set to -maxOffset
        vehicle.setXOffset(vehicle.getMaxXOffset() + 1);
        vehicle.update();

        assertEquals(vehicle.getXOffset(), -vehicle.getMaxXOffset(), 1);
    }

    @Test
    public void vehiclePosition_wrapsBelowMax() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        Vehicle vehicle = new Vehicle(sprite, "sm", true, images);

        // The vehicle is going LEFT so going above the max offset should set to -maxOffset
        vehicle.setXOffset(-vehicle.getMaxXOffset() - 1);
        vehicle.update();

        assertEquals(vehicle.getXOffset(), vehicle.getMaxXOffset(), 1);

    }

    @Test
    public void vehicleSize_basedOnType() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        Vehicle vehicle = new Vehicle(sprite, "sm", false, images);
        assertEquals(vehicle.getSize(), 1);
        Vehicle vehicle2 = new Vehicle(sprite, "md", false, images);
        assertEquals(vehicle2.getSize(), 2);
        Vehicle vehicle3 = new Vehicle(sprite, "lg", false, images);
        assertEquals(vehicle3.getSize(), 3);
    }

    @Test
    public void vehicleSpeed_basedOnType() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        Vehicle vehicle = new Vehicle(sprite, "sm", false, images);
        assertEquals(vehicle.getSpeed(), 3, 0.1);
        Vehicle vehicle2 = new Vehicle(sprite, "md", false, images);
        assertEquals(vehicle2.getSpeed(), 2, 0.1);
        Vehicle vehicle3 = new Vehicle(sprite, "lg", false, images);
        assertEquals(vehicle3.getSpeed(), 1, 0.1);
    }

    @Test
    public void vehicleUpdate_basedOnSpeed() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        Vehicle vehicle = new Vehicle(sprite, "sm", false, images);

        float posDifference = vehicle.getXOffset();
        vehicle.update();
        posDifference = vehicle.getXOffset() - posDifference;
        assertEquals(posDifference, 15, 0.1);

        Vehicle vehicle2 = new Vehicle(sprite, "md", false, images);
        posDifference = vehicle2.getXOffset();
        vehicle2.update();
        posDifference = vehicle2.getXOffset() - posDifference;
        assertEquals(posDifference, 10, 0.1);

        Vehicle vehicle3 = new Vehicle(sprite, "lg", false, images);
        posDifference = vehicle3.getXOffset();
        vehicle3.update();
        posDifference = vehicle3.getXOffset() - posDifference;
        assertEquals(posDifference, 5, 0.1);
    }

    @Test
    public void vehicleUpdate_basedOnDirection() {
        ImageView sprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        Vehicle vehicle = new Vehicle(sprite, "sm", false, images);
        Vehicle vehicle2 = new Vehicle(sprite, "sm", true, images);

        float posBefore = vehicle.getXOffset();
        vehicle.update();
        assertEquals(vehicle.getXOffset(), posBefore + 15, 0.1);

        posBefore = vehicle2.getXOffset();
        vehicle2.update();
        assertEquals(vehicle2.getXOffset(), posBefore - 15, 0.1);
    }

    @Test
    public void vehicleCollide_handleCollisionWithPlayer() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);

        ImageView vehicleSprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        // This vehicle is overlapping the player, it starts at 0, 0.
        Vehicle vehicle = new Vehicle(vehicleSprite, "sm", false, images, 0, 50);

        vehicle.handleCollisionWithPlayer(player);
        assertEquals(2, player.getLives());
    }

    @Test
    public void vehicleCollide_handleCollisionWithPlayer_checksCollision() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);
        player.setPosition(0, -300);

        ImageView vehicleSprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        // This vehicle should not be overlapping the player, since the player is moved from 0, 0.
        Vehicle vehicle = new Vehicle(vehicleSprite, "sm", false, images, 0, 50);

        vehicle.handleCollisionWithPlayer(player);
        assertEquals(3, player.getLives());
    }

    @Test
    public void vehicleCollide_isCollidingWithPlayer_handles_sm_vehicle() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);

        ImageView vehicleSprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        // This vehicle is overlapping the player, it starts at 0, 0.
        Vehicle vehicle = new Vehicle(vehicleSprite, "sm", false, images, 0, 50);
        assertTrue(vehicle.isCollidingWithPlayer(player));

        vehicle.setXOffset(50);
        assertFalse(vehicle.isCollidingWithPlayer(player));
    }

    @Test
    public void vehicleCollide_isCollidingWithPlayer_handles_md_vehicle() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);

        ImageView vehicleSprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        // This vehicle is overlapping the player, it starts at 0, 0.
        Vehicle vehicle = new Vehicle(vehicleSprite, "md", false, images, 0, 50);
        assertTrue(vehicle.isCollidingWithPlayer(player));

        vehicle.setXOffset(50);
        assertTrue(vehicle.isCollidingWithPlayer(player));

        vehicle.setXOffset(75);
        assertFalse(vehicle.isCollidingWithPlayer(player));
    }

    @Test
    public void vehicleCollide_isCollidingWithPlayer_handles_lg_vehicle() {
        ImageView playerSprite = mock();
        when(playerSprite.getHeight()).thenReturn(50);
        Player player = new Player (playerSprite, 100, 2214, 3);

        ImageView vehicleSprite = mock();
        Drawable[] images = {mock(), mock(), mock()};
        // This vehicle is overlapping the player, it starts at 0, 0.
        Vehicle vehicle = new Vehicle(vehicleSprite, "lg", false, images, 0, 50);
        assertTrue(vehicle.isCollidingWithPlayer(player));

        vehicle.setXOffset(50);
        assertTrue(vehicle.isCollidingWithPlayer(player));

        vehicle.setXOffset(75);
        assertTrue(vehicle.isCollidingWithPlayer(player));

        vehicle.setXOffset(99);
        assertTrue(vehicle.isCollidingWithPlayer(player));

        vehicle.setXOffset(100);
        assertFalse(vehicle.isCollidingWithPlayer(player));
    }

}
