package inkball;

import processing.core.PImage;

public class AcceleratedTile extends Tile {
    public AcceleratedTile(PImage objImg, float x, float y) {
        super(objImg, x, y);
    }

    public boolean intersect(Ball ball) {
        return false;
    }

    public void interactWithBall(Ball ball) {
    }
}
