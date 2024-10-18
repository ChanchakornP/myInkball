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
        app.noLoop();
        PApplet.runSketch(new String[] { "--headless", "App" }, app);
        app.configPath = "test_config.json";
        app.delay(1000);
    }

    @Test
    public void DrawStages() {
        // Test drawing different stages.
        app.setup();
        app.stage = 0;
        assertEquals(app.stage, 0);
        int numStages = app.levels.size();
        for (int i = 0; i < numStages; i++) {
            assertEquals(app.stage, i);
            app.redraw();
            app.delay(1000);
            app.updateStageInfo();
            app.stage++;
        }
    }
}