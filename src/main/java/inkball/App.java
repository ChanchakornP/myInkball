package inkball;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.*;
import java.util.*;

import inkball.object.StaticObject;
import inkball.state.Color;

/**
 * The main application of the game.
 */
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

    public static final int FPS = 30;

    public String configPath;

    public static Random random = new Random();
    public HashMap<String, PImage> sprites = new HashMap<>();
    public List<StaticObject> staticObj = new ArrayList<>();
    public List<Straightline> hiddenBorder = new ArrayList<>();

    public List<Tile> tiles = new ArrayList<>();
    public List<Ball> Balls = new ArrayList<>();
    public Queue<String> BallsInQueue = new LinkedList<>();
    private int BallsInQueuePadding = 35;
    public int TotalScore;

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
                "wall4",
                "acc_tile0",
                "acc_tile1",
                "acc_tile2",
                "acc_tile3",
                "timer_tile0",
                "timer_tile1",
                "timer_tile2",
                "timer_tile3",
                "timer_tile4"
        };

        for (int i = 0; i < local_sprites.length; i++) {
            PImage result;
            result = loadImage(
                    this.getClass().getResource(local_sprites[i] + ".png").getPath().toLowerCase(Locale.ROOT)
                            .replace("%20", " "));
            sprites.put(local_sprites[i], result);
        }
    }

    JSONArray levels;

    /**
     * Read json file given by the directory
     * 
     * @param configPath
     */
    public void getconfig(String configPath) {
        JSONObject json;
        json = loadJSONObject(configPath);
        levels = json.getJSONArray("levels");
        JSONObject incScore = json.getJSONObject("score_increase_from_hole_capture");
        for (Object k : incScore.keys()) {
            String key = k.toString();
            int reward = incScore.getInt(key);
            Color.setReward(key, reward);
        }
        JSONObject punishScore = json.getJSONObject("score_decrease_from_wrong_hole");
        for (Object k : punishScore.keys()) {
            String key = k.toString();
            int penalty = punishScore.getInt(key);
            Color.setPenalty(key, penalty);
        }
    }

    /**
     * Get sprites and return hashmap.
     * 
     * @param spriteNames
     * @return
     */
    public HashMap<String, PImage> loadSprites(String[] spriteNames) {
        HashMap<String, PImage> sprites = new HashMap<>();
        for (String spriteName : spriteNames) {
            PImage result = loadImage(
                    this.getClass()
                            .getResource(spriteName + ".png")
                            .getPath()
                            .toLowerCase(Locale.ROOT)
                            .replace("%20", " "));
            sprites.put(spriteName, result);
        }
        return sprites;
    }

    /**
     * Add hidden border, it should be bounced back when there is no wall.
     */
    public void initializeHiddenWall() {
        // 4 hidden borders
        hiddenBorder.add(new Straightline(0, TOPBAR, WIDTH, TOPBAR));
        hiddenBorder.add(new Straightline(WIDTH, TOPBAR, WIDTH, HEIGHT));
        hiddenBorder.add(new Straightline(WIDTH, HEIGHT, 0, HEIGHT));
        hiddenBorder.add(new Straightline(0, HEIGHT, 0, TOPBAR));
    }

    /**
     * The function to add static object, given two consecutive character c1, c2
     * from .txt file.
     * 
     * @param c1
     * @param c2
     * @param i
     * @param current_row
     */
    public void initializeStaticObject(char c1, char c2, int i, int current_row) {
        float x = i * CELLSIZE;
        float y = current_row * CELLSIZE + TOPBAR;

        if (c1 != ' ') {
            if (c2 != 'H' && c2 != 'B') {
                if (c1 == 'X') {
                    staticObj.add(new Wall(sprites.get("wall0"), 0, x, y));
                } else if (c1 == '1') {
                    staticObj.add(new Wall(sprites.get("wall1"), 1, x, y));
                } else if (c1 == '2') {
                    staticObj.add(new Wall(sprites.get("wall2"), 2, x, y));
                } else if (c1 == '3') {
                    staticObj.add(new Wall(sprites.get("wall3"), 3, x, y));
                } else if (c1 == '4') {
                    staticObj.add(new Wall(sprites.get("wall4"), 4, x, y));
                } else if (c1 == 'S') {
                    staticObj.add(new EntryPoint(sprites.get("entrypoint"), x, y));
                } else if (c1 == '^') {
                    staticObj.add(new AcceleratedTile(sprites.get("acc_tile0"), 0, x, y));
                } else if (c1 == 'v') {
                    staticObj.add(new AcceleratedTile(sprites.get("acc_tile1"), 1, x, y));
                } else if (c1 == '>') {
                    staticObj.add(new AcceleratedTile(sprites.get("acc_tile2"), 2, x, y));
                } else if (c1 == '<') {
                    staticObj.add(new AcceleratedTile(sprites.get("acc_tile3"), 3, x, y));
                } else if (c1 == 'T') {
                    String[] timerTileSprites = { "timer_tile0", "timer_tile1", "timer_tile2", "timer_tile3",
                            "timer_tile4" };
                    HashMap<String, PImage> timerTileSpritesMap = loadSprites(timerTileSprites);
                    int state = 4;
                    staticObj.add(new TimerTile(timerTileSpritesMap, state, x, y));
                }
            } else if (c2 == 'H') {
                x = (i - 1) * CELLSIZE;
                int state = Character.getNumericValue(c1);
                String holeSpiriteFilename = "hole" + String.valueOf(c1);
                staticObj.add(new Hole(sprites.get(holeSpiriteFilename), state, x, y));

            } else if (c2 == 'B') {
                x = (i - 1) * CELLSIZE;
                String[] ballSprites = { "ball0", "ball1", "ball2", "ball3", "ball4" };
                HashMap<String, PImage> ballSpriteMap = loadSprites(ballSprites);

                Ball obj = new Ball(ballSpriteMap, Character.getNumericValue(c1),
                        x, y);
                Balls.add(obj);
            }
        }
        // add a tile to all places
        PImage objImg;
        objImg = sprites.get("tile");
        tiles.add(new Tile(objImg, i * CELLSIZE, current_row * CELLSIZE + TOPBAR));
    }

    /**
     * Setup the board and objects from .txt file
     * 
     * @param filename
     */
    public void initializeBoard(String filename) {
        staticObj = new ArrayList<>();
        Balls = new ArrayList<>();
        lines = new ArrayList<>();
        try {
            File mytxt = new File(filename);
            Scanner sc = new Scanner(mytxt);
            int current_row = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                char c1 = ' ';
                char c2 = ' ';
                for (int i = 0; i < BOARD_WIDTH; i++) {
                    c1 = line.charAt(i);
                    if (i == 0) {
                        c2 = ' ';
                    } else {
                        c2 = line.charAt(i - 1);
                    }
                    initializeStaticObject(c1, c2, i, current_row);

                }
                current_row += 1;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            gamestop = true;
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
        getconfig(this.configPath);
        updateStageInfo();
        initializeHiddenWall();
        stage = 0;
        TotalScore = 0;
        controlPressed = false;

    }

    boolean gamestop;
    boolean controlPressed;

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ') {
            gamestop = ((gamestop || true) && !(gamestop && true)); // toggle
        } else if (event.getKey() == 'r') {
            setup();
        } else if (event.getKeyCode() == KeyEvent.CTRL) {
            controlPressed = true;
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.CTRL) {
            controlPressed = false;
        }
    }

    public List<DrawingLine> lines = new ArrayList<>();
    private DrawingLine currentLine; // To keep track of the current line being drawn

    /**
     * Receive mouse pressed signal.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == App.LEFT && !controlPressed) {
            currentLine = new DrawingLine();
            lines.add(currentLine);
        } else if (e.getButton() == App.LEFT && controlPressed) {
            // remove line when control and left click
            Iterator<DrawingLine> linesIterator = lines.iterator();
            while (linesIterator.hasNext()) {
                DrawingLine line = linesIterator.next();
                if (line.intersect(e.getX(), e.getY())) {
                    linesIterator.remove();
                }
            }
        } else if (e.getButton() == App.RIGHT) {
            // remove line when right click
            Iterator<DrawingLine> linesIterator = lines.iterator();
            while (linesIterator.hasNext()) {
                DrawingLine line = linesIterator.next();
                if (line.intersect(e.getX(), e.getY())) {
                    linesIterator.remove();
                }
            }
        }
    }

    /**
     * Receive mouse dragged signal.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == App.LEFT && !controlPressed) {
            int x = e.getX();
            int y = e.getY();
            currentLine.addPoints(x, y);
        } else if (e.getButton() == App.LEFT && controlPressed) {
            // remove line when control and left click
            Iterator<DrawingLine> linesIterator = lines.iterator();
            while (linesIterator.hasNext()) {
                DrawingLine line = linesIterator.next();
                if (line.intersect(e.getX(), e.getY())) {
                    linesIterator.remove();
                }
            }
        } else if (e.getButton() == App.RIGHT) {
            // remove line when right click
            Iterator<DrawingLine> linesIterator = lines.iterator();
            while (linesIterator.hasNext()) {
                DrawingLine line = linesIterator.next();
                if (line.intersect(e.getX(), e.getY())) {
                    linesIterator.remove();
                }
            }
        }
    }

    /**
     * Receive mouse released signal.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        currentLine = new DrawingLine();
    }

    int stage;
    float incMod;
    float decMod;
    int timeLimit;
    int spawnInterval;
    int timedTilesSecond;

    /**
     * When the current stage is cleared, read new config of the next stage.
     */
    public void updateStageInfo() {
        JSONObject stageInfo = levels.getJSONObject(stage);
        String layout = stageInfo.getString("layout");
        initializeBoard(layout);
        JSONArray buffer = stageInfo.getJSONArray("balls");
        BallsInQueue = new LinkedList<>();
        for (int i = 0; i < buffer.size(); i++) {
            BallsInQueue.add(buffer.getString(i));
        }
        incMod = stageInfo.getFloat("score_increase_from_hole_capture_modifier");
        decMod = stageInfo.getFloat("score_decrease_from_wrong_hole_modifier");
        spawnInterval = stageInfo.getInt("spawn_interval");
        try {
            timeLimit = stageInfo.getInt("time");
        } catch (RuntimeException e) {
            timeLimit = -1; // undefined
        }
        try {
            timedTilesSecond = stageInfo.getInt("timedtile_second_disappear");
        } catch (RuntimeException e) {
            timedTilesSecond = 10;
        }
        spawnCounter = spawnInterval;
        stage++;
        frameCount = 0;
        frameOffset = 0;
        stageEnd = false;
    }

    /**
     * Draw the game every frame.
     */
    @Override
    public void draw() {
        background(200); // clear previous info
        strokeWeight(0);
        noStroke();

        drawLoadingBalls();
        timer();
        score();

        for (Tile tile : tiles) {
            tile.draw(this);
        }

        for (StaticObject o : staticObj) {
            o.draw(this);
        }

        for (Ball o : Balls) {
            o.draw(this);
        }

        if (timeLeft == 0 && (BallsInQueue.size() != 0 || Balls.size() != 0)) {
            text("*** TIME’S UP ***", 260, 40);
            return;
        }

        for (DrawingLine line : lines) {
            stroke(0);
            strokeWeight(10);
            line.draw(this);
        }

        if (gamestop) {
            // pause
            text("*** PAUSED ***", 260, 40);
        } else {
            // run the game
            // if the stage is end.
            stageEnd = BallsInQueue.size() == 0 && Balls.size() == 0;
            if (stageEnd) {
                if (stage < levels.size()) {
                    endStageDisplay();
                } else {
                    text("=== ENDED ===", 260, 40);
                }
            } else {
                gameContinue();
            }
        }
    }

    private int[] locMovingWall1 = new int[] { 0, 0 };
    private int[] locMovingWall2 = new int[] { 17, 17 };
    boolean gameCleared;

    /**
     * If the stage is cleared, the stage displays the moving yellow wall around the
     * board.
     */
    public void endStageDisplay() {
        // create the yellow wall and overwrite on the board instead.
        timeLeft--;
        TotalScore++;
        frameCount++;

        movingWall();

        if (timeLeft <= 0) {
            if (stage < levels.size()) {
                updateStageInfo();
            } else {
                gameCleared = true;
            }
        }
    }

    /**
     * moving the yellow walls at the end of the stage.
     */
    public void movingWall() {
        PImage wall = sprites.get("wall" + String.valueOf(Color.YELLOW.ordinal()));
        image(wall, locMovingWall1[0] * CELLSIZE, locMovingWall1[1] * CELLSIZE + TOPBAR);
        image(wall, locMovingWall2[0] * CELLSIZE, locMovingWall2[1] * CELLSIZE + TOPBAR);
        movingWallState(locMovingWall1);
        movingWallState(locMovingWall2);
    }

    /**
     * a logic to move yellow wall around the board
     * 
     * @param locMovingWall
     */
    public void movingWallState(int[] locMovingWall) {
        int x = locMovingWall[0];
        int y = locMovingWall[1];
        if (y == 0 & x < 17) {
            locMovingWall[0] += 1;
        } else if (x == 17 && y < 17) {
            locMovingWall[1] += 1;
        } else if (y == 17 && x <= 17 && x > 0) {
            locMovingWall[0] -= 1;
        } else if (x == 0 && y <= 17) {
            locMovingWall[1] -= 1;
        }
    }

    private float spawnCounter;
    public float timeLeft;
    private int frameCount;
    private int frameOffset;
    private boolean stageEnd;

    /**
     * A timer of each stage.
     */
    public void timer() {
        textSize(20);
        fill(0);
        if (timeLimit > 0) {
            if (!stageEnd) {
                timeLeft = timeLimit - (frameCount / FPS);
            }
            String time = "Time: " + String.format("%.0f", timeLeft);
            text(time, 450, App.TOPBAR - 10);
        } else {
            timeLeft = -1;
            String time = "Time: ";
            text(time, 450, App.TOPBAR - 10);
        }
    }

    private int loadingX = 40;
    private int loadingY = 15;
    private int loadingWidth = 175;
    private int loadingHeight = 35;
    private int loadingPad = 3;

    private int slideCounter = 0;

    /**
     * Displays unloaded balls at the top-left of the GUI.
     */
    public void drawLoadingBalls() {
        fill(0);
        rect(loadingX, loadingY, loadingWidth, loadingHeight);
        int counter = 0;
        // draw balls in the queue
        for (String s : BallsInQueue) {
            int colorCode = Color.valueOf(s.toUpperCase()).ordinal();
            String filename = "ball" + String.valueOf(colorCode);
            PImage ball = sprites.get(filename);
            image(ball, loadingX + loadingPad + (BallsInQueuePadding * counter) + slideCounter, loadingY + loadingPad);
            counter++;
            if (counter == 5) {
                break;
            }
        }
        fill(200);
        rect(loadingX + loadingWidth, loadingY, loadingX + loadingWidth + 30, loadingHeight, 0);

        // draw spawning time
        String formattedString;
        if (spawnCounter > 0) {
            formattedString = String.format("%.01f", spawnCounter);
        } else {
            formattedString = "";
        }
        if (slideCounter > 0) {
            slideCounter--;
        }
        fill(0);
        text(formattedString, loadingX + loadingWidth + 5, loadingY + loadingHeight / 2);

    }

    /**
     * If the game is not stopped and not finished, compute all objects each frame.
     */
    public void gameContinue() {
        // ----------------------------------
        // display Board for current level:
        // ----------------------------------
        frameCount++;
        spawnCounter = spawnInterval - (float) (frameCount - frameOffset) / FPS;
        for (Ball ball : Balls) {
            for (Straightline line : hiddenBorder) {
                if (line.intersect(ball)) {
                    line.interactWithBall(this, ball);
                }
            }
        }

        for (Ball ball : Balls) {
            Iterator<DrawingLine> linesIterator = lines.iterator();
            while (linesIterator.hasNext()) {
                DrawingLine line = linesIterator.next();
                if (line.intersect(ball)) {
                    line.interactWithBall(this, ball);
                    linesIterator.remove();
                }
            }
        }

        // interact objects with the ball
        for (StaticObject o : staticObj) {
            Iterator<Ball> ballIterator = Balls.iterator();
            while (ballIterator.hasNext()) {
                Ball ball = ballIterator.next();
                // if the ball intersect -> do something
                if (o.intersect(ball)) {
                    o.interactWithBall(this, ball);
                }

                // Remove ball if it is captured by a hole
                if (ball.getCaptured()) {
                    calScore(ball, o);
                    ballIterator.remove();
                }
            }

            // update timerTile state
            if (o.getObjName().equals("timerTile") && o.getState() > 0) {
                int timerTileCounter = o.getCounter();
                if (timerTileCounter > FPS * timedTilesSecond) {
                    o.resetCounter();
                    int currentState = o.getState();
                    o.updateState(currentState - 1);
                } else {
                    o.incCounter();
                }
            }
        }

        // inefficient here. Ignore it for now.
        if (BallsInQueue.size() > 0 && spawnCounter <= 0) {
            List<StaticObject> spawners = new ArrayList<>();
            for (StaticObject o : staticObj) {
                if (o.getObjName().equals("EntryPoint")) {
                    slideCounter = BallsInQueuePadding;
                    spawners.add(o);
                }
            }
            // Check if there is a spawner
            if (spawners.size() > 0) {
                Random random = new Random();

                StaticObject randomEntryPoint = spawners.get(random.nextInt(spawners.size()));

                String ballColor = BallsInQueue.remove();
                String[] ballSprites = { "ball0", "ball1", "ball2", "ball3", "ball4" };
                HashMap<String, PImage> ballSpriteMap = loadSprites(ballSprites);
                float[] spawnPos = randomEntryPoint.getPosition();
                Ball obj = new Ball(ballSpriteMap, Color.valueOf(ballColor.toUpperCase()).ordinal(),
                        spawnPos[0], spawnPos[1]);
                Balls.add(obj);
                frameOffset = frameCount;
                spawnCounter = spawnInterval;
            }
        }

        // move the balls
        for (Ball ball : Balls) {
            ball.move();
        }
    }

    /**
     * Display the score at the top-right
     */
    public void score() {
        String score = "Score: " + Integer.toString(TotalScore);
        text(score, 450, App.TOPBAR - 40);
    }

    /**
     * The logic that calculates the score when the hole captures the ball.
     * 
     * @param ball
     * @param hole
     */
    public void calScore(Ball ball, StaticObject hole) {
        int ballState = ball.getState();
        int holeState = hole.getState();
        if (ballState == holeState) {
            TotalScore += incMod * Color.values()[ballState].getReward();
        } else if (holeState == Color.GREY.ordinal()) {
            TotalScore += incMod * Color.values()[ballState].getReward();
        } else if (ballState == Color.GREY.ordinal()) {
            TotalScore += incMod * Color.values()[ballState].getReward();
        } else {
            TotalScore -= decMod * Color.values()[ballState].getPenalty();
            if (TotalScore < 0) {
                TotalScore = 0;
            }
            BallsInQueue.add(Color.getColorName(ballState));
        }
    }

    public static void main(String[] args) {
        PApplet.main("inkball.App");
    }

}
