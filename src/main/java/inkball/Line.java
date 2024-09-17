package inkball;

import inkball.object.StaticObject;
import processing.core.PVector;

public class Line extends StaticObject {
    public Line(float x1, float y1, float x2, float y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void interactWithBall(App app, Ball ball) {
        float distance = Float.MAX_VALUE;
        PVector normVec = new PVector(0, 0);
        float[] center = new float[] { (x2 + x1) / 2, (y2 + y1) / 2 };
        float dy = y2 - y1;
        float dx = x2 - x1;

        PVector midPointToBall = new PVector(ball.getCenter()[0] - center[0],
                +ball.getCenter()[1] - center[1]);
        PVector tempNormVec = new PVector(-dy, dx).normalize();
        if (distance > midPointToBall.mag()) {
            normVec = tempNormVec;
            distance = midPointToBall.mag();
        }

        // find the closest line
        if (distance <= ball.width) {
            ball.reflect(normVec);
        }
    }

    @Override
    public boolean intersect(Ball ball) {
        return false;
    }

}
