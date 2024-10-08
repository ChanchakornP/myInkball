package inkball;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;

public class StageTest {
    static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.configPath = "test_config.json";
        app.delay(500);
    }

    @Test
    public void DrawStages() {
        app.setup();
        app.delay(300);
        int numStages = app.levels.size();

        for (int i = 0; i < numStages; i++) {
            app.delay(10);
            app.BallsInQueue = new LinkedList<>();
            app.Balls = new LinkedList<>();
            app.timeLimit = 1;
            app.delay(2000);
        }
        // app.delay(2000);
    }
}