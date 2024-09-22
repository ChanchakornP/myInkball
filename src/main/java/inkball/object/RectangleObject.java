package inkball.object;

import inkball.App;
import inkball.Ball;
import processing.core.PImage;
import java.util.HashMap;

public abstract class RectangleObject extends StaticObject {
    public String objName = "obj";
    public float width, height;
    public float x1, x2, y1, y2;
    public HashMap<String, PImage> localSprites = new HashMap<>();

    public abstract void interactWithBall(App app, Ball ball);

    public abstract boolean intersect(Ball ball);

    public void setupLocalSprites(HashMap<String, PImage> localSprites) {
        this.localSprites = localSprites;
    }

    public RectangleObject(PImage objImage, float x, float y) {
        this.objImg = objImage;
        position[0] = x;
        position[1] = y;
        this.width = objImage.width;
        this.height = objImage.height;
        x1 = x;
        x2 = x + width;
        y1 = y;
        y2 = y + height;
    }

    // for Line
    public RectangleObject(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y2;
        this.y2 = y1;
    }

    public RectangleObject() {
    }

    // for ball, it needs to store images
    public RectangleObject(HashMap<String, PImage> localSprites, int state, float x, float y) {
        setupLocalSprites(localSprites);
        this.state = state;
    }
}
