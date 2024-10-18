package inkball;

import processing.core.PApplet;
import processing.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;

public class GuiCollisionTest {
    static App app;

    @BeforeAll
    public static void setup() {
        app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.configPath = "test_config.json";
        app.delay(1000);
    }

    // This is testing the moving of the ball and line collision in GUI.
    @Test
    public void DrawStages() {
        app.setup();
        Object tmp = new String("a");
        MouseEvent mouseLeft = new MouseEvent(tmp, 0, 0, 0, 100, 100, App.LEFT, 0);

        assertEquals(app.lines.size(), 0);
        app.mousePressed(mouseLeft);

        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 200, 100 + 10 * i,
                    App.LEFT,
                    0));
        }
        app.mouseReleased(mouseLeft);

        app.mousePressed(mouseLeft);

        for (int i = 0; i < 50; i++) {
            app.mouseDragged(new MouseEvent(tmp, 0, 0, 0, 400, 100 + 10 * i,
                    App.LEFT,
                    0));
        }
        app.mouseReleased(mouseLeft);
        assertEquals(app.lines.size(), 2);
        app.redraw();

        for (int i = 0; i < 1000; i++) {
            app.redraw();
            app.delay(i / 30);
        }

    }
}