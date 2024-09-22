package inkball.object;

import inkball.App;
import inkball.Ball;
import processing.core.PImage;
import java.util.HashMap;

public abstract class StaticObject {
        public PImage objImg;
        public String objName = "obj";
        public float[] position = new float[2];
        protected int state;

        public HashMap<String, PImage> localSprites = new HashMap<>();

        public abstract void interactWithBall(App app, Ball ball);

        public abstract boolean intersect(Ball ball);

        public void draw(App app) {
                app.image(this.objImg, position[0], position[1]);
        }

        public void updateState(int state) {
                this.state = state;
        }

        public int getState() {
                return state;
        }
}
