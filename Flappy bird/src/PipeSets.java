import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class of all types pipes
 */
public class PipeSets {

    //attributes
    private final Image PIPE_IMAGE;
    private final int PIPE_GAP = 168;
    private double pipeSpeed = 5;
    private final double TOP_PIPE_Y;
    private final double BOTTOM_PIPE_Y;
    private final DrawOptions ROTATION = new DrawOptions().setRotation(Math.PI);
    private double pipeX = Window.getWidth();
    private final double RANDOM_POSITION;
    private List<Integer> PipeTypes = new ArrayList<>();
    private boolean levelUp;
    private boolean isCollide = false;
    private boolean isPass = false;
    private final double THE_SCALE = 1.5;
    private final double OG_SPEED = 5;

    /**
     * The constructor
     * @param IMAGE image of the pipe
     * @param isLevelUp if the game is leveled up of not
     */
    public PipeSets(Image IMAGE, boolean isLevelUp) {
        this.levelUp = isLevelUp;
        this.PIPE_IMAGE = IMAGE;
        // for level 0, three different types of gap position
        if (!levelUp) {
            PipeTypes.add(100);
            PipeTypes.add(300);
            PipeTypes.add(500);
            Random rand = new Random();
            RANDOM_POSITION = PipeTypes.get(rand.nextInt(PipeTypes.size()));
        }
        // for level 1, the gap position is randomly generated
        else {
            double start = 100;
            double end = 500;
            double rand = new Random().nextDouble();
            RANDOM_POSITION = start + (rand * (end-start));
        }
        this.TOP_PIPE_Y = RANDOM_POSITION - PIPE_IMAGE.getHeight()/2;
        this.BOTTOM_PIPE_Y = PIPE_IMAGE.getHeight()/2 +
                RANDOM_POSITION + PIPE_GAP;
    }

    /**
     * This method gives the box of top pipe
     * @return Rectangle This gives the rectangle box of top pipe
     */
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, TOP_PIPE_Y));

    }

    /**
     * This method gives the box of the bottom pipe
     * @return Rectangle This gives the rectangle box of the bottom pipe
     */
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, BOTTOM_PIPE_Y));

    }

    /**
     * This method returns if the pipe is collided or not
     * @return boolean This gives if the pipe is collided or not
     */
    public boolean getIsCollide() {
        return this.isCollide;
    }

    /**
     * This method sets the collision boolean
     * @param collide new collision boolean
     */
    public void setIsCollide(boolean collide) {
        this.isCollide = collide;
    }

    /**
     * This method get if the pipe is pass
     * @return boolean This returns if the pipe is pass
     */
    public boolean getIsPass() {
        return this.isPass;
    }

    /**
     * This method set the pass boolean
     * @param pass new pass boolean
     */
    public void setIsPass(boolean pass) {
        this.isPass = pass;
    }

    /**
     * This method gives X coordinate of the pipe
     * @return double The X coordinate
     */
    public double getPipeX() {
        return pipeX;
    }

    /**
     * This method gives the top Y coordinate
     * @return double The top Y coordinate of the pipe
     */
    public double getTOP_PIPE_Y() {
        return TOP_PIPE_Y;
    }

    /**
     * This method gives the bottom Y coordinate
     * @return double The bottom Y coordinate of the pipe
     */
    public double getBOTTOM_PIPE_Y() {
        return BOTTOM_PIPE_Y;
    }

    /**
     * This gives a rotation DrawOption
     * @return DrawOption The rotation
     */
    public DrawOptions getROTATOR() {
        return ROTATION;
    }

    /**
     * This method gives the image of the pipe
     * @return Image The pipe image
     */
    public Image getPIPE_IMAGE() {
        return PIPE_IMAGE;
    }

    /**
     * This method update the pipe based on the timescale
     * @param timeScale the timescale
     */
    public void update(int timeScale) {
        updateSpeed(timeScale);
        // draw pipes if they are not collided
        if (!isCollide) {
            PIPE_IMAGE.draw(pipeX, TOP_PIPE_Y);
            PIPE_IMAGE.draw(pipeX, BOTTOM_PIPE_Y, ROTATION);
        }
    }

    /**
     * This method update the speed of pipes based on timescale
     * @param timeScale the timescale
     */
    public void updateSpeed(int timeScale) {
        pipeSpeed = OG_SPEED;
        for (int i = 1; i < timeScale; i++) {
            pipeSpeed *= THE_SCALE;
        }
        pipeX -= pipeSpeed;
    }
}
