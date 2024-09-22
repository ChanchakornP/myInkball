package inkball;

import inkball.object.RectangleObject;
import processing.core.PImage;

public class EntryPoint extends RectangleObject {
    public EntryPoint(PImage objImg, float x, float y) {
        super(objImg, x, y);
    }

    public void interactWithBall(App app, Ball ball) {
    }

    public void updateState() {
    }

    public boolean intersect(Ball ball) {
        return false;
    }

    public boolean intersectEdge(Ball ball) {
        return false;
    }
}
