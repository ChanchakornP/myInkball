package inkball;

import inkball.object.RectangleObject;
import processing.core.PImage;
import processing.core.PVector;

public class Hole extends RectangleObject {
        float[] center = new float[2];

        public Hole(PImage objImg, int state, float x, float y) {
                super(objImg, x, y);
                center[0] = x + objImg.width / 2;
                center[1] = y + objImg.height / 2;
                this.objName = "hole";
                updateState(state);
        }

        @Override
        public boolean intersect(Ball ball) {
                return iscollideDiagonal(ball) || iscollidePendicular(ball);
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
                // PVector _newballVal = PVector.mult(PVector.add(ball.getVelocityVec(),
                // gravityVector).normalize(),
                // (float) Math.sqrt(8));
                PVector _newballVal = PVector.add(ball.getVelocityVec(), gravityVector);
                ball.setVel(_newballVal);
                if (ballSize < 12) {
                        ball.setCaptured(true);
                }
                if (ballSize > 24)
                        ballSize = 24;
                ball.resize(ballSize, ballSize);
        }
}
