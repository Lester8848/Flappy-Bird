import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The class of steel pipes
 */
public class SteelPipeSet extends PipeSets{

    //attributes
    private final Image FLAME = new Image("res/level-1/flame.png");
    private final double SWITCH_FRAME = 20;
    private final double FLAME_FRAME = 30;
    private int frameCount = 0;
    private int Duration = 0;
    private boolean collideWithFlame = false;

    /**
     * The constructor
     * @param IMAGE steel pipe image
     * @param isLevelUp if the game is leveled up or not
     */
    public SteelPipeSet(Image IMAGE, boolean isLevelUp) {
        super(IMAGE, isLevelUp);
    }

    /**
     * This method return the frame counted
     * @return int The frame counted
     */
    public int getFrameCount() {
        return frameCount;
    }

    /**
     * This method returns the box of top flame
     * @return Rectangle This returns the Rectangle box of the top flame
     */
    public Rectangle getTopFlame() {
        return FLAME.getBoundingBoxAt(new Point(super.getPipeX(),
                super.getTOP_PIPE_Y() + (Window.getHeight()/2) + 10));

    }

    /**
     * This method returns the box of bottom flame
     * @return Rectangle This returns the Rectangle box of the bottom flame
     */
    public Rectangle getBottomFlame() {
        return FLAME.getBoundingBoxAt(new Point(super.getPipeX(),
                super.getBOTTOM_PIPE_Y() - (Window.getHeight()/2) - 10));

    }

    /**
     * This gives if the flame is collided
     * @return boolean If the flame is collided
     */
    public boolean isCollideWithFlame() {
        return collideWithFlame;
    }

    /**
     * This sets the collision boolean of the flame
     * @param collideWithFlame the new boolean of the flame collision
     */
    public void setCollideWithFlame(boolean collideWithFlame) {
        this.collideWithFlame = collideWithFlame;
    }

    /**
     * This method update the game based on timescale
     * @param timeScale the timescale
     */
    // update the flame based on timescale
    public void update(int timeScale) {
        super.update(timeScale);
        // draw flames every 20 frames
        if (frameCount % SWITCH_FRAME == 0) {
            if (!super.getIsCollide() && Duration < FLAME_FRAME) {
                shootFlame();
                frameCount -= 1;
                Duration += 1;
            }
        }
        // reset the duration and add frames
        if (Duration == FLAME_FRAME) {
            Duration = 0;
            frameCount += FLAME_FRAME;
        }
        frameCount += 1;
    }

    /**
     * This method draws flames on the screen
     */
    // draw flames
    public void shootFlame() {
        // top flame
        FLAME.draw(super.getPipeX(),
                super.getTOP_PIPE_Y() + Window.getHeight()/2 + 10);
        // bottom flame
        FLAME.draw(super.getPipeX(),
                super.getBOTTOM_PIPE_Y() - Window.getHeight()/2 - 10,
                super.getROTATOR());
    }
}
