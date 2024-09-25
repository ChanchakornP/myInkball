package inkball.object;

import inkball.App;
import inkball.Ball;
import processing.core.PImage;
import java.util.*;

public abstract class StaticObject {
        protected PImage objImg;
        protected String objName = "obj";
        protected float[] position = new float[2];
        private int state;
        private int objCounter;

        public HashMap<String, PImage> localSprites = new HashMap<>();

        public abstract void interactWithBall(App app, Ball ball);

        public abstract void interactWithBall(App app, List<Ball> ball, String BallColor);

        public abstract boolean intersect(Ball ball);

        public void updateInfo() {
        }

        public void draw(App app) {
                app.image(this.objImg, position[0], position[1]);
        }

        public void updateState(int state) {
                this.state = state;
        }

        public int getState() {
                return state;
        }

        public String getObjName() {
                return this.objName;
        }

        public float[] getPosition() {
                return position;
        }

        public int getCounter() {
                return objCounter;
        }

        public void incCounter() {
                objCounter++;
        }

        public void resetCounter() {
                objCounter = 0;
        }
}
