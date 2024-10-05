package inkball;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.*;
import processing.core.PApplet;

public class TimerTest {
    static App app;

    @Test
    public void testNoGivenTime() {
        // Test layout when given timelimit = -1
        app = new App();
        // app.configPath = "test_config.json";
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.timeLimit = -1;
        // app.delay(3000);
    }

    @Test
    public void testTimeLeft() {
        // Test timeout
        app = new App();
        // app.configPath = "test_config.json";
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.timeLimit = 1;
        // app.delay(3000);
    }

    @Test
    public void noConfigFile() {
        // Test load not found config
        app = new App();
        // app.configPath = "test_config.json";
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.configPath = "nofile.json";
    }
}
