package inkball;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.io.*;
import java.util.*;

import inkball.interfaces.Collidable;
import inkball.object.DynamicObject;
import inkball.object.StaticObject;

public class App extends PApplet {

    public static final int CELLSIZE = 32; // 8;
    public static final int CELLHEIGHT = 32;

    public static final int CELLAVG = 32;
    public static final int TOPBAR = 64;
    public static int WIDTH = 576; // CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 640; // BOARD_HEIGHT*CELLSIZE+TOPBAR;
    public static final int BOARD_WIDTH = WIDTH / CELLSIZE;
    public static final int BOARD_HEIGHT = (HEIGHT - TOPBAR) / CELLSIZE;

    public static final int INITIAL_PARACHUTES = 1;

    public static final int FPS = 300;
    public Tile[][] board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

    public String configPath;

    public static Random random = new Random();
    private HashMap<String, PImage> sprites = new HashMap<>();
    public List<StaticObject> staticObj = new ArrayList<>();
    public List<Ball> Balls = new ArrayList<>();

    public void getSprite() {
        String[] local_sprites = new String[] {
                "ball0",
                "ball1",
                "ball2",
                "ball3",
                "ball4",
                "entrypoint",
                "hole0",
                "hole1",
                "hole2",
                "hole3",
                "hole4",
                "inkball_spritesheet",
                "tile",
                "wall0",
                "wall1",
                "wall2",
                "wall3",
                "wall4"
        };

        for (int i = 0; i < local_sprites.length; i++) {
            PImage result;
            result = loadImage(
                    this.getClass().getResource(local_sprites[i] + ".png").getPath().toLowerCase(Locale.ROOT)
                            .replace("%20", " "));
            sprites.put(local_sprites[i], result);
        }
    }

    JSONArray level;
    JSONObject incScore;
    JSONObject punishScore;

    public void getconfig() {
        JSONObject json;
        json = loadJSONObject(this.configPath);
        level = json.getJSONArray("levels");
        incScore = json.getJSONObject("score_increase_from_hole_capture");
        punishScore = json.getJSONObject("score_decrease_from_wrong_hole");
    }

    public void addStaticObject(String filename, int i, int current_row) {
        PImage objImg = sprites.get(filename);
        StaticObject obj;

        if (filename.startsWith("wall")) {
            String[] local_sprites = new String[] {
                    "wall0",
                    "wall1",
                    "wall2",
                    "wall3",
                    "wall4",
            };
            HashMap<String, PImage> localSprites = new HashMap<>();

            for (int idx = 0; idx < local_sprites.length; idx++) {
                PImage result;
                result = loadImage(
                        this.getClass().getResource(local_sprites[idx] + ".png").getPath().toLowerCase(Locale.ROOT)
                                .replace("%20", " "));
                localSprites.put(local_sprites[idx], result);
            }
            // obj = new Wall(objImg, i * CELLSIZE, current_row * CELLSIZE + TOPBAR);
            obj = new Wall(localSprites, filename.charAt(filename.length() - 1), i * CELLSIZE,
                    current_row * CELLSIZE + TOPBAR);
        } else if (filename.startsWith("hole")) {
            obj = new Hole(objImg, i * CELLSIZE, current_row * CELLSIZE + TOPBAR);
        } else if (filename.equals("entrypoint")) {
            obj = new EntryPoint(objImg, i * CELLSIZE, current_row * CELLSIZE + TOPBAR);
        } else {
            obj = null;
        }
        staticObj.add(obj);
    }

    public void addDynamicObject(char ballNumber, int i, int current_row) {
        String[] local_sprites = new String[] {
                "ball0",
                "ball1",
                "ball2",
                "ball3",
                "ball4",
        };
        HashMap<String, PImage> localSprites = new HashMap<>();

        for (int idx = 0; idx < local_sprites.length; idx++) {
            PImage result;
            result = loadImage(
                    this.getClass().getResource(local_sprites[idx] + ".png").getPath().toLowerCase(Locale.ROOT)
                            .replace("%20", " "));
            localSprites.put(local_sprites[idx], result);
        }
        Ball obj = new Ball(localSprites, ballNumber, i * CELLSIZE,
                current_row * CELLSIZE + TOPBAR);
        Balls.add(obj);
    }

