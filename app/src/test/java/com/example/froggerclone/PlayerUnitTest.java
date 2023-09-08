package com.example.froggerclone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PlayerUnitTest {

    @Test
    public void playerPosition_defaultsToOrigin() {
        ImageView sprite = mock();
        Player player = new Player(sprite);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);
    }

    @Test
    public void player_setPosition_disallowsOffscreen() {
        ImageView sprite = mock();
        Player player = new Player(sprite);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        // An invalid set should result in no change.
        player.setPosition(-10, 9000);
        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);
    }

    @Test
    public void player_setPosition_allowsOnScreen() {
        ImageView sprite = mock();
        Player player = new Player(sprite, 100, 1000);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        // A valid set should result in the player's position changing.
        player.setPosition(-10, -100);
        assertEquals( -10, player.getPosition()[0], 0.0);
        assertEquals(-100, player.getPosition()[1], 0.0);
    }

    @Test
    public void player_setPosition_moveHorizontallyUpToMaxXOffset() {
        ImageView sprite = mock();
        Player player = new Player(sprite, 100, 1000);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        // A move to 99 or -99 should result in the player's position changing.
        player.setPosition(-99, -100);
        assertEquals( -99, player.getPosition()[0], 0.0);
        assertEquals(-100, player.getPosition()[1], 0.0);

        // A move to 99 or -99 should result in the player's position changing.
        player.setPosition(99, -100);
        assertEquals( 99, player.getPosition()[0], 0.0);
        assertEquals(-100, player.getPosition()[1], 0.0);

        player.setPosition(0, 0);

        // A move to 100 or -100 should not.
        player.setPosition(-100, -100);
        assertEquals( 0, player.getPosition()[0], 0.0);
        assertEquals(0, player.getPosition()[1], 0.0);

        player.setPosition(100, -100);
        assertEquals( 0, player.getPosition()[0], 0.0);
        assertEquals(0, player.getPosition()[1], 0.0);
    }
    @Test
    public void player_setPosition_moveVerticallyUpToMaxYOffset() {
        ImageView sprite = mock();
        Player player = new Player(sprite, 100, 2000);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        // A move to -451 should result in the player's position changing up.
        player.setPosition(-99, -451);
        assertEquals( -99, player.getPosition()[0], 0.0);
        assertEquals(-451, player.getPosition()[1], 0.0);

        // A move to -200 should result in the player's position changing up.
        player.setPosition(-99, -200);
        assertEquals( -99, player.getPosition()[0], 0.0);
        assertEquals(-200, player.getPosition()[1], 0.0);

        player.setPosition(0, 0);

        // A move to 2000 or -2000 should not.
        player.setPosition(-100, -2000);
        assertEquals( 0, player.getPosition()[0], 0.0);
        assertEquals(0, player.getPosition()[1], 0.0);

        player.setPosition(-100, 2000);
        assertEquals( 0, player.getPosition()[0], 0.0);
        assertEquals(0, player.getPosition()[1], 0.0);
    }
    @Test
    public void player_setPosition_moveToRiver() {
        ImageView sprite = mock();
        Player player = new Player(sprite, 100, 2214);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        // A move to -1750 should result in the player's position being located on the River.
        player.setPosition(-99, -1750);

        if ((Math.abs(player.getPosition()[1]) < 2214) &&
                (Math.abs(player.getPosition()[1]) >= 1700)) {
            System.out.print("In Bound of River \n");
        }
        assertEquals( -99, player.getPosition()[0], 0.0);
        assertEquals(-1750, player.getPosition()[1], 0.0);

        // A move to -1500 should not result in the player's position being located on the River.
        player.setPosition(-99, -1500);
        if ((Math.abs(player.getPosition()[1]) >= 2214) ||
                (Math.abs(player.getPosition()[1]) < 1750)) {
            System.out.print("Out Bound of River \n");
        }
        assertEquals( -99, player.getPosition()[0], 0.0);
        assertEquals(-1500, player.getPosition()[1], 0.0);

    }



    @Test
    public void player_setPosition_moveToSafeTile() {
        ImageView sprite = mock();
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        //A move to -1108 will result in the player's position at the middle of the frame.
        //Which will be the safeTile position.
        player.setPosition(-99,-1108);
        player.setPosition(0,-1108);


        if(Math.abs(player.getPosition()[1]) != -1108) {
            System.out.println("not in the safe tile area.");
        }
        assertEquals( -99, player.getPosition()[0], 0.0);
        assertEquals(-1108, player.getPosition()[1], 0.0);
        assertEquals( 0, player.getPosition()[0], 0.0);
        assertEquals(-1108, player.getPosition()[1], 0.0);

    }

    @Test
    public void player_getTileType_start() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);

        Player player = new Player(sprite);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);
        assertEquals("start", player.getTileType());
    }

    @Test
    public void player_getTileType_road() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);

        Player player = new Player(sprite);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        player.setPosition(0, -50);
        assertEquals("road", player.getTileType());

        player.setPosition(0, -299);
        assertEquals("road", player.getTileType());
    }

    @Test
    public void player_getTileType_safe() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);

        Player player = new Player(sprite);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        player.setPosition(0, -300);
        assertEquals("safe", player.getTileType());

        player.setPosition(0, -349);
        assertEquals("safe", player.getTileType());
    }

    @Test
    public void player_getTileType_river() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);

        Player player = new Player(sprite);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        player.setPosition(0, -350);
        assertEquals("river", player.getTileType());

        player.setPosition(0, -549);
        assertEquals("river", player.getTileType());
    }

    @Test
    public void player_getTileType_goal() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);

        Player player = new Player(sprite);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        player.setPosition(0, -550);
        assertEquals("goal", player.getTileType());

        player.setPosition(0, -649);
        assertEquals("goal", player.getTileType());
    }
    @Test
    public void player_setPosition_startTile() {
        ImageView sprite = mock();
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getPosition()[0], 0, 0.0);
        assertEquals(player.getPosition()[1], 0, 0.0);

        //A move to 0 will result in the player's position at the middle of the frame.
        //Which will be the start tile position.
        player.setPosition(-99,0);


        if(Math.abs(player.getPosition()[1]) != 0) {
            System.out.println("not in the start tile area. ");
        }
        assertEquals( -99, player.getPosition()[0], 0.0);
        assertEquals(0, player.getPosition()[1], 0.0);

    }

    @Test
    public void player_setPosition_startTile_awardsNoPoints() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getScore(), 0);
        player.setPosition(0, -10);  // Up into the start tile.
        assertEquals(player.getScore(), 0);
    }

    @Test
    public void player_setPosition_roadTile_awards10Points() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getScore(), 0);
        player.setPosition(0, -100);  // Up into the road tiles.
        // A single move should award 10 points
        assertEquals(player.getScore(), 10);
    }

    @Test
    public void player_setPosition_safeTile_awards5Points() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getScore(), 0);
        player.setPosition(0, -300);  // Up into the safe tile.
        assertEquals(player.getScore(), 5);
    }

    @Test
    public void player_setPosition_riverTile_awards20Points() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getScore(), 0);
        player.setPosition(0, -400);  // Up into the river tile.
        assertEquals(player.getScore(), 20);
    }

    @Test
    public void player_setPosition_goalTile_awards50Points() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getScore(), 0);
        player.setPosition(0, -600);  // Up into the goal tile.
        assertEquals(player.getScore(), 50);
    }

    @Test
    public void player_setPosition_repeatedMove_awardsNoPoints() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214);

        assertEquals(player.getScore(), 0);
        player.setPosition(0, -100);  // Up into the road tile.
        assertEquals(player.getScore(), 10);

        player.setPosition(-50, -100);  // Sideways
        assertEquals(player.getScore(), 10);
        player.setPosition(0, -50);  // Down
        assertEquals(player.getScore(), 10);
        player.setPosition(0, -100);  // Back up
        assertEquals(player.getScore(), 10);
    }

    @Test
    public void player_loseLife_reduces_lives() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214, 3);

        player.loseLife();

        assertEquals(2, player.getLives());
    }

    @Test
    public void player_loseLife_resets_position() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214, 3);

        player.loseLife();

        assertEquals(0, player.getPosition()[0], 0.1);
        assertEquals(0, player.getPosition()[1], 0.1);

    }
    @Test
    public void player_loseLife_resets_score() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214, 3);

        player.loseLife();

        assertEquals(0, player.getScore());
    }

    @Test
    public void player_loseLife_resets_mostProgress() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214, 3);
        player.setPosition(0, -100);

        int beforeScore = player.getScore();
        assertNotEquals(0, beforeScore);

        player.loseLife();
        assertEquals(0, player.getScore());

        player.setPosition(0, -100);
        assertEquals(beforeScore, player.getScore());
    }

    @Test
    public void player_setPosition_ontoRiver_causesLoseLife() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214, 3);

        player.setPosition(0, -400);

        assertEquals(2, player.getLives());
    }

    @Test
    public void player_setPosition_ontoRiver_causesResetPosition() {
        ImageView sprite = mock();
        when(sprite.getHeight()).thenReturn(50);
        Player player = new Player (sprite, 100, 2214, 3);

        player.setPosition(0, -400);

        assertEquals(0, player.getPosition()[0], 0.1);
        assertEquals(0, player.getPosition()[1], 0.1);
    }
}
