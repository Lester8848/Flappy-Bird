import bagel.*;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 *
 * Please filling your name below
 * @author YD :)
 */
public class ShadowFlap extends AbstractGame {

    //attributes
    private final Image BACKGROUND_IMAGE_LVL0 =
            new Image("res/level-0/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final int FONT_SIZE = 48;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SCORE_MSG_OFFSET = 75;
    private final double Y_COORDINATE = 350;
    private Bird bird;
    private int score;
    private boolean gameOn;
    private boolean win;
    private final Image PLASTIC = new Image("res/level/plasticPipe.png");
    private final Image STEEL = new Image("res/level-1/steelPipe.png");
    private final Image ROCK = new Image("res/level-1/rock.png");
    private final Image BOMB = new Image("res/level-1/bomb.png");
    private boolean isLevelUp = false;
    private ArrayList<PipeSets> plasticPipeSets = new ArrayList<>(); // level 0
    private ArrayList<PipeSets> pipeSets = new ArrayList<>();// level 1
    private int timeScale = 1;
    private final int MAX_SCALE = 5;
    private final int MIN_SCALE = 1;
    private LifeBar lifebar;
    private final int LEVEL_1_LIFE = 6;
    private boolean flameCollision = false;
    private int frameCount = 0;
    private final double SWITCH_FRAME = 100;
    private final double LEVEL_UP_FRAME = 150;
    private final double LEVEL_UP_SCORE = 10;
    private final double WIN_SCORE = 30;
    private final double SCORE_POSITION = 100;
    private final String LEVEL_UP = "LEVEL-UP!";
    private boolean levelUp = false;
    private int levelUpCount = 100;
    private final Image LEVEL1_BACKGROUND =
            new Image("res/level-1/background.png");
    private final double GENERATE_WEAPON = 200;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private final double THE_SCALE = 1.5;

    /**
     * The constructor of ShadowFlap
     */
    //constructor for the ShadowFlap
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        lifebar = new LifeBar(); //
        score = 0;
        gameOn = false;
        win = false;
    }

    /**
     * The main method
     * @param args the inputs
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     * @param input input of pressing keys
     */
    @Override
    public void update(Input input) {
        // render game background based on the level
        if (!isLevelUp) {
            BACKGROUND_IMAGE_LVL0
                    .draw(Window.getWidth()/2, Window.getHeight()/2);
        }
        else {LEVEL1_BACKGROUND
                .draw(Window.getWidth()/2, Window.getHeight()/2);}
        if (input.wasPressed(Keys.ESCAPE)) {Window.close();}
        // before the game start
        if (!gameOn) {
            renderInstructionScreen(input);
        }
        // when the bird has no life, game over
        if ((lifebar.getLife()) == 0) {
            renderGameOverScreen();
        }
        // if bird goes out of the bound,
        // reset the bird position and lose one life
        if (birdOutOfBound()) {
            if ((lifebar.getLife()) > 0) {
                lifebar.setLife(lifebar.getLife()-1);
                bird.setY(Y_COORDINATE);
            }
        }
        // render win screen if win condition detected
        if(win) {
            renderWinScreen();
        }

        // when game is ongoing
        if (gameOn && !(lifebar.getLife() == 0) && !win) {
            // game at level 0 stage
            if (!levelUp) {
                updateScale(input);
                // add plastic pipes
                if (frameCount %
                        (int)(SWITCH_FRAME/Math.pow(THE_SCALE, timeScale-1)) == 0) {
                    plasticPipeSets
                            .add(new PlasticPipeSet(PLASTIC, isLevelUp));
                }
                frameCount ++;
                //update bird, pipes and life information
                bird.update(input);
                Rectangle birdBox = bird.getBox();
                updatePipeSets(plasticPipeSets, birdBox);
                lifebar.renderLifeBar();
            }
            // game at level 1 stage
            if (isLevelUp) {
                updateScale(input);
                // add random pipes
                if (frameCount % (int)
                        (SWITCH_FRAME/Math.pow(THE_SCALE, timeScale-1)) == 0) {
                    randomAddPipe();
                }
                // generate random weapons
                if (frameCount % (int)
                        (GENERATE_WEAPON/Math.pow(THE_SCALE, timeScale-1)) == 0) {
                    randomAddWeapon();
                }
                frameCount += 1;
                //update bird, pipes, weapons and life information
                bird.update(input);
                Rectangle birdBox = bird.getBox();
                updateWeapons(birdBox, input);
                updatePipeSets(pipeSets, birdBox);
                lifebar.renderLifeBar();
            }
        }

        // render level up screen between level 0 game and level 1 game
        if (levelUp && !isLevelUp) {
            renderLevelUpScreen();
            levelUpCount ++;
            if (levelUpCount == LEVEL_UP_FRAME) {
                resetGame();
            }
        }
    }

    //methods
    /**
     * Method to add new pipes randomly
     */
    public void randomAddPipe() {
        Random random = new Random();
        if (random.nextBoolean()) {
            pipeSets.add(new SteelPipeSet(STEEL, isLevelUp));
        }
        else {
            pipeSets.add(new PlasticPipeSet(PLASTIC, isLevelUp));
        }
    }

    /**
     * Method to add new weapons randomly
     */
    public void randomAddWeapon() {
        Random random = new Random();
        if (random.nextBoolean()) {
            weapons.add(new Rock(ROCK));
        }
        else {
            weapons.add(new Bomb(BOMB));
        }
    }

    /**
     * Method to update the timescale
     *
     * @param input the input of pressing keys
     */
    public void updateScale(Input input) {
        if ((input.wasPressed(Keys.L)) && (timeScale < MAX_SCALE)){
            timeScale += 1;
        }
        if ((input.wasPressed(Keys.K)) && (timeScale > MIN_SCALE)){
            timeScale -= 1;
        }
    }

    /**
     * This method is used to update pipes and bird's life information
     *
     * @param pipeSets the arraylist of pipeSets
     * @param birdBox bird as a rectangle
     */
    // based on birds and pipes, update conditions of birds,
    // pipes and bird's life
    public void updatePipeSets(ArrayList<PipeSets> pipeSets,
                               Rectangle birdBox) {
        // for all pipes, if the pipe collides with the bird,
        // update pipe and life information
        for (PipeSets pipeSet : pipeSets) {
            pipeSet.update(timeScale);
            Rectangle topPipeBox = pipeSet.getTopBox();
            Rectangle bottomPipeBox = pipeSet.getBottomBox();
            boolean pipeCollision =
                    detectCollision(birdBox, topPipeBox, bottomPipeBox);
            // only if they collide and they are valid for collision
            if (pipeCollision && !pipeSet.getIsCollide()) {
                lifebar.setLife(lifebar.getLife() - 1);
                pipeSet.setIsCollide(true);
                pipeSet.setIsPass(true);
            }
            //update score
            updateScore(pipeSet);
            // when the pipes are steel,
            // if the pipe flame collides with the bird,
            // update the pipe and life information
            if (pipeSet.getPIPE_IMAGE().equals(STEEL)) {
                SteelPipeSet steelPipe = (SteelPipeSet) pipeSet;
                Rectangle topFlameBox = steelPipe.getTopFlame();
                Rectangle bottomFlameBox = steelPipe.getBottomFlame();
                if (steelPipe.getFrameCount() % 20 == 0) {
                    flameCollision = detectCollision
                            (birdBox, topFlameBox, bottomFlameBox);
                    // only if they collide and they are valid for collision
                    //
                    if (flameCollision) {
                    }
                    //
                    if (flameCollision && !steelPipe.isCollideWithFlame()
                            && !steelPipe.getIsCollide()) {
                        lifebar.setLife(lifebar.getLife() - 1);
                        steelPipe.setCollideWithFlame(true);
                        steelPipe.setIsCollide(true);
                        steelPipe.setIsPass(true);
                    }
                }
            }
            //update score
            //updateScore(pipeSet);
        }
    }

    /**
     * This method update score of the game
     * @param pipeSet the pipes
     */
    //update score for the game
    public void updateScore(PipeSets pipeSet) {
        // when the bird passes the pipe and the pipe
        // hasn't been passed yet, add one score
        if ((bird.getX() > pipeSet.getTopBox().right())
                && (!pipeSet.getIsPass())) {
            score += 1;
            pipeSet.setIsPass(true);
        }
        // show score on screen
        String scoreMSG = SCORE_MSG + score;
        FONT.drawString(scoreMSG,SCORE_POSITION, SCORE_POSITION);
        // level up
        if (score == LEVEL_UP_SCORE && !isLevelUp) {
            levelUp = true;
        }
        // win
        if (score == WIN_SCORE) {
            win = true;
        }
    }

