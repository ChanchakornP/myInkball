package inkball;

import java.util.HashMap;
import processing.core.PImage;

public class TimerTile extends Tile {

    public TimerTile(HashMap<String, PImage> localSprites, int state, float x, float y) {
        super(localSprites, state, x, y);
        PImage objImage = localSprites.get("timer_tile" + String.valueOf(state));
        this.objImg = objImage;
        position[0] = x;
        position[1] = y;
        this.width = objImage.width;
        this.height = objImage.height;
        x1 = x;
        x2 = x + width;
        y1 = y;
        y2 = y + height;
        this.objName = "timerTile";
    }

    @Override
    public boolean intersect(Ball ball) {
        if (getState() > 0) {
            return iscollideDiagonal(ball) || iscollidePendicular(ball);
        }
        return false;
    }

    @Override
    public void interactWithBall(App app, Ball ball) {
        collideHandling(app, ball);
    }

    @Override
    public void draw(App app) {
        PImage objImage = localSprites.get("timer_tile" + String.valueOf(getState()));
        app.image(objImage, position[0], position[1]);
    }
}
