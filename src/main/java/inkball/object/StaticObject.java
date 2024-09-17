package inkball.object;

import inkball.App;
import inkball.Ball;
import processing.core.PImage;
import java.util.HashMap;

public abstract class StaticObject {
    public PImage objImg;
    public String objName = "obj";
    public float[] position = new float[2];
    public float width, height;
    public boolean interactable;
    public float x1, x2, y1, y2;
    public HashMap<String, PImage> localSprites = new HashMap<>();
    private char state;

    public void setupLocalSprites(HashMap<String, PImage> localSprites) {
        this.localSprites = localSprites;
    }

    public boolean intersect(Ball ball) {
        float[] ballCenter = ball.getCenter();
        float radius = ball.getRadius();
        float[] vel = ball.getVelocity();
        float[] leftNextLocation = new float[] { ballCenter[0] - radius + vel[0],
                ballCenter[1] + vel[1] };
        float[] rightNextLocation = new float[] { ballCenter[0] + radius +
                vel[0],
                ballCenter[1] };
        float[] topNextLocation = new float[] { ballCenter[0] +
                vel[0],
                ballCenter[1] - radius + vel[1] };
        float[] bottomNextLocation = new float[] { ballCenter[0] +
                vel[0],
                ballCenter[1] + radius + vel[1] };
        boolean collideLeft = leftNextLocation[0] >= x1 && leftNextLocation[0] <= x2
                &&
                leftNextLocation[1] >= y1
                && leftNextLocation[1] <= y2;
        boolean collideTop = rightNextLocation[0] >= x1 && rightNextLocation[0] <= x2
                && rightNextLocation[1] >= y1
                && rightNextLocation[1] <= y2;
        boolean collideRight = topNextLocation[0] >= x1 && topNextLocation[0] <= x2
                && topNextLocation[1] >= y1
                && topNextLocation[1] <= y2;
        boolean collideBottom = bottomNextLocation[0] >= x1 && bottomNextLocation[0] <= x2
                && bottomNextLocation[1] >= y1
                && bottomNextLocation[1] <= y2;

        return collideLeft || collideRight || collideBottom || collideTop;
    }

    public boolean intersectEdge(Ball ball) {
        float[] ballCenter = ball.getCenter();
        float radius = ball.getRadius();
        float[] vel = ball.getVelocity();
        float[] topLeftNextLocation = new float[] { ballCenter[0] - (float) (radius /
                Math.sqrt(2)) + vel[0],
                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
        float[] topRightNextLocation = new float[] { ballCenter[0] + (float) (radius
                / Math.sqrt(2)) + vel[0],
                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
        float[] bottomLeftNextLocation = new float[] { ballCenter[0] - (float) (radius / Math.sqrt(2)) + vel[0],
                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };
        float[] bottomRightNextLocation = new float[] { ballCenter[0] + (float) (radius / Math.sqrt(2)) + vel[0],
                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };

        boolean collideTopLeft = topLeftNextLocation[0] >= x1 &&
                topLeftNextLocation[0] <= x2 &&
                topLeftNextLocation[1] >= y1 && topLeftNextLocation[1] <= y2;
        boolean collideTopRight = topRightNextLocation[0] >= x1 &&
                topRightNextLocation[0] <= x2 &&
                topRightNextLocation[1] >= y1 && topRightNextLocation[1] <= y2;
        boolean collideBottomLeft = bottomLeftNextLocation[0] >= x1 &&
                bottomLeftNextLocation[0] <= x2 &&
                bottomLeftNextLocation[1] >= y1 && bottomLeftNextLocation[1] <= y2;
        boolean collideBottomRight = bottomRightNextLocation[0] >= x1 &&
                bottomRightNextLocation[0] <= x2 &&
                bottomRightNextLocation[1] >= y1 && bottomRightNextLocation[1] <= y2;
        return collideTopLeft || collideTopRight || collideBottomLeft ||
                collideBottomRight;
    }

    public StaticObject(PImage objImage, float x, float y) {
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

    public StaticObject(HashMap<String, PImage> localSprites, char state, float x, float y) {
        setupLocalSprites(localSprites);
        this.state = state;
    }

    public void draw(App app) {
        app.image(this.objImg, position[0], position[1]);
    }

    public abstract void interactWithBall(App app, Ball ball);

    public void updateState(char state) {
        this.state = state;
    }

    public char getState() {
        return state;
    }

}