    public void initializeObjects(char c1, char c2, int i, int current_row) {
        if (c1 != ' ') {
            if (c2 != 'H' && c2 != 'B') {
                if (c1 == 'X') {
                    addStaticObject("wall0", i, current_row);
                } else if (c1 == '1') {
                    addStaticObject("wall1", i, current_row);
                } else if (c1 == '2') {
                    addStaticObject("wall2", i, current_row);
                } else if (c1 == '3') {
                    addStaticObject("wall3", i, current_row);
                } else if (c1 == '4') {
                    addStaticObject("wall4", i, current_row);
                } else if (c1 == 'S') {
                    addStaticObject("entrypoint", i, current_row);
                }
            } else if (c2 == 'H') {
                addStaticObject("hole" + c1, i - 1, current_row);
            } else if (c2 == 'B') {
                addDynamicObject(c1, i - 1, current_row);
            }
        }
    }

    public void initializeBoard(String filename) {
        try {
            File mytxt = new File(filename);
            Scanner sc = new Scanner(mytxt);
            int current_row = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                char c1 = ' ';
                char c2 = ' ';
                for (int i = 0; i < board[current_row].length; i++) {
                    PImage objImg = sprites.get("tile");
                    board[current_row][i] = new Tile(objImg, i * CELLSIZE, current_row * CELLSIZE + TOPBAR);
                    c1 = line.charAt(i);
                    if (i == 0) {
                        c2 = ' ';
                    } else {
                        c2 = line.charAt(i - 1);
                    }
                    initializeObjects(c1, c2, i, current_row);

                }
                current_row += 1;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player
     * and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
        getSprite();
        getconfig();
        initializeBoard("level3.txt");
        // See PApplet javadoc:
        // loadJSONObject(configPath)
        // the image is loaded from relative path: "src/main/resources/inkball/..."
        /*
         * try {
         * result =
         * loadImage(URLDecoder.decode(this.getClass().getResource(filename+".png").
         * getPath(), StandardCharsets.UTF_8.name()));
         * } catch (UnsupportedEncodingException e) {
         * throw new RuntimeException(e);
         * }
         */
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    boolean gamestop;

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == 'r') {
            gamestop = ((gamestop || true) && !(gamestop && true));
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // create a new player-drawn line object
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // add line segments to player-drawn line object if left mouse button is held

        // remove player-drawn line object if right mouse button is held
        // and mouse position collides with the line
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        if (gamestop) {

        } else {
            gameContinue();
        }

    }

    public void gameContinue() {
        // ----------------------------------
        // display Board for current level:
        // ----------------------------------
        for (Tile[] row : board) {
            for (Tile tile : row) {
                tile.draw(this);
            }
        }
        for (StaticObject o : staticObj) {
            o.draw(this);
        }
        for (Ball o : Balls) {
            o.draw(this);
        }
        // Line x = new Line(100, 100, 200, 200);

        for (StaticObject o : staticObj) {
            Iterator<Ball> ballIterator = Balls.iterator();
            while (ballIterator.hasNext()) {
                Ball ball = ballIterator.next();
                if (o.objName.equals("hole")) {
                    if (o.intersect(ball)) {
                        o.interactWithBall(this, ball);
                        if (ball.getCaptured()) {
                            // ballIterator.remove();
                        }
                    }
                } else if (o.objName.equals("wall")) {
                    if (o.intersect(ball)) {
                        o.interactWithBall(this, ball);
                    }
                    // Edge case, when collide at the exact border
                    if (o.intersectEdge(ball)) {
                        ball.inverseVel();
                        if (o.getState() != '0') {
                            ball.updateState(o.getState());
                        }
                    }
                }

            }
        }

        // for (StaticObject o : staticObj) {
        // for (Ball ball : Balls) {
        // if (o.objName.equals("wall")) {
        // if (o.intersect(ball)) {
        // o.interactWithBall(this, ball);
        // }
        // // Edge case, when collide at the exact border
        // if (o.intersectEdge(ball)) {
        // ball.inverseVel();
        // if (o.getState() != '0') {
        // ball.updateState(o.getState());
        // }
        // }
        // }
        // }
        // }

        for (Ball ball : Balls) {
            ball.move();
        }

        // ----------------------------------
        // display score
        // ----------------------------------
        // TODO

        // ----------------------------------
        // ----------------------------------
        // display game end message

    }

    public static void main(String[] args) {
        PApplet.main("inkball.App");
    }

}
