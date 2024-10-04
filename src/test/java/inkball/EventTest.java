package inkball;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import java.util.*;

public class EventTest {
    @Test
    public void testKeyEvent() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(3000);

        Object tmp = new String("a");
        KeyEvent keyStop = new KeyEvent(tmp, (long) 0, 0, 0, ' ', 0, false);
        app.keyPressed(keyStop);
        app.keyReleased(keyStop);
        app.delay(1000);
        app.keyPressed(keyStop);
        app.keyReleased(keyStop);

        KeyEvent keyRestart = new KeyEvent(tmp, (long) 0, 0, 0, 'r', 0, false);
        app.keyPressed(keyRestart);
        app.keyReleased(keyRestart);
        app.delay(1000);

        KeyEvent keyCTRL = new KeyEvent(tmp, (long) 0, 0, 0, '0', KeyEvent.CTRL,
                false);
        app.keyPressed(keyCTRL);
        app.keyReleased(keyCTRL);
        app.delay(1000);

        KeyEvent keyUNK = new KeyEvent(tmp, (long) 0, 0, 0, '0', 0, false);
        app.keyPressed(keyUNK);
        app.keyReleased(keyUNK);
        app.delay(3000);

    }

    @Test
    public void testMouseEvent() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(3000);

        // create a line
        Object tmp = new String("a");
        MouseEvent mouseLeft = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.LEFT, 0);
        app.mousePressed(mouseLeft);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + i, 100 + i, App.LEFT,
                    0));
            app.delay(30);

        }
        app.mouseReleased(mouseLeft);

        // remove a line by right click
        MouseEvent mouseRight = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.RIGHT, 0);
        app.mousePressed(mouseRight);

        // create a line
        app.mousePressed(mouseLeft);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + i, 100 + i, App.LEFT,
                    0));
            app.delay(30);

        }
        app.mouseReleased(mouseLeft);

        // remove a line by right dragged
        MouseEvent mouseRightMissed = new MouseEvent(tmp, 0, 0, 0, 0, 0, App.RIGHT, 0);
        app.mouseReleased(mouseRightMissed);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + i, 100 + i, App.RIGHT,
                    0));
            app.delay(10);
        }
        app.mouseReleased(mouseRightMissed);

        // create a line
        app.mousePressed(mouseLeft);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + i, 100 + i, App.LEFT,
                    0));
            app.delay(30);

        }
        app.mouseReleased(mouseLeft);

        // remove a line by left click + CTRL
        KeyEvent keyCTRL = new KeyEvent(tmp, (long) 0, 0, 0, '0', KeyEvent.CTRL,
                false);
        app.keyPressed(keyCTRL);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + i, 100 + i, App.LEFT,
                    0));
            app.delay(30);

        }
        app.delay(3000);

    }

    @Test
    public void testNoGivenTime() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.timeLimit = -1;
        app.delay(1000);
    }

    @Test
    public void testTimeLeft() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.timeLimit = 1;
        app.delay(1000);
    }

    @Test
    public void noConfigFile() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.configPath = "nofile.json";
    }

    @Test
    public void testEndStage() {
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(3000);
        app.BallsInQueue = new LinkedList<>();
        app.Balls = new LinkedList<>();
        app.timeLimit = 60;
        assertEquals(app.BallsInQueue.size() == 0, true);
        assertEquals(app.Balls.size() == 0, true);
        app.delay(5000); // delay is to give time to initialise stuff before drawing begins
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
