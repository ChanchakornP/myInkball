package inkball.object;

import inkball.App;
import inkball.Ball;
import processing.core.PImage;
import processing.core.PVector;

import java.util.*;

public abstract class RectangleObject extends StaticObject {
    public float width, height;
    public float x1, x2, y1, y2;

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

    // for ball, it needs to store images
    public RectangleObject(HashMap<String, PImage> localSprites, int state, float x, float y) {
        this.localSprites = localSprites;
        updateState(state);
    }

    public int whichSideCollide(Ball ball) {
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
        boolean collideLeft = leftNextLocation[0] >= x1 && leftNextLocation[0] <= x2 &&
                leftNextLocation[1] >= y1
                && leftNextLocation[1] <= y2;
        boolean collideRight = rightNextLocation[0] >= x1 && rightNextLocation[0] <= x2
                && rightNextLocation[1] >= y1
                && rightNextLocation[1] <= y2;
        boolean collideTop = topNextLocation[0] >= x1 && topNextLocation[0] <= x2
                && topNextLocation[1] >= y1
                && topNextLocation[1] <= y2;
        boolean collideBottom = bottomNextLocation[0] >= x1 && bottomNextLocation[0] <= x2
                && bottomNextLocation[1] >= y1
                && bottomNextLocation[1] <= y2;
        if (collideBottom) {
            return 0;
        } else if (collideLeft) {
            return 1;
        } else if (collideTop) {
            return 2;
        } else { // collideRight
            return 3;
        }
    }

    public boolean iscollideDiagonal(Ball ball) {
        return intersectTopLeft(ball) || intersectTopRight(ball) || intersectBottomLeft(ball)
                || intersectBottomRight(ball);
    }

    public boolean intersectTopLeft(Ball ball) {
        float[] ballCenter = ball.getCenter();
        float radius = ball.getRadius();
        float[] vel = ball.getVelocity();
        float[] topLeftNextLocation = new float[] { ballCenter[0] - (float) (radius /
                Math.sqrt(2)) + vel[0],
                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
        boolean collideTopLeft = topLeftNextLocation[0] >= x1 &&
                topLeftNextLocation[0] <= x2 &&
                topLeftNextLocation[1] >= y1 && topLeftNextLocation[1] <= y2;
        return collideTopLeft;
    }

    public boolean intersectTopRight(Ball ball) {
        float[] ballCenter = ball.getCenter();
        float radius = ball.getRadius();
        float[] vel = ball.getVelocity();
        float[] topRightNextLocation = new float[] { ballCenter[0] + (float) (radius
                / Math.sqrt(2)) + vel[0],
                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
        boolean collideTopRight = topRightNextLocation[0] >= x1 &&
                topRightNextLocation[0] <= x2 &&
                topRightNextLocation[1] >= y1 && topRightNextLocation[1] <= y2;
        return collideTopRight;
    }

    public boolean intersectBottomLeft(Ball ball) {
        float[] ballCenter = ball.getCenter();
        float radius = ball.getRadius();
        float[] vel = ball.getVelocity();
        float[] bottomLeftNextLocation = new float[] { ballCenter[0] - (float) (radius / Math.sqrt(2)) + vel[0],
                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };
        boolean collideBottomLeft = bottomLeftNextLocation[0] >= x1 &&
                bottomLeftNextLocation[0] <= x2 &&
                bottomLeftNextLocation[1] >= y1 && bottomLeftNextLocation[1] <= y2;
        return collideBottomLeft;
    }

    public boolean intersectBottomRight(Ball ball) {
        float[] ballCenter = ball.getCenter();
        float radius = ball.getRadius();
        float[] vel = ball.getVelocity();
        float[] bottomRightNextLocation = new float[] {
                ballCenter[0] + (float) (radius / Math.sqrt(2)) + vel[0],
                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };

        boolean collideBottomRight = bottomRightNextLocation[0] >= x1 &&
                bottomRightNextLocation[0] <= x2 &&
                bottomRightNextLocation[1] >= y1 && bottomRightNextLocation[1] <= y2;
        return collideBottomRight;

    }

    public boolean iscollidePendicular(Ball ball) {
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

    public void collideHandling(App app, Ball ball) {
        if (iscollidePendicular(ball)) {
            float[][] lines = getObjectLines();
            int lineNum = whichSideCollide(ball);

            float x1 = lines[lineNum][0];
            float y1 = lines[lineNum][1];
            float x2 = lines[lineNum][2];
            float y2 = lines[lineNum][3];
            float dy = y2 - y1;
            float dx = x2 - x1;
            PVector normVec = new PVector(-dy, dx).normalize();
            ball.reflect(normVec);

        } else if (intersectTopLeft(ball)) {
            float[] ballVel = ball.getVelocity();
            PVector normVec;
            if (ballVel[0] > 0 && ballVel[1] < 0) {
                normVec = new PVector(0, -1).normalize();
            } else if (ballVel[0] < 0 && ballVel[1] > 0) {
                normVec = new PVector(-1, 0).normalize();
            } else {
                normVec = new PVector(1, 1).normalize();
            }
            ball.reflect(normVec);
        } else if (intersectTopRight(ball)) {
            float[] ballVel = ball.getVelocity();
            PVector normVec;
            if (ballVel[0] < 0 && ballVel[1] < 0) {
                normVec = new PVector(0, 1).normalize();
            } else if (ballVel[0] > 0 && ballVel[1] > 0) {
                normVec = new PVector(-1, 0).normalize();
            } else {
                normVec = new PVector(-1, 1).normalize();
            }
            ball.reflect(normVec);
        } else if (intersectBottomLeft(ball)) {
            float[] ballVel = ball.getVelocity();
            PVector normVec;
            if (ballVel[0] > 0 && ballVel[1] > 0) {
                normVec = new PVector(0, 1).normalize();
            } else if (ballVel[0] < 0 && ballVel[1] < 0) {
                normVec = new PVector(1, 0).normalize();
            } else {
                normVec = new PVector(1, -1).normalize();
            }
            ball.reflect(normVec);
        } else if (intersectBottomRight(ball)) {
            float[] ballVel = ball.getVelocity();
            PVector normVec;
            if (ballVel[0] < 0 && ballVel[1] > 0) {
                normVec = new PVector(0, 1).normalize();
            } else if (ballVel[0] > 0 && ballVel[1] < 0) {
                normVec = new PVector(-1, 0).normalize();
            } else {
                normVec = new PVector(-1, -1).normalize();
            }
            ball.reflect(normVec);
        }
    }

    private float[][] getObjectLines() {
        return new float[][] {
                { x1, y1, x2, y1 },
                { x2, y1, x2, y2 },
                { x2, y2, x1, y2 },
                { x1, y2, x1, y1 }
        };
    }
}
