package inkball;

import inkball.object.RectangleObject;
import processing.core.PImage;
import java.util.*;
import inkball.state.Color;

public class Wall extends RectangleObject {
        public int wallNumber = 0;

        public Wall(HashMap<String, PImage> localSprites, int state, float x, float y) {
                super(localSprites, state, x, y);
                PImage objImage = localSprites.get("wall" + String.valueOf(state));
                this.objImg = objImage;
                position[0] = x;
                position[1] = y;
                this.width = objImage.width;
                this.height = objImage.height;
                x1 = x;
                x2 = x + width;
                y1 = y;
                y2 = y + height;
                this.objName = "wall";
        }

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
                if (!iscollidePendicular(ball) && !iscollideDiagonal(ball)) {
                        return;
                }

                collideHandling(app, ball);

                if (getState() != Color.GREY.ordinal()) {
                        ball.updateState(getState());
                }
        }
}
