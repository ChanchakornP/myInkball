package inkball;

import java.util.HashMap;

import inkball.object.RectangleObject;
import processing.core.PImage;

public class Tile extends RectangleObject {
    public Tile(PImage objImg, float x, float y) {
        super(objImg, x, y);
    }

    public Tile(HashMap<String, PImage> localSprites, int state, float x, float y) {
        super(localSprites, state, x, y);
    }

    @Override
    public void interactWithBall(App app, Ball ball) {
    }

    @Override
    public boolean intersect(Ball ball) {
        return false;
    }
}
