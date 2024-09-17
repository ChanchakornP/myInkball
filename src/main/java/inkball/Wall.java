package inkball;

import inkball.interfaces.Collidable;
import inkball.object.StaticObject;
import processing.core.PImage;
import processing.core.PVector;
import java.util.*;

public class Wall extends StaticObject implements Collidable {
    public int wallNumber = 0;

    public Wall(PImage objImg, float x, float y) {
        super(objImg, x, y);
        this.interactable = true;
        this.objName = "wall";
    }

    public Wall(HashMap<String, PImage> localSprites, char state, float x, float y) {
        super(localSprites, state, x, y);
        PImage objImage = localSprites.get("wall" + state);
        this.objImg = objImage;
        position[0] = x;
        position[1] = y;
        this.width = objImage.width;
        this.height = objImage.height;
        x1 = x;
        x2 = x + width;
        y1 = y;
        y2 = y + height;
        this.interactable = true;
        this.objName = "wall";
    }

    // public void interactWithBall(App app, Ball ball) {
    // float[][] lines = getWallLines();
    // float distance = Float.MAX_VALUE;
    // PVector normVec = new PVector(0, 0);
    // for (int i = 0; i < lines.length; i++) {
    // float x1 = lines[i][0];
    // float y1 = lines[i][1];
    // float x2 = lines[i][2];
    // float y2 = lines[i][3];
    // float[] center = new float[] { (x2 + x1) / 2, (y2 + y1) / 2 };
    // float dy = y2 - y1;
    // float dx = x2 - x1;

    // PVector midPointToBall = new PVector(ball.getCenter()[0] - center[0],
    // +ball.getCenter()[1] - center[1]);
    // PVector tempNormVec = new PVector(-dy, dx).normalize();
    // if (distance > midPointToBall.mag()) {
    // normVec = tempNormVec;
    // distance = midPointToBall.mag();
    // }
    // }

    // // find the closest line
    // if (distance <= ball.width) {
    // ball.reflect(normVec);
    // }

    // // ignore white
    // if (getState() != '0') {
    // ball.updateState(getState());
    // }
    // }
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
        } else if (collideRight) {
            return 3;
        } else {
            // edge
            // System.out.printf("radius : %.4f", radius);
            return 4;
        }
    }

    public void interactWithBall(App app, Ball ball) {
        float[][] lines = getWallLines();
        int lineNum = whichSideCollide(ball);

        if (lineNum == 4) {
            return;
        }

        float x1 = lines[lineNum][0];
        float y1 = lines[lineNum][1];
        float x2 = lines[lineNum][2];
        float y2 = lines[lineNum][3];
        float dy = y2 - y1;
        float dx = x2 - x1;
        PVector normVec = new PVector(-dy, dx).normalize();

        // find the closest line
        ball.reflect(normVec);

        // ignore white
        if (getState() != '0') {
            ball.updateState(getState());
        }
    }

    public float[][] getWallLines() {
        return new float[][] {
                { x1, y1, x2, y1 },
                { x2, y1, x2, y2 },
                { x2, y2, x1, y2 },
                { x1, y2, x1, y1 }
        };
    }
}
