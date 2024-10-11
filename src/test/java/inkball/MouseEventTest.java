package inkball;

import processing.core.PApplet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class MouseEventTest {
    static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "--headless", "App" }, app);
        app.delay(1000);
    }

    @Test
    public void testMouseEvent() {
        // Test Create a line and remove a line by just simple click (without dragged)
        app.setup();
        app.redraw();

        Object tmp = new String("a");
        MouseEvent mouseLeft = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.LEFT, 0);

        assertEquals(app.lines.size(), 0);
        app.mousePressed(mouseLeft);

        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i,
                    App.LEFT,
                    0));
        }
        app.mouseReleased(mouseLeft);
        // create a line
        assertEquals(app.lines.size(), 1);

        // remove a line by right clicked
        MouseEvent mouseRightClicked = new MouseEvent(tmp, 0, 0, 0, 490, 490, App.RIGHT,
                0);
        app.mousePressed(mouseRightClicked);
        app.mouseReleased(mouseRightClicked);

        // The line should be remove
        assertEquals(app.lines.size(), 0);

        assertEquals(app.lines.size(), 0);
        app.mousePressed(mouseLeft);

        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i,
                    App.LEFT,
                    0));
        }
        app.mouseReleased(mouseLeft);
        // create a line
        assertEquals(app.lines.size(), 1);

        app.controlPressed = true;
        // remove a line by right clicked
        app.mousePressed(mouseLeft);
        app.mouseReleased(mouseLeft);
        assertEquals(app.lines.size(), 0);
    }

    @Test
    void testMouseEventTwo() {
        // Test Create a line and remove a line by right dragged
        app.setup();
        app.redraw();

        Object tmp = new String("a");
        MouseEvent mouseLeft = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.LEFT, 0);

        // create a line
        assertEquals(app.lines.size(), 0);
        app.mousePressed(mouseLeft);

        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i,
                    App.LEFT,
                    0));
        }
        app.mouseReleased(mouseLeft);
        assertEquals(app.lines.size(), 1);

        // remove a line by right dragged
        MouseEvent mouseRightMissed = new MouseEvent(tmp, 0, 0, 0, 0, 0, App.RIGHT,
                0);
        app.mouseReleased(mouseRightMissed);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i,
                    App.RIGHT,
                    0));
        }
        app.mouseReleased(mouseRightMissed);
        // successfully remove the line
        assertEquals(app.lines.size(), 0);
    }

    @Test
    void testMouseEventThree() {
        // Test Create a line and remove a line by left click + CTRL
        app.setup();

        Object tmp = new String("a");
        MouseEvent mouseLeft = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.LEFT, 0);

        // create a line
        assertEquals(app.lines.size(), 0);
        app.mousePressed(mouseLeft);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i,
                    App.LEFT,
                    0));
        }
        app.mouseReleased(mouseLeft);
        assertEquals(app.lines.size(), 1);

        // remove a line by left click + CTRL
        KeyEvent keyCTRL = new KeyEvent(tmp, (long) 0, 0, 0, '0', KeyEvent.CTRL,
                false);
        app.keyPressed(keyCTRL);
        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 100 + 10 * i, 100 + 10 * i,
                    App.LEFT,
                    0));
        }
        // successfully remove the line
        assertEquals(app.lines.size(), 0);

    }
}