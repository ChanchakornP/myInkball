package inkball;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.*;

public class StageTest {
    static App app;

    @Test
    public void DrawStages() {
        // Draw Every Stage in the config file
        app = new App();
        app.configPath = "test_config.json";
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        // app.delay(100);
        int numStages = app.levels.size();
        for (int i = 0; i < numStages - 1; i++) {
            app.stage = i;
            app.updateStageInfo();
            app.delay(5000);
        }
    }
}