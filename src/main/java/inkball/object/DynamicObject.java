package inkball.object;

import inkball.App;
import processing.core.PImage;
import processing.core.PVector;
import java.util.HashMap;

/**
 * The dynamic object of the game.
 */

public abstract class DynamicObject {
    protected PImage objImg;
    protected PVector vel = new PVector(2, 2);
    protected HashMap<String, PImage> localSprites = new HashMap<>();
    private int state;
    public float[] position = new float[2];
    public float width, height;

    public abstract void move();

    public void draw(App app) {
        app.image(objImg, position[0], position[1]);
    }

    public PVector getVel() {
        return this.vel;
    }

    public float[] getVelocity() {
        return this.vel.array();
    }

    public void updateState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}