    /**
     * This method reset the game when the game levels up
     */
    public void resetGame() {
        isLevelUp = true;
        gameOn = false;
        bird.setLevelUp(true);
        timeScale = 1;
        lifebar.setMaxLife(LEVEL_1_LIFE);
        lifebar.setLife(LEVEL_1_LIFE);
        score = 0;
        frameCount = 0;
        bird.setY(Y_COORDINATE);
    }

    /**
     * This method render level up screen
     */
    //render level up screen at the centre
    public void renderLevelUpScreen() {
        FONT.drawString(LEVEL_UP,
                (Window.getWidth()/2.0-(FONT.getWidth(LEVEL_UP)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
    }

    /**
     * This method update weapons's relations with the bird
     * @param birdBox the bird as a box
     * @param input input by pressing keys
     */
    // update weapons' relations with the bird
    public void updateWeapons(Rectangle birdBox, Input input) {
        for(Weapon weapon: weapons) {
            weapon.update(input, bird, timeScale);
            Rectangle weaponBox = weapon.getBox();
            if (birdBox.intersects(weaponBox) && !weapon.isPickedUp()) {
                if(!bird.isHoldWeapon()) {
                    bird.setHoldWeapon(true);
                    weapon.setPickedUp(true);
                }
            }
            checkWeaponPipeCollision(weapon);
        }
    }

    /**
     * This method chech if the weapon hit the pipe
     * @param weapon weapon
     */
    // check if the weapon hit pipes and update their corresponding information
    public void checkWeaponPipeCollision(Weapon weapon) {
        Rectangle weaponBox = weapon.getBox();
        // check actions between weapons and pipes
        for(PipeSets pipe: pipeSets) {
            Rectangle topPipeBox = pipe.getTopBox();
            Rectangle bottomPipeBox = pipe.getBottomBox();
            // when the weapon is valid
            if (!weapon.isDisappear() && weapon.isShoot()) {
                boolean weaponCollision =
                        detectCollision(weaponBox, topPipeBox, bottomPipeBox);
                // when a rock hit plastic pipes,
                // the rock and pipes disappear and add score
                if (weapon.getWEAPON_IMAGE().equals(ROCK)
                        && pipe.getPIPE_IMAGE().equals(PLASTIC)
                        && weaponCollision) {
                    weapon.setDisappear(true);
                    pipe.setIsCollide(true);
                    pipe.setIsPass(true);
                    score += 1;
                }
                // when a rock hit steel pipes, the rock disappears
                if (weapon.getWEAPON_IMAGE().equals(ROCK)
                        && pipe.getPIPE_IMAGE().equals(STEEL)
                        && weaponCollision) {
                    weapon.setDisappear(true);
                }
                //when a bomb hit any pipes,
                //the bomb and pipes disappear and add score
                if (weapon.getWEAPON_IMAGE().equals(BOMB)
                        && weaponCollision) {
                    weapon.setDisappear(true);
                    pipe.setIsCollide(true);
                    pipe.setIsPass(true);
                    score += 1;
                }
            }
        }
    }

    // below methods were written by Betty, from project-1 solution

    /**
     * This method detect if the bird is out of bound
     * @return boolean This return if the bird is out of bound or not
     */
    // detect if the bird is out of bound
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    /**
     * This method draw the instruction screen
     * @param input input of pressing keys
     */
    // render instruction screen
    public void renderInstructionScreen(Input input) {
        // paint the instruction on screen
        FONT.drawString(INSTRUCTION_MSG,
                (Window.getWidth()/2.0-(FONT.getWidth(INSTRUCTION_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        if (input.wasPressed(Keys.SPACE)) {
            gameOn = true;
        }
    }

    /**
     * This method draw the game over screen
     */
    // render game over screen
    public void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG,
                (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg,
                (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * This method draw the win screen
     */
    // render win screen
    public void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG,
                (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg,
                (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0-(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    /**
     * this method check if a object collides with other two objects
     * @param theBox the object
     * @param object1 one of the other two object
     * @param object2 one of the other two object
     * @return bollean This returns if the object
     * have collision with the other two objects
     */
    // detect if one object collides with other two objects
    public boolean detectCollision(Rectangle theBox, Rectangle object1, Rectangle object2) {
        // check for collision
        return theBox.intersects(object1) ||
                theBox.intersects(object2);
    }
}
