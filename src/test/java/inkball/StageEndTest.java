package inkball;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.*;

public class StageEndTest {
    static App app;

    @Test
    public void testEndStage() {
        // Test moving walls at the end of the stage
        app = new App();
        app.configPath = "test_config.json";
        app.loop();
        PApplet.runSketch(new String[] { "AppStage" }, app);
        app.setup();
        app.BallsInQueue = new LinkedList<>();
        app.Balls = new LinkedList<>();
        app.delay(1000); // delay is to give time to initialise stuff before drawing begins

        app.timeLimit = 60;
        assertEquals(app.BallsInQueue.size() == 0, true);
        assertEquals(app.Balls.size() == 0, true);
        app.delay(3000); // delay is to give time to initialise stuff before drawing begins
    }
}