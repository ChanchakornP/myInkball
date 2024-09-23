package inkball;

import inkball.object.RectangleObject;
import processing.core.PImage;
import processing.core.PVector;
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

        public int whichSideCollide(Ball ball) {
                float[] ballCenter = ball.getCenter();
                float radius = ball.getRadius();
                float[] vel = ball.getVelocity();
                float[] leftNextLocation = new float[] { ballCenter[0] - radius + vel[0],
                                ballCenter[1] + vel[1] };
                float[] rightNextLocation = new float[] { ballCenter[0] + radius +
                                vel[0],
                                ballCenter[1] };
                float[] topNextLocation = new float[] { ballCenter[0] +
                                vel[0],
                                ballCenter[1] - radius + vel[1] };
                float[] bottomNextLocation = new float[] { ballCenter[0] +
                                vel[0],
                                ballCenter[1] + radius + vel[1] };
                boolean collideLeft = leftNextLocation[0] >= x1 && leftNextLocation[0] <= x2 &&
                                leftNextLocation[1] >= y1
                                && leftNextLocation[1] <= y2;
                boolean collideRight = rightNextLocation[0] >= x1 && rightNextLocation[0] <= x2
                                && rightNextLocation[1] >= y1
                                && rightNextLocation[1] <= y2;
                boolean collideTop = topNextLocation[0] >= x1 && topNextLocation[0] <= x2
                                && topNextLocation[1] >= y1
                                && topNextLocation[1] <= y2;
                boolean collideBottom = bottomNextLocation[0] >= x1 && bottomNextLocation[0] <= x2
                                && bottomNextLocation[1] >= y1
                                && bottomNextLocation[1] <= y2;
                if (collideBottom) {
                        return 0;
                } else if (collideLeft) {
                        return 1;
                } else if (collideTop) {
                        return 2;
                } else if (collideRight) {
                        return 3;
                } else {
                        // edge
                        // System.out.printf("radius : %.4f", radius);
                        return 4;
                }
        }

        public boolean intersectEdge(Ball ball) {
                float[] ballCenter = ball.getCenter();
                float radius = ball.getRadius();
                float[] vel = ball.getVelocity();
                float[] topLeftNextLocation = new float[] { ballCenter[0] - (float) (radius /
                                Math.sqrt(2)) + vel[0],
                                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
                float[] topRightNextLocation = new float[] { ballCenter[0] + (float) (radius
                                / Math.sqrt(2)) + vel[0],
                                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
                float[] bottomLeftNextLocation = new float[] { ballCenter[0] - (float) (radius / Math.sqrt(2)) + vel[0],
                                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };
                float[] bottomRightNextLocation = new float[] {
                                ballCenter[0] + (float) (radius / Math.sqrt(2)) + vel[0],
                                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };

                boolean collideTopLeft = topLeftNextLocation[0] >= x1 &&
                                topLeftNextLocation[0] <= x2 &&
                                topLeftNextLocation[1] >= y1 && topLeftNextLocation[1] <= y2;
                boolean collideTopRight = topRightNextLocation[0] >= x1 &&
                                topRightNextLocation[0] <= x2 &&
                                topRightNextLocation[1] >= y1 && topRightNextLocation[1] <= y2;
                boolean collideBottomLeft = bottomLeftNextLocation[0] >= x1 &&
                                bottomLeftNextLocation[0] <= x2 &&
                                bottomLeftNextLocation[1] >= y1 && bottomLeftNextLocation[1] <= y2;
                boolean collideBottomRight = bottomRightNextLocation[0] >= x1 &&
                                bottomRightNextLocation[0] <= x2 &&
                                bottomRightNextLocation[1] >= y1 && bottomRightNextLocation[1] <= y2;
                return collideTopLeft || collideTopRight || collideBottomLeft ||
                                collideBottomRight;
        }

        public boolean intersectTopLeft(Ball ball) {
                float[] ballCenter = ball.getCenter();
                float radius = ball.getRadius();
                float[] vel = ball.getVelocity();
                float[] topLeftNextLocation = new float[] { ballCenter[0] - (float) (radius /
                                Math.sqrt(2)) + vel[0],
                                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
                boolean collideTopLeft = topLeftNextLocation[0] >= x1 &&
                                topLeftNextLocation[0] <= x2 &&
                                topLeftNextLocation[1] >= y1 && topLeftNextLocation[1] <= y2;
                return collideTopLeft;
        }

        public boolean intersectTopRight(Ball ball) {
                float[] ballCenter = ball.getCenter();
                float radius = ball.getRadius();
                float[] vel = ball.getVelocity();
                float[] topRightNextLocation = new float[] { ballCenter[0] + (float) (radius
                                / Math.sqrt(2)) + vel[0],
                                ballCenter[1] - (float) (radius / Math.sqrt(2)) + vel[1] };
                boolean collideTopRight = topRightNextLocation[0] >= x1 &&
                                topRightNextLocation[0] <= x2 &&
                                topRightNextLocation[1] >= y1 && topRightNextLocation[1] <= y2;
                return collideTopRight;
        }

        public boolean intersectBottomLeft(Ball ball) {
                float[] ballCenter = ball.getCenter();
                float radius = ball.getRadius();
                float[] vel = ball.getVelocity();
                float[] bottomLeftNextLocation = new float[] { ballCenter[0] - (float) (radius / Math.sqrt(2)) + vel[0],
                                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };
                boolean collideBottomLeft = bottomLeftNextLocation[0] >= x1 &&
                                bottomLeftNextLocation[0] <= x2 &&
                                bottomLeftNextLocation[1] >= y1 && bottomLeftNextLocation[1] <= y2;
                return collideBottomLeft;
        }

        public boolean intersectBottomRight(Ball ball) {
                float[] ballCenter = ball.getCenter();
                float radius = ball.getRadius();
                float[] vel = ball.getVelocity();
                float[] bottomRightNextLocation = new float[] {
                                ballCenter[0] + (float) (radius / Math.sqrt(2)) + vel[0],
                                ballCenter[1] + (float) (radius / Math.sqrt(2)) + vel[1] };

                boolean collideBottomRight = bottomRightNextLocation[0] >= x1 &&
                                bottomRightNextLocation[0] <= x2 &&
                                bottomRightNextLocation[1] >= y1 && bottomRightNextLocation[1] <= y2;
                return collideBottomRight;

        }

        @Override
        public boolean intersect(Ball ball) {
                float[] ballCenter = ball.getCenter();
                float radius = ball.getRadius();
                float[] vel = ball.getVelocity();
                float[] leftNextLocation = new float[] { ballCenter[0] - radius + vel[0],
                                ballCenter[1] + vel[1] };
                float[] rightNextLocation = new float[] { ballCenter[0] + radius +
                                vel[0],
                                ballCenter[1] };
                float[] topNextLocation = new float[] { ballCenter[0] +
                                vel[0],
                                ballCenter[1] - radius + vel[1] };
                float[] bottomNextLocation = new float[] { ballCenter[0] +
                                vel[0],
                                ballCenter[1] + radius + vel[1] };
                boolean collideLeft = leftNextLocation[0] >= x1 && leftNextLocation[0] <= x2
                                &&
                                leftNextLocation[1] >= y1
                                && leftNextLocation[1] <= y2;
                boolean collideTop = rightNextLocation[0] >= x1 && rightNextLocation[0] <= x2
                                && rightNextLocation[1] >= y1
                                && rightNextLocation[1] <= y2;
                boolean collideRight = topNextLocation[0] >= x1 && topNextLocation[0] <= x2
                                && topNextLocation[1] >= y1
                                && topNextLocation[1] <= y2;
                boolean collideBottom = bottomNextLocation[0] >= x1 && bottomNextLocation[0] <= x2
                                && bottomNextLocation[1] >= y1
                                && bottomNextLocation[1] <= y2;

                return collideLeft || collideRight || collideBottom || collideTop;
        }

        @Override
        public void interactWithBall(App app, Ball ball) {
                if (!intersect(ball) && !intersectEdge(ball)) {
                        return;
                }

                if (intersect(ball)) {
                        float[][] lines = getWallLines();
                        int lineNum = whichSideCollide(ball);

                        if (lineNum == 4) {
                                return;
                        }

                        float x1 = lines[lineNum][0];
                        float y1 = lines[lineNum][1];
                        float x2 = lines[lineNum][2];
                        float y2 = lines[lineNum][3];
                        float dy = y2 - y1;
                        float dx = x2 - x1;
                        PVector normVec = new PVector(-dy, dx).normalize();

                        // find the closest line
                        ball.reflect(normVec);
                }
                if (intersectTopLeft(ball)) {
                        float[] ballVel = ball.getVelocity();
                        if (ballVel[0] > 0 && ballVel[1] > 0) {
                                ball.inverseVel();
                        } else {
                                PVector normVec = new PVector(-1, -1).normalize();
                                ball.reflect(normVec);
                        }
                } else if (intersectTopRight(ball)) {
                        float[] ballVel = ball.getVelocity();
                        if (ballVel[0] < 0 && ballVel[1] > 0) {
                                ball.inverseVel();
                        } else {
                                PVector normVec = new PVector(1, -1).normalize();
                                ball.reflect(normVec);
                        }
                } else if (intersectBottomLeft(ball)) {
                        float[] ballVel = ball.getVelocity();
                        if (ballVel[0] > 0 && ballVel[1] < 0) {
                                ball.inverseVel();
                        } else {
                                PVector normVec = new PVector(-1, 1).normalize();
                                ball.reflect(normVec);
                        }
                } else if (intersectBottomRight(ball)) {
                        float[] ballVel = ball.getVelocity();
                        if (ballVel[0] < 0 && ballVel[1] < 0) {
                                ball.inverseVel();
                        } else {
                                PVector normVec = new PVector(1, 1).normalize();
                                ball.reflect(normVec);
                        }
                }

                if (getState() != Color.GREY.ordinal()) {
                        ball.updateState(getState());
                }
        }

        public float[][] getWallLines() {
                return new float[][] {
                                { x1, y1, x2, y1 },
                                { x2, y1, x2, y2 },
                                { x2, y2, x1, y2 },
                                { x1, y2, x1, y1 }
                };
        }
}
