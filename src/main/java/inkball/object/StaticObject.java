package inkball.object;

import inkball.App;
import inkball.Ball;
import processing.core.PImage;
import java.util.HashMap;

public abstract class StaticObject {
        public PImage objImg;
        public String objName = "obj";
        public float[] position = new float[2];
        public float width, height;
        public boolean interactable;
        public float x1, x2, y1, y2;
        public HashMap<String, PImage> localSprites = new HashMap<>();
        private char state;

        public abstract void interactWithBall(App app, Ball ball);

        public abstract boolean intersect(Ball ball);

        public void setupLocalSprites(HashMap<String, PImage> localSprites) {
                this.localSprites = localSprites;
        }

        public StaticObject(PImage objImage, float x, float y) {
                this.objImg = objImage;
                position[0] = x;
                position[1] = y;
                this.width = objImage.width;
                this.height = objImage.height;
                x1 = x;
                x2 = x + width;
                y1 = y;
                y2 = y + height;
        }

        public StaticObject(float x1, float y1, float x2, float y2) {
                this.x1 = x1;
                this.x2 = x2;
                this.y1 = y2;
                this.y2 = y1;
        }

        public StaticObject(HashMap<String, PImage> localSprites, char state, float x, float y) {
                setupLocalSprites(localSprites);
                this.state = state;
        }

        public void draw(App app) {
                app.image(this.objImg, position[0], position[1]);
        }

        public void updateState(char state) {
                this.state = state;
        }

        public char getState() {
                return state;
        }

}
