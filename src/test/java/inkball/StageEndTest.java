package inkball;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;

public class StageEndTest {
    static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.configPath = "test_movingWall.json";
        app.delay(1000);
    }

    @Test
    public void testEndGame() {
        // Test moving walls at the end of the stage
        // Display End on Top
        app.setup();
        app.BallsInQueue = new LinkedList<>();
        app.Balls = new LinkedList<>();
        app.timeLimit = 60;
        assertEquals(app.BallsInQueue.size() == 0, true);
        assertEquals(app.Balls.size() == 0, true);
        app.loop();
        app.delay(5000);
    }

    @Test
    public void testEndStage() {
        // Test moving walls at the end of the stage
        // Moving to next Stage
        app.configPath = "test_config.json";
        app.setup();
        app.BallsInQueue = new LinkedList<>();
        app.Balls = new LinkedList<>();
        app.timeLimit = 60;
        assertEquals(app.BallsInQueue.size() == 0, true);
        assertEquals(app.Balls.size() == 0, true);
        app.loop();
        app.delay(5000);
    }
}