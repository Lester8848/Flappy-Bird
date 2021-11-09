import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Class of the Bird
 * modified by project1 solution by Betty
 */
public class Bird {

    //attributes
    private final Image WING_DOWN_LVL0 = new Image("res/level-0/birdWingDown.png");
    private final Image WING_UP_LVL0 = new Image("res/level-0/birdWingUp.png");
    private final Image WING_DOWN_LVL1 = new Image("res/level-1/birdWingDown.png");
    private final Image WING_UP_LVL1 = new Image("res/level-1/birdWingUp.png");
    private final double X = 200;
    private final double FLY_SIZE = 6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 10;
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;
    private boolean levelUp = false;
    private boolean holdWeapon = false;

    /**
     * The constructor
     */
    public Bird() {
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = WING_DOWN_LVL0.getBoundingBoxAt(new Point(X, y));
    }

    /**
     * This method return the Y coordinate of the bird
     * @return double This returns the Y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * This method return the X coordinate of the bird
     * @return double This returns the X-coordinate
     */
    public double getX() {
        return X;
    }

    /**
     * This return the box if the bird
     * @return Rectangle This return the bird as a rectangle box
     */
    public Rectangle getBox() {
        return boundingBox;
    }

    /**
     * This method set the Y coordinate
     * @param y new Y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * This method set the boolean of levelUp
     * @param levelUp new boolean of levelUp
     */
    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }

    /**
     * This method return if the bird is holding weapons
     * @return boolean This returns if the bird is holding a weapon
     */
    public boolean isHoldWeapon() {
        return holdWeapon;
    }

    /**
     * This method set the boolean of holding a weapon
     * @param holdWeapon new boolean of holding a weapon
     */
    public void setHoldWeapon(boolean holdWeapon) {
        this.holdWeapon = holdWeapon;
    }

    /**
     * This method update the bird
     * @param input input of pressing keys
     */
    // update the bird, modified by the version written by
    // Betty from project-1 solution
    public void update(Input input) {
        frameCount += 1;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = - FLY_SIZE;
            WING_DOWN_LVL0.draw(X, y);
        }
        else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            // wing up
            if (frameCount % SWITCH_FRAME == 0) {
                // level 0
                if (levelUp == false) {
                    WING_UP_LVL0.draw(X, y);
                    boundingBox = WING_UP_LVL0.
                            getBoundingBoxAt(new Point(X, y));
                }
                // level 1
                else {
                    WING_UP_LVL1.draw(X, y);
                    boundingBox = WING_UP_LVL1.
                            getBoundingBoxAt(new Point(X, y));
                }
            }
            // wing down
            else {
                // level 0
                if (levelUp == false) {
                    WING_DOWN_LVL0.draw(X, y);
                    boundingBox = WING_DOWN_LVL0.
                            getBoundingBoxAt(new Point(X, y));
                }
                // level 1
                else {
                    WING_DOWN_LVL1.draw(X, y);
                    boundingBox = WING_DOWN_LVL1.
                            getBoundingBoxAt(new Point(X, y));
                }
            }
        }
        y += yVelocity;
    }
}
