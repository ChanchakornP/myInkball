package inkball;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import processing.core.PImage;
import processing.core.PVector;
import inkball.object.StaticObject;
import inkball.state.Color;

public class LogicTest {
    static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "--headless", "App" }, app);
        app.delay(2000);
    }

    // Create collision test cases of a ball and a wall
    @Test
    public void testBallWallIntersect() {
        app.setup();

        // Create a ball object at topleft position is 32, 32
        // then set it's velocity to 2
        // Create a wall object at the same position.
        // must be collide.
        Ball ball = new Ball(app.sprites.get("ball0"), 32, 32);
        float[] ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);
        StaticObject wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);

        // ********************************************** //
        // test the ball collides to the left side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 7, 32);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the left side of the wall.
        // It should collide. its moving with velocityX = 1
        ball = new Ball(app.sprites.get("ball0"), 7, 32);
        ballVel = new float[] { 1, 0 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the top side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 32, 7);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the top side of the wall.
        // It should collide. its moving with velocityY = 1
        ball = new Ball(app.sprites.get("ball0"), 32, 7);
        ballVel = new float[] { 0, 1 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the right side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 65, 32);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the right side of the wall.
        // It should collide. its moving with velocityY = 1
        ball = new Ball(app.sprites.get("ball0"), 65, 32);
        ballVel = new float[] { -1, 0 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the bottom side of the wall.
        // It should not because its not moving
        ball = new Ball(app.sprites.get("ball0"), 32, 65);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), false);

        // test the ball collides to the bottom side of the wall.
        // It should collide. its moving with velocityY = 1
        ball = new Ball(app.sprites.get("ball0"), 32, 65);
        ballVel = new float[] { 0, -1 }; // ball move to the right
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //

        // ********************************************** //
        // test the ball collides to the corner side of the wall.

        ball = new Ball(app.sprites.get("ball0"), 61, 61);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), false);
        // Collide bottom-right of the wall
        ball = new Ball(app.sprites.get("ball0"), 61, 61);
        ballVel = new float[] { -1, -1 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);

        ball = new Ball(app.sprites.get("ball0"), 11, 11);
        ballVel = new float[] { 0, 0 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), false);
        // Collide top-left of the wall
        ball = new Ball(app.sprites.get("ball0"), 11, 11);
        ballVel = new float[] { 1, 1 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        // ********************************************** //
        app.delay(1000);
    }

    // Create ball bouncing test cases
    @Test
    public void testBallWallInteraction() {
        app.setup();
        Ball ball;
        float[] ballVel;
        StaticObject wall;
        // ********************************************** //
        // The ball moves to the right, should be bounced to left.
        ball = new Ball(app.sprites.get("ball0"), 7, 32);
        ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
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
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
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
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
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
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
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
        wall = new Wall(app.sprites.get("wall0"), 0, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 2, 2, 0 }); // diagonal moves

        ballVel = new float[] { 0, -2 };
        ball.setVel(ballVel);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 0, 2, 0 }); // perpendicular moeves
        // ********************************************** //

        app.delay(1000);
    }

    // Create score calculation logic
    @Test
    public void testUpdatingScore() {
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
        greyHole = new Hole(holeSpriteMap.get("hole" + String.valueOf(Color.GREY.ordinal())), Color.GREY.ordinal(), 0,
                0);
        orangeHole = new Hole(holeSpriteMap.get("hole" + String.valueOf(Color.ORANGE.ordinal())),
                Color.ORANGE.ordinal(), 0, 0);
        blueHole = new Hole(holeSpriteMap.get("hole" + String.valueOf(Color.BLUE.ordinal())), Color.BLUE.ordinal(), 0,
                0);
        greenHole = new Hole(holeSpriteMap.get("hole" + String.valueOf(Color.GREEN.ordinal())), Color.GREEN.ordinal(),
                0, 0);
        yellowHole = new Hole(holeSpriteMap.get(("hole" + String.valueOf(Color.YELLOW.ordinal()))),
                Color.YELLOW.ordinal(), 0, 0);

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

        // greyball, to any hole
        app.calScore(greyBall, orangeHole);
        assertEquals(app.TotalScore, 390);
        app.calScore(greyBall, blueHole);
        assertEquals(app.TotalScore, 460);
        app.calScore(greyBall, greenHole);
        assertEquals(app.TotalScore, 530);
        app.calScore(greyBall, yellowHole);
        assertEquals(app.TotalScore, 600);

        // unmatched, decrease the score
        app.calScore(orangeBall, yellowHole);
        assertEquals(app.TotalScore, 575);
        app.calScore(orangeBall, blueHole);
        assertEquals(app.TotalScore, 550);
        app.calScore(orangeBall, greenHole);
        assertEquals(app.TotalScore, 525);

        // unmatched, decrease the score
        app.calScore(blueBall, yellowHole);
        assertEquals(app.TotalScore, 500);
        app.calScore(blueBall, orangeHole);
        assertEquals(app.TotalScore, 475);
        app.calScore(blueBall, greenHole);
        assertEquals(app.TotalScore, 450);

        // unmatched, decrease the score
        app.calScore(greenBall, yellowHole);
        assertEquals(app.TotalScore, 425);
        app.calScore(greenBall, blueHole);
        assertEquals(app.TotalScore, 400);
        app.calScore(greenBall, orangeHole);
        assertEquals(app.TotalScore, 375);

        // unmatched, decrease the score
        app.calScore(yellowBall, orangeHole);
        assertEquals(app.TotalScore, 275);
        app.calScore(yellowBall, blueHole);
        assertEquals(app.TotalScore, 175);
        app.calScore(yellowBall, greenHole);
        assertEquals(app.TotalScore, 75);
        app.calScore(yellowBall, orangeHole);
        assertEquals(app.TotalScore, 0); // Cannot below than zero

        // grayHole
        app.calScore(yellowBall, greyHole);
        assertEquals(app.TotalScore, 100); // Cannot below than zero

        app.delay(1000);

    }

    // test lineIntersect
    @Test
    public void testBallLineIntersect() {
        app.setup();

        Ball ball;
        float[] ballVel;
        DrawingLine testedLine;
        // ********************************************** //
        // test horizontal line
        testedLine = new DrawingLine();
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
        testedLine = new DrawingLine();
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
        testedLine = new DrawingLine();
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

        app.delay(1000);

    }

    // test lineBouncing
    @Test
    public void testBallLineInteraction() {
        app.setup();
        Ball ball;
        float[] ballVel;
        DrawingLine testedLine;

        // ********************************************** //
        // test horizontal line
        testedLine = new DrawingLine();
        for (int i = 0; i < 100; i++) {
            testedLine.addPoints(i, 32);
        }
        ball = new Ball(app.sprites.get("ball0"), 50, 12);
        ballVel = new float[] { 0, 1 };
        ball.setVel(ballVel);
        testedLine.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { 0, -1, 0 }); // perpendicular moeves
        app.delay(1000);

    }

    public static void assertFloatEquals(double expected, double actual, double tolerance) {
        if (Double.compare(expected, -0.0) == 0 && Double.compare(actual, 0.0) == 0) {
            return; // Treat -0.0 and 0.0 as equal
        }
        if (Double.compare(actual, -0.0) == 0 && Double.compare(expected, 0.0) == 0) {
            return; // Treat 0.0 and -0.0 as equal
        }
        // Otherwise, use standard comparison with tolerance
        assertEquals(expected, actual, tolerance);

    }

    @Test
    public void testBallVelocity() {
        // Test reverse velocity
        Ball ball;
        float[] ballVel;

        ball = new Ball(app.sprites.get("ball0"), 50, 12);
        ballVel = new float[] { 0, 1 };
        ball.setVel(ballVel);
        ball.inverseVel();
        assertArrayEquals(ball.getVelocity(), new float[] { 0, -1, 0 }, (float) 0.001); // perpendicular moeves
    }

    @Test
    public void testBallTimerTileIntersect() {
        app.setup();

        // Create a ball object at topleft position is 32, 32
        // then set it's velocity to 2
        // Create a wall object at the same position.
        // must be collide.
        Ball ball = new Ball(app.sprites.get("ball0"), 32, 32);
        float[] ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);

        String[] timerTileSprites = { "timer_tile0", "timer_tile1", "timer_tile2", "timer_tile3",
                "timer_tile4" };
        HashMap<String, PImage> timerTileSpritesMap = app.loadSprites(timerTileSprites);
        int state = 4;
        TimerTile wall = new TimerTile(timerTileSpritesMap, state, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.updateState(0);
        assertEquals(wall.intersect(ball), false);
    }

    @Test
    public void testBallTimerTileInteract() {
        app.setup();

        // Create a ball object at topleft position is 32, 32
        // then set it's velocity to 2
        // Create a wall object at the same position.
        // must be collide.
        Ball ball = new Ball(app.sprites.get("ball0"), 32, 32);
        float[] ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);

        String[] timerTileSprites = { "timer_tile0", "timer_tile1", "timer_tile2", "timer_tile3",
                "timer_tile4" };
        HashMap<String, PImage> timerTileSpritesMap = app.loadSprites(timerTileSprites);
        int state = 4;
        StaticObject wall = new TimerTile(timerTileSpritesMap, state, 32, 32);

        ball = new Ball(app.sprites.get("ball0"), 7, 32);
        ballVel = new float[] { 2, 2 };
        ball.setVel(ballVel);
        wall = new TimerTile(timerTileSpritesMap, state, 32, 32);
        assertEquals(wall.intersect(ball), true);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { -2, 2, 0 }); // diagonal moves

        ballVel = new float[] { 2, 0 };
        ball.setVel(ballVel);
        wall.interactWithBall(app, ball);
        assertArrayEquals(ball.getVelocity(), new float[] { -2, 0, 0 }); // perpendicular moeves

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
