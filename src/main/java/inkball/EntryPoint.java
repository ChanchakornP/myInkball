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

    @Override
    public boolean intersect(Ball ball) {
        return false;
    }

    @Override
    public void interactWithBall(App app, Ball ball) {
    }
}
