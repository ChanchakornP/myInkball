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
        PApplet.runSketch(new String[] { "--headless", "App" }, app);
    }

    @Test
    public void testEndStage() {
        // Test moving walls at the end of the stage
        app.setup();
        app.BallsInQueue = new LinkedList<>();
        app.Balls = new LinkedList<>();
        app.timeLimit = 60;
        assertEquals(app.BallsInQueue.size() == 0, true);
        assertEquals(app.Balls.size() == 0, true);
        // for (int i = 0; i < 100; i++) {
        // app.endStageDisplay();

        // }
        app.loop();
        app.delay(5000);
    }
}