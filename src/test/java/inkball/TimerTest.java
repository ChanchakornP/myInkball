package inkball;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;
import processing.core.PApplet;

public class TimerTest {
    static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.delay(2000);
    }

    @Test
    public void testNoGivenTime() {
        // Test layout when given timelimit = -1
        app.setup();
        app.delay(300);

        app.timeLimit = -1;
        app.delay(1000);
    }

    @Test
    public void testTimeLeft() {
        // Test timeout
        app.setup();
        app.delay(300);

        app.timeLimit = 1;
        app.delay(3000);
    }

    // @Test
    // public void noConfigFile() {
    // // Test load not found config
    // app.setup();
    // app.delay(1000);

    // app.configPath = "nofile.json";
    // }
}