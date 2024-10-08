package inkball;

import inkball.object.RectangleObject;
import processing.core.PImage;
import inkball.state.Color;

/**
 * The wall object, collidable with the ball.
 */
public class Wall extends RectangleObject {
        public int wallNumber = 0;

        public Wall(PImage objImg, int state, float x, float y) {
                super(objImg, x, y);
                updateState(state);
                position[0] = x;
                position[1] = y;
                this.width = objImg.width;
                this.height = objImg.height;
                x1 = x;
                x2 = x + width;
                y1 = y;
                y2 = y + height;
                this.objName = "wall";
        }

        @Override
        public boolean intersect(Ball ball) {
                return iscollideDiagonal(ball) || iscollidePendicular(ball);
        }

        @Override
        public void interactWithBall(App app, Ball ball) {
                collideHandling(app, ball);

                if (getState() != Color.GREY.ordinal()) {
                        ball.updateState(getState());
                }
        }
}
