package inkball;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import processing.core.PImage;
import inkball.object.LineObject;
import inkball.object.StaticObject;
import inkball.state.Color;

public class SampleTest {

    // @Test
    // public void simpleTest() {
    // App app = new App();
    // app.loop();
    // PApplet.runSketch(new String[] { "App" }, app);
    // app.setup();
    // app.delay(1000); // delay is to give time to initialise stuff before drawing
    // begins
    // }

    // Create collision test cases of a ball and a wall
    @Test
    public void testBallWallIntersect() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();

        // Create a ball object at topleft position is 32, 32
        // then set it's velocity to 2
        // Create a wall object at the same position.
        // must be collide.
        Ball ball = new Ball(app.sprites.get("ball0"), 32, 32);
        float[] ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);
        Wall wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);

        // ********************************************** //
        // test the ball collides to the left side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 7, 32);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the left side of the wall.
        // It should collide. its moving with velocityX = 1
        ball = new Ball(app.sprites.get("ball0"), 7, 32);
        ballVel = new float[] { 1, 0 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the top side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 32, 7);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the top side of the wall.
        // It should collide. its moving with velocityY = 1
        ball = new Ball(app.sprites.get("ball0"), 32, 7);
        ballVel = new float[] { 0, 1 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the right side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 65, 32);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the right side of the wall.
        // It should collide. its moving with velocityY = 1
        ball = new Ball(app.sprites.get("ball0"), 65, 32);
        ballVel = new float[] { -1, 0 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the bottom side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 32, 65);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the bottom side of the wall.
        // It should collide. its moving with velocityY = 1
        ball = new Ball(app.sprites.get("ball0"), 32, 65);
        ballVel = new float[] { 0, -1 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the corner side of the wall.
        ball = new Ball(app.sprites.get("ball0"), 53, 53);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        ball = new Ball(app.sprites.get("ball0"), 53, 53);
        ballVel = new float[] { -1, -1 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //
    }

    // Create ball bouncing test cases
    @Test
    public void testBallWallInteraction() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        Ball ball;
        float[] ballVel;
        Wall wall;
        // ********************************************** //
        // The ball moves to the right, should be bounced to left.
        ball = new Ball(app.sprites.get("ball0"), 7, 32);
        ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { -2, 2, 0 }); // diagonal moves

        ballVel = new float[] { 2, 0 };
        ball.setVel(ballVel);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { -2, 0, 0 }); // perpendicular moeves

        // ********************************************** //

        // ********************************************** //
        // The ball moves to the bottom, should be bounced to top.
        ball = new Ball(app.sprites.get("ball0"), 32, 7);
        ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 2, -2, 0 }); // diagonal moves

        ballVel = new float[] { 0, 2 };
        ball.setVel(ballVel);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 0, -2, 0 }); // perpendicular moeves
        // ********************************************** //

        // ********************************************** //
        // The ball moves to the left, should be bounced to right.
        ball = new Ball(app.sprites.get("ball0"), 65, 32);
        ballVel = new float[] { -2, 2 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 2, 2, 0 }); // diagonal moves

        ballVel = new float[] { -2, 0 };
        ball.setVel(ballVel);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 2, 0, 0 }); // perpendicular moeves
        // ********************************************** //

        // ********************************************** //
        // The ball moves to the bottom, should be bounced to top.
        ball = new Ball(app.sprites.get("ball0"), 32, 7);
        ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 2, -2, 0 }); // diagonal moves

        ballVel = new float[] { 0, 2 };
        ball.setVel(ballVel);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 0, -2, 0 }); // perpendicular moeves
        // ********************************************** //

        // ********************************************** //
        // The ball moves to the top, should be bounced to the bottom.
        ball = new Ball(app.sprites.get("ball0"), 32, 65);
        ballVel = new float[] { 2, -2 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites, 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 2, 2, 0 }); // diagonal moves

        ballVel = new float[] { 0, -2 };
        ball.setVel(ballVel);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 0, 2, 0 }); // perpendicular moeves
        // ********************************************** //
    }

    // Create score calculation logic
    @Test
    public void testUpdatingScore() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        Ball greyBall, orangeBall, blueBall, greenBall, yellowBall;
        StaticObject greyHole, orangeHole, blueHole, greenHole, yellowHole;
        HashMap<String, PImage> ballSpriteMap = app.sprites;

        greyBall = new Ball(ballSpriteMap, Color.GREY.ordinal(), 0, 0);
        orangeBall = new Ball(ballSpriteMap, Color.ORANGE.ordinal(), 0, 0);
        blueBall = new Ball(ballSpriteMap, Color.BLUE.ordinal(), 0, 0);
        greenBall = new Ball(ballSpriteMap, Color.GREEN.ordinal(), 0, 0);
        yellowBall = new Ball(ballSpriteMap, Color.YELLOW.ordinal(), 0, 0);

        HashMap<String, PImage> holeSpriteMap = app.sprites;
        greyHole = new Hole(holeSpriteMap, Color.GREY.ordinal(), 0, 0);
        orangeHole = new Hole(holeSpriteMap, Color.ORANGE.ordinal(), 0, 0);
        blueHole = new Hole(holeSpriteMap, Color.BLUE.ordinal(), 0, 0);
        greenHole = new Hole(holeSpriteMap, Color.GREEN.ordinal(), 0, 0);
        yellowHole = new Hole(holeSpriteMap, Color.YELLOW.ordinal(), 0, 0);

        // Matched, increase the score
        app.calScore(greyBall, greyHole);
        assertEquals(app.TotalScore, 70);
        app.calScore(orangeBall, orangeHole);
        assertEquals(app.TotalScore, 120);
        app.calScore(blueBall, blueHole);
        assertEquals(app.TotalScore, 170);
        app.calScore(greenBall, greenHole);
        assertEquals(app.TotalScore, 220);
        app.calScore(yellowBall, yellowHole);
        assertEquals(app.TotalScore, 320);

        // unmatched, decrease the score
        app.calScore(greyBall, orangeHole);
        assertEquals(app.TotalScore, 320);
        app.calScore(greyBall, blueHole);
        assertEquals(app.TotalScore, 320);
        app.calScore(greyBall, greenHole);
        assertEquals(app.TotalScore, 320);
        app.calScore(greyBall, yellowHole);
        assertEquals(app.TotalScore, 320);

        // unmatched, decrease the score
        app.calScore(orangeBall, yellowHole);
        assertEquals(app.TotalScore, 295);
        app.calScore(orangeBall, greyHole);
        assertEquals(app.TotalScore, 270);
        app.calScore(orangeBall, blueHole);
        assertEquals(app.TotalScore, 245);
        app.calScore(orangeBall, greenHole);
        assertEquals(app.TotalScore, 220);

        // unmatched, decrease the score
        app.calScore(blueBall, yellowHole);
        assertEquals(app.TotalScore, 195);
        app.calScore(blueBall, greyHole);
        assertEquals(app.TotalScore, 170);
        app.calScore(blueBall, orangeHole);
        assertEquals(app.TotalScore, 145);
        app.calScore(blueBall, greenHole);
        assertEquals(app.TotalScore, 120);

        // unmatched, decrease the score
        app.calScore(greenBall, yellowHole);
        assertEquals(app.TotalScore, 95);
        app.calScore(greenBall, greyHole);
        assertEquals(app.TotalScore, 70);
        app.calScore(greenBall, blueHole);
        assertEquals(app.TotalScore, 45);
        app.calScore(greenBall, orangeHole);
        assertEquals(app.TotalScore, 20);

        // add more score
        app.calScore(yellowBall, yellowHole);
        assertEquals(app.TotalScore, 120);
        app.calScore(yellowBall, yellowHole);
        assertEquals(app.TotalScore, 220);
        app.calScore(yellowBall, yellowHole);
        assertEquals(app.TotalScore, 320);

        // unmatched, decrease the score
        app.calScore(yellowBall, greyHole);
        assertEquals(app.TotalScore, 220);
        app.calScore(yellowBall, blueHole);
        assertEquals(app.TotalScore, 120);
        app.calScore(yellowBall, greenHole);
        assertEquals(app.TotalScore, 20);
        app.calScore(yellowBall, orangeHole);
        assertEquals(app.TotalScore, 0); // Cannot below than zero
    }

    // test lineIntersect
    @Test
    public void testBallLineIntersect() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        Ball ball;
        float[] ballVel;
        LineObject testedLine;
        // ********************************************** //
        // test horizontal line
        testedLine = new LineObject();
        for (int i = 0; i < 100; i++) {
            testedLine.addPoints(i, 32);
        }
        ball = new Ball(app.sprites.get("ball0"), 50, 12);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        assertEquals(testedLine.intersect(ball), false);

        ball = new Ball(app.sprites.get("ball0"), 50, 12);
        ballVel = new float[] { 0, 1 };
        ball.setVel(ballVel);
        assertEquals(testedLine.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test verticalLine line
        testedLine = new LineObject();
        for (int i = 0; i < 100; i++) {
            testedLine.addPoints(32, i);
        }
        ball = new Ball(app.sprites.get("ball0"), 12, 50);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        assertEquals(testedLine.intersect(ball), false);

        ball = new Ball(app.sprites.get("ball0"), 12, 50);
        ballVel = new float[] { 1, 0 };
        ball.setVel(ballVel);
        assertEquals(testedLine.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test diagonal line
        testedLine = new LineObject();
        for (int i = 0; i < 100; i++) {
            testedLine.addPoints(i, 100 - i);
        }
        ball = new Ball(app.sprites.get("ball0"), 32, 32);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        assertEquals(testedLine.intersect(ball), false);

        ball = new Ball(app.sprites.get("ball0"), 32, 32);
        ballVel = new float[] { 1, 1 };
        ball.setVel(ballVel);
        assertEquals(testedLine.intersect(ball), true);
        // ********************************************** //

    }
    // test lineBouncing
}

// gradle run Run the program
// gradle test Run the testcases

// Please ensure you leave comments in your testcases explaining what the
// testcase is testing.
// Your mark will be based off the average of branches and instructions code
// coverage.
// To run the testcases and generate the jacoco code coverage report:
// gradle test jacocoTestReport
