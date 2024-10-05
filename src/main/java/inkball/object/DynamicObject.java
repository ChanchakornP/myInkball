package inkball.object;

import inkball.App;
import processing.core.PImage;
import processing.core.PVector;
import java.util.HashMap;

public abstract class DynamicObject {
    public PImage objImg;
    public float[] position = new float[2];
    public float width, height;
    protected PVector vel = new PVector(2, 2);
    private HashMap<String, PImage> localSprites = new HashMap<>();
    protected int state;

    public abstract void move();

    public DynamicObject(PImage objImage, float x, float y) {
        this.objImg = objImage;
        position[0] = x;
        position[1] = y;
        this.width = objImage.width;
        this.height = objImage.height;
    }

    public DynamicObject(HashMap<String, PImage> localSprites, int state, float x, float y) {
        this.localSprites = localSprites;
        this.state = state;
    }

    public HashMap<String, PImage> getLocalSprites() {
        return this.localSprites;
    }

    public void draw(App app) {
        app.image(objImg, position[0], position[1]);
    }

    public PVector getVel() {
        return this.vel;
    }

    public void updateState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}
