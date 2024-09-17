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

    public void interactWithBall(App app, Ball ball) {
        // assume ball already intersects with the hole
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
            // capture here
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
