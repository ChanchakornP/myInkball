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
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.delay(2000);
    }

    @Test
    public void testEndStage() {
        // Test moving walls at the end of the stage
        app.setup();
        app.delay(300);

        app.BallsInQueue = new LinkedList<>();
        app.Balls = new LinkedList<>();
        // app.delay(1000); // delay is to give time to initialise stuff before drawing
        // begins

        app.timeLimit = 60;
        assertEquals(app.BallsInQueue.size() == 0, true);
        assertEquals(app.Balls.size() == 0, true);
        app.delay(5000); // delay is to give time to initialise stuff before drawing
        // begins
    }
}