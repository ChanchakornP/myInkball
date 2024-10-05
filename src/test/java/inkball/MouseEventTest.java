package inkball;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class MouseEventTest {
    static App app;

    @Test
    public void testMouseEvent() {
        // Test leftclick, rightclick, left-dragged, right-dragged, left-release,
        // right-release
        app = new App();
        // app.configPath = "test_config.json";
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();

        // create a line
        Object tmp = new String("a");
        MouseEvent mouseLeft = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.LEFT, 0);
        app.mousePressed(mouseLeft);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i, App.LEFT,
                    0));
            app.delay(10);
        }
        app.mouseReleased(mouseLeft);

        // remove a line by right click
        MouseEvent mouseRight = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.RIGHT, 0);
        app.mousePressed(mouseRight);

        // create a line
        app.mousePressed(mouseLeft);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i, App.LEFT,
                    0));
            app.delay(10);
        }
        app.mouseReleased(mouseLeft);

        // remove a line by right dragged
        MouseEvent mouseRightMissed = new MouseEvent(tmp, 0, 0, 0, 0, 0, App.RIGHT,
                0);
        app.mouseReleased(mouseRightMissed);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i, App.RIGHT,
                    0));
            app.delay(10);
        }
        app.mouseReleased(mouseRightMissed);

        // create a line
        app.mousePressed(mouseLeft);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i, App.LEFT,
                    0));
            app.delay(10);
        }
        app.mouseReleased(mouseLeft);

        // remove a line by left click + CTRL
        KeyEvent keyCTRL = new KeyEvent(tmp, (long) 0, 0, 0, '0', KeyEvent.CTRL,
                false);
        app.keyPressed(keyCTRL);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i, App.LEFT,
                    0));
            app.delay(10);
        }
        // app.delay(3000);

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
