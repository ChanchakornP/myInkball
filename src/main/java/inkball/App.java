package inkball;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.io.*;
import java.util.*;

import inkball.object.LineObject;
import inkball.object.StaticObject;
import inkball.state.Color;

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
    public Tile[][] board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];

    public String configPath;

    public static Random random = new Random();
    private HashMap<String, PImage> sprites = new HashMap<>();
    public List<StaticObject> staticObj = new ArrayList<>();
    public List<Ball> Balls = new ArrayList<>();
    public Queue<String> BallsInQueue = new LinkedList<>();
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

    JSONArray levels;

    public void getconfig() {
        JSONObject json;
        json = loadJSONObject(this.configPath);
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

    private HashMap<String, PImage> loadSprites(String[] spriteNames) {
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

    public void addStaticObject(String filename, int i, int current_row) {
        StaticObject obj;
        int xPos = i * CELLSIZE;
        int yPos = current_row * CELLSIZE + TOPBAR;

        if (filename.startsWith("wall")) {
            String[] wallSprites = { "wall0", "wall1", "wall2", "wall3", "wall4" };
            HashMap<String, PImage> wallSpriteMap = loadSprites(wallSprites);
            int state = Character.getNumericValue(filename.charAt(filename.length() - 1));
            obj = new Wall(wallSpriteMap, state, xPos, yPos);
        } else if (filename.startsWith("hole")) {
            String[] holeSprites = { "hole0", "hole1", "hole2", "hole3", "hole4" };
            HashMap<String, PImage> holeSpriteMap = loadSprites(holeSprites);
            int state = Character.getNumericValue(filename.charAt(filename.length() - 1));
            obj = new Hole(holeSpriteMap, state, xPos, yPos);
        } else if (filename.equals("entrypoint")) {
            obj = new EntryPoint(sprites.get(filename), xPos, yPos);
        } else {
            obj = null;
        }

        if (obj != null) {
            staticObj.add(obj);
        }
    }

    public void addDynamicObject(char ballNumber, int i, int current_row) {
        String[] ballSprites = { "ball0", "ball1", "ball2", "ball3", "ball4" };
        HashMap<String, PImage> ballSpriteMap = loadSprites(ballSprites);

        Ball obj = new Ball(ballSpriteMap, Character.getNumericValue(ballNumber),
                i * CELLSIZE, current_row * CELLSIZE + TOPBAR);
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
        board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
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
        getconfig();
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

    public List<LineObject> lines = new ArrayList<>();
    private LineObject currentLine; // To keep track of the current line being drawn

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == App.LEFT) {
            currentLine = new LineObject();
            lines.add(currentLine);
        } else if (e.getButton() == App.RIGHT) {

        }

        // create a new player-drawn line object
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == App.LEFT) {
            int x = e.getX();
            int y = e.getY();
            currentLine.addPoints(x, y);
        } else {

        }
        // add line segments to player-drawn line object if left mouse button is held

        // remove player-drawn line object if right mouse button is held
        // and mouse position collides with the line
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentLine = new LineObject();
    }

    /**
     * Draw all elements in the game by current frame.
     */
    // boolean stageFinished;
    int stage;
    float incMod;
    float decMod;
    int timeLimit;
    int spawnInterval;

    @Override
    public void draw() {

        if (gamestop) {
            text("*** PAUSED ***", 260, 40);
        } else {
            if (BallsInQueue.size() == 0 && Balls.size() == 0) {
                try {
                    JSONObject stageInfo = levels.getJSONObject(stage);
                    String layout = stageInfo.getString("layout");
                    initializeBoard(layout);
                    JSONArray buffer = stageInfo.getJSONArray("balls");
                    for (int i = 0; i < buffer.size(); i++) {
                        BallsInQueue.add(buffer.getString(i));
                    }
                    incMod = stageInfo.getFloat("score_increase_from_hole_capture_modifier");
                    decMod = stageInfo.getFloat("score_decrease_from_wrong_hole_modifier");
                    spawnInterval = stageInfo.getInt("spawn_interval");
                    timeLimit = stageInfo.getInt("time");
                    prevStageTime = millis();
                    spawnBufferTime = millis();
                    stage++;
                } catch (RuntimeException e) {
                    gamestop = true;
                }
            }
            gameContinue();
        }
    }

    private int elapsedTime;
    private int prevStageTime;

    private int spawnBufferTime;
    private int spawnCounter;

    public void timer() {
        elapsedTime = millis() - prevStageTime;
        spawnCounter = millis() - spawnBufferTime;
        int timeLeft;
        textSize(20);
        fill(0);

        if (timeLimit > 0) {
            timeLeft = timeLimit - elapsedTime / 1000;
            String time = "Time: " + Integer.toString(timeLeft);
            text(time, 450, App.TOPBAR - 10);
        } else {
            timeLeft = -1;
            String time = "Time: ";
            text(time, 450, App.TOPBAR - 10);
        }
    }

    public void score() {
        String score = "Score: " + Integer.toString(TotalScore);
        text(score, 450, App.TOPBAR - 40);
    }

    int loadingX = 40;
    int loadingY = 15;
    int loadingWidth = 175;
    int loadingHeight = 35;
    int loadingPad = 3;

    public void drawLoadingBalls() {
        rect(loadingX, loadingY, loadingWidth, loadingHeight);
        String formattedString = String.format("%.01f", (float) (spawnInterval - (float) spawnCounter / 1000));
        text(formattedString, loadingX + loadingWidth + 5, loadingY + loadingHeight / 2);
        int counter = 0;
        for (String s : BallsInQueue) {
            int colorCode = Color.valueOf(s.toUpperCase()).ordinal();
            String filename = "ball" + String.valueOf(colorCode);
            PImage ball = sprites.get(filename);
            image(ball, loadingX + loadingPad + (35 * counter), loadingY + loadingPad);
            counter++;
            if (counter == 5) {
                break;
            }
        }
    }

    public void gameContinue() {
        // ----------------------------------
        // display Board for current level:
        // ----------------------------------
        background(200); // clear previous info

        timer();
        score();
        drawLoadingBalls();

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

        for (LineObject line : lines) {
            line.draw(this);
        }

        for (Ball ball : Balls) {
            Iterator<LineObject> linesIterator = lines.iterator();
            while (linesIterator.hasNext()) {
                LineObject line = linesIterator.next();
                if (line.intersect(ball)) {
                    line.interactWithBall(this, ball);
                    linesIterator.remove();
                }
            }
        }

        for (StaticObject o : staticObj) {
            Iterator<Ball> ballIterator = Balls.iterator();
            while (ballIterator.hasNext()) {
                Ball ball = ballIterator.next();
                o.interactWithBall(this, ball);

                // Remove ball if it is captured by a hole
                if (ball.getCaptured()) {
                    calScore(ball, o);
                    ballIterator.remove();
                }
            }
        }

        // inefficient here. Ignore it for now.
        if (BallsInQueue.size() > 0 && spawnCounter >= spawnInterval * 1000) {
            List<StaticObject> spawners = new ArrayList<>();
            for (StaticObject o : staticObj) {
                if (o.getObjName().equals("EntryPoint")) {
                    spawners.add(o);
                }
            }
            Random random = new Random();

            StaticObject randomEntryPoint = spawners.get(random.nextInt(spawners.size()));

            String ballColor = BallsInQueue.remove();
            String[] ballSprites = { "ball0", "ball1", "ball2", "ball3", "ball4" };
            HashMap<String, PImage> ballSpriteMap = loadSprites(ballSprites);
            float[] spawnPos = randomEntryPoint.getPosition();
            Ball obj = new Ball(ballSpriteMap, Color.valueOf(ballColor.toUpperCase()).ordinal(),
                    spawnPos[0], spawnPos[1]);
            Balls.add(obj);
            spawnBufferTime = millis();
            spawnCounter = millis() - spawnBufferTime;
        }

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

    public int calScore(Ball ball, StaticObject hole) {
        int ballState = ball.getState();
        int holeState = hole.getState();
        if (ballState == holeState) {
            TotalScore += incMod * Color.values()[ballState].getReward();
        } else {
            TotalScore -= decMod * Color.values()[ballState].getPenalty();
            if (TotalScore < 0) {
                TotalScore = 0;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        PApplet.main("inkball.App");
    }

}
