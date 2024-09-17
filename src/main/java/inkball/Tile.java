package inkball;

import inkball.object.StaticObject;
import processing.core.PImage;

public class Tile extends StaticObject {
    public Tile(PImage objImg, float x, float y) {
        super(objImg, x, y);
    }

    public void interactWithBall(App app, Ball ball) {
    }

    public boolean intersect(Ball ball) {
        return false;
    }

    public boolean intersectEdge(Ball ball) {
        return false;
    }

    public void updateState() {
    }

}
