package inkball;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    }

}

// gradle run Run the program
// gradle test Run the testcases

// Please ensure you leave comments in your testcases explaining what the
// testcase is testing.
// Your mark will be based off the average of branches and instructions code
// coverage.
// To run the testcases and generate the jacoco code coverage report:
// gradle test jacocoTestReport
