package inkball.object;

import inkball.App;
import processing.core.PImage;
import processing.core.PVector;
import java.util.HashMap;

enum stateColor {
    White,
    Orange,
    Blue,
    Green,
    Yellow
}

public abstract class DynamicObject {
    public PImage objImg;
    public float[] position = new float[2];
    public float width, height;
    protected PVector vel = new PVector(2, 2);
    private HashMap<String, PImage> localSprites = new HashMap<>();
    private char state;

    public abstract void move();

    public DynamicObject(PImage objImage, float x, float y) {
        this.objImg = objImage;
        position[0] = x;
        position[1] = y;
        this.width = objImage.width;
        this.height = objImage.height;
    }

    public DynamicObject(HashMap<String, PImage> localSprites, char state, float x, float y) {
        this.localSprites = localSprites;
        this.state = state;
    }

    public HashMap<String, PImage> getLocalSprites() {
        return this.localSprites;
    }

    public void updateLocalSpritesSize(int x, int y) {
        for (String str : localSprites.keySet()) {
            PImage tmpImg = localSprites.get(str);
            tmpImg.resize(x, y);
            localSprites.put(str, tmpImg);
        }
    }

    public void draw(App app) {
        app.image(objImg, position[0], position[1]);
    }

    public PVector getVel() {
        return this.vel;
    }

    public void updateState(char state) {
        this.state = state;
    }

    public char getState() {
        return state;
    }

}
