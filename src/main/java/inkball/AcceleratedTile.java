package inkball;

import processing.core.PImage;
import inkball.state.AccTile;

/**
 * An accelerated tile, speed up the ball in a specific direction.
 */
public class AcceleratedTile extends Tile {
    public AcceleratedTile(PImage objImg, float x, float y) {
        super(objImg, x, y);
    }

    public AcceleratedTile(PImage objImg, int state, float x, float y) {
        super(objImg, x, y);
        updateState(state);
        objName = "acctile";
    }

    /**
     * If the center of the ball is inside the accelerated tile, considered it is
     * intersected.
     */
    @Override
    public boolean intersect(Ball ball) {
        float[] centerBall = ball.getCenter();
        if (centerBall[0] >= x1 && centerBall[0] <= x2 && centerBall[1] >= y1 && centerBall[1] <= y2) {
            return true;
        }
        return false;
    }

    /**
     * The tile speed up the ball by 0.5 per frame.
     */
    @Override
    public void interactWithBall(App app, Ball ball) {
        if (AccTile.UP.ordinal() == getState()) {
            float[] currentVelocity = ball.getVelocity();
            currentVelocity[1] -= 0.5;
            ball.setVel(currentVelocity);
        } else if (AccTile.DOWN.ordinal() == getState()) {
            float[] currentVelocity = ball.getVelocity();
            currentVelocity[1] += 0.5;
            ball.setVel(currentVelocity);
        } else if (AccTile.LEFT.ordinal() == getState()) {
            float[] currentVelocity = ball.getVelocity();
            currentVelocity[0] -= 0.5;
            ball.setVel(currentVelocity);
        } else if (AccTile.RIGHT.ordinal() == getState()) {
            float[] currentVelocity = ball.getVelocity();
            currentVelocity[0] += 0.5;
            ball.setVel(currentVelocity);
        }
    }
}
