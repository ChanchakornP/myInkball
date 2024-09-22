package inkball;

import inkball.object.DynamicObject;
import processing.core.PImage;
import processing.core.PVector;
import java.util.HashMap;

public class Ball extends DynamicObject {
    private boolean captured = false;

    public void setCaptured(boolean x) {
        captured = x;
    }

    public boolean getCaptured() {
        return captured;
    }

    public void move() {
        float[] velArray = vel.array();
        float newX = this.position[0] + velArray[0];
        float newY = this.position[1] + velArray[1];
        this.position[0] = newX;
        this.position[1] = newY;
    }

    public float[] getVelocity() {
        return vel.array();
    }

    public PVector getVelocityVec() {
        return vel;
    }

    public Ball(PImage objImg, float x, float y) {
        super(objImg, x, y);
    }

    public Ball(HashMap<String, PImage> localSprites, char state, float x, float y) {
        super(localSprites, state, x, y);
        PImage objImage = localSprites.get("ball" + state);
        updateState(state);
        position[0] = x;
        position[1] = y;
        this.width = objImage.width;
        this.height = objImage.height;
    }

    public float[] getCenter() {
        float[] center = new float[2];
        center[0] = position[0] + width / 2;
        center[1] = position[1] + height / 2;
        return center;
    }

    public float getRadius() {
        return height / 2; // assume width == height
    }

    // nextFrame base on given velocity
    public float[] nextFramePosition() {
        float[] nextPos = new float[2];
        float[] vel = getVelocity();
        float[] center = getCenter();
        nextPos[0] = center[0] + vel[0];
        nextPos[1] = center[1] + vel[1];
        return nextPos;
    }

    public PVector nextFramePositionVec() {
        float[] nextPos = new float[2];
        float[] vel = getVelocity();
        float[] center = getCenter();
        nextPos[0] = center[0] + vel[0];
        nextPos[1] = center[1] + vel[1];
        PVector nextPosVec = new PVector(nextPos[0], nextPos[1]);
        return nextPosVec;
    }

    public void inverseVel() {
        this.vel = PVector.mult(vel, -1);
    }

    public void rotateVel(int theta) {
        vel = this.vel.rotate(theta);
    }

    public void reflect(PVector normVec) {
        // u = v - 2 * (vel . normVec) * normVec
        float dotProduct = PVector.dot(vel, normVec);
        PVector reflection = PVector.mult(PVector.mult(normVec, dotProduct), 2);
        PVector newVelocity = PVector.sub(vel, reflection);
        vel = newVelocity;
    }

    public PVector newVelocity(PVector normVec) {
        float dotProduct = PVector.dot(vel, normVec);
        PVector reflection = PVector.mult(PVector.mult(normVec, dotProduct), 2);
        PVector newVelocity = PVector.sub(vel, reflection);
        return newVelocity;
    }

    public void draw(App app) {
        PImage objImgTmp = getLocalSprites().get("ball" + getState()).copy();
        if (this.width < 28) {
            objImgTmp.resize((int) this.width, (int) this.height);
        }
        app.image(objImgTmp, position[0], position[1]);
    }

    public void setVel(PVector vel) {
        this.vel = vel;
    }

    public void setVel(float[] vel) {
        this.vel = new PVector(vel[0], vel[1]);
    }

    public void resize(float x, float y) {
        this.width = x;
        this.height = y;
    }
}