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
    }

    @Test
    public void DrawStages() {
        app.setup();
        app.stage = 0;
        assertEquals(app.stage, 0);
        int numStages = app.levels.size();
        for (int i = 0; i < numStages; i++) {
            assertEquals(app.stage, i);
            app.redraw();
            app.delay(1000);
            app.updateStageInfo();
        }
    }
}