package com.example.froggerclone;

public interface Collidable {
    public boolean isCollidingWithPlayer(Player player);

    public void handleCollisionWithPlayer(Player player);
}
