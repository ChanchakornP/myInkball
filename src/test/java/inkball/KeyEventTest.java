package inkball;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import processing.event.KeyEvent;

public class KeyEventTest {
    static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "--headless", "App" }, app);
        app.delay(2000);
    }

    @Test
    public void testKeyEvent() {
        app.setup();

        // Test Pausing
        Object tmp = new String("a");
        KeyEvent keyStop = new KeyEvent(tmp, (long) 0, 0, 0, ' ', 0, false);
        app.keyPressed(keyStop);
        app.keyReleased(keyStop);
        // app.delay(1000);
        app.keyPressed(keyStop);
        app.keyReleased(keyStop);

        // Test restarting
        KeyEvent keyRestart = new KeyEvent(tmp, (long) 0, 0, 0, 'r', 0, false);
        app.keyPressed(keyRestart);
        app.keyReleased(keyRestart);
        // app.delay(1000);

        // Test CTRL
        KeyEvent keyCTRL = new KeyEvent(tmp, (long) 0, 0, 0, '0', KeyEvent.CTRL,
                false);
        app.keyPressed(keyCTRL);
        app.keyReleased(keyCTRL);
        // app.delay(1000);

        // Test other keys
        KeyEvent keyUNK = new KeyEvent(tmp, (long) 0, 0, 0, '0', 0, false);
        app.keyPressed(keyUNK);
        app.keyReleased(keyUNK);
        // app.delay(1000);

    }
}

// gradle run Run the program
// gradle test Run the testcases

// Please ensure you leave comments in your testcases explaining what the
// testcase is testing.
// Your mark will be based off the average of branches and instructions code
// coverage.
// To run the testcases and generate the jacoco code coverage report:
// gradle test jacocoTestReport
