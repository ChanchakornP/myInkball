package inkball;

import inkball.object.StaticObject;
import processing.core.PImage;
import processing.core.PVector;

public class Hole extends StaticObject {
    float[] center = new float[2];

    public Hole(PImage objImg, float x, float y) {
        super(objImg, x, y);
        center[0] = x + objImg.width / 2;
        center[1] = y + objImg.height / 2;
        this.interactable = true;
        this.objName = "hole";
    }

    @Override
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

    @Override
    public void interactWithBall(App app, Ball ball) {
        if (!intersect(ball)) {
            return;
        }
        float[] ballCenter = ball.getCenter();
        PVector gravityVector = new PVector(center[0] - ballCenter[0], center[1] - ballCenter[1]);
        int ballSize = (int) gravityVector.mag() + 1;
        gravityVector = PVector.mult(gravityVector, (float) 0.005);
        PVector _newballVal = PVector.mult(PVector.add(ball.getVelocityVec(),
                gravityVector).normalize(),
                (float) Math.sqrt(8));
        // PVector _newballVal = PVector.add(ball.getVelocityVec(), gravityVector);
        ball.setVel(_newballVal);
        if (ballSize < 10) {
            ball.setCaptured(true);
        }
        if (ballSize > 32)
            ballSize = 32;
        ball.resize(ballSize, ballSize);
        // ball.setsize();
        // TODO //
        // If in the middle Capture it //
    }

    public void updateState() {
    }

}
