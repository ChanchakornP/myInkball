package inkball.object;

import inkball.App;
import inkball.Ball;
import processing.core.PImage;
import java.util.*;

/**
 * The static object in the game. For example, walls, tiles, holes and spawner.
 */
public abstract class StaticObject {
        protected PImage objImg;
        protected String objName = "obj";
        protected float[] position = new float[2];
        private int state;
        private int objCounter;

        public HashMap<String, PImage> localSprites = new HashMap<>();

        /**
         * The method determines the behaviours if both the ball and object are
         * intersected
         * 
         * @param ball the ball object
         * @param app  the game application
         */
        public abstract void interactWithBall(App app, Ball ball);

        /**
         * The method determines the behaviours if both the ball and object are
         * intersected
         * 
         * @param ball the ball object
         */

        /**
         * The method computes whether the object and the ball are intersected
         * 
         * @param ball the ball object
         * @return the condition determines whether this object have to interact with
         *         the ball or not
         */
        public abstract boolean intersect(Ball ball);

        /**
         * Draw the object in the app.
         * 
         * @param app game application
         */
        public void draw(App app) {
                app.image(this.objImg, position[0], position[1]);
        }

        /**
         * Update the state of the object (if any).
         * 
         * @param state state of the object.
         */
        public void updateState(int state) {
                this.state = state;
        }

        /**
         * get the state of the object.
         * 
         * @return return the state of this object
         */
        public int getState() {
                return state;
        }

        /**
         * get the object name of the app.
         * 
         * @return return this object name
         */
        public String getObjName() {
                return this.objName;
        }

        /**
         * get the position of the object in the app.
         * 
         * @return return the top-left position of this object
         */
        public float[] getPosition() {
                return position;
        }

        /**
         * get the counter of the object.
         * 
         * @return return the counter.
         */
        public int getCounter() {
                return objCounter;
        }

        /**
         * increase the counter of the object.
         * 
         */
        public void incCounter() {
                objCounter++;
        }

        /**
         * reset the counter of the object.
         * 
         */
        public void resetCounter() {
                objCounter = 0;
        }
}
