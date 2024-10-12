package inkball;

import java.util.*;
import inkball.object.StaticObject;
import processing.core.PVector;

/**
 * The hidden object, used as hidden border when there is no walls. The balls
 * cannot move outside the board.
 * 
 */
public class Straightline extends StaticObject {
    // work around for now to handle the stage without the walls.
    // create hidden line around the stage.
    PVector normVec = new PVector();
    int x1, y1, x2, y2;

    public Straightline(int x1, int y1, int x2, int y2) {
        float dy = y2 - y1;
        float dx = x2 - x1;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.normVec = new PVector(-dy, dx).normalize();
    }

    @Override
    public void interactWithBall(App app, Ball ball) {
        ball.reflect(normVec);
    }

    @Override
    public boolean intersect(Ball ball) {
        float[] ballPos = ball.nextFramePosition();
        float radius = ball.getRadius();
        if (y1 == y2) {
            if (Math.abs(ballPos[1] - y1) < radius) {
                return true;
            }
        } else {
            if (Math.abs(ballPos[0] - x1) < radius) {
                return true;
            }
        }
        return false;
    }

}
