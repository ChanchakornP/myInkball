package inkball;

import inkball.object.RectangleObject;
import processing.core.PImage;
import java.util.*;

/**
 * The spawner of the balls.
 */
public class EntryPoint extends RectangleObject {
    public EntryPoint(PImage objImg, float x, float y) {
        super(objImg, x, y);
        objName = "EntryPoint";
    }

    public void interactWithBall(App app, Ball ball) {
    }

    public void interactWithBall(App app, List<Ball> ball, String BallColor) {
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
