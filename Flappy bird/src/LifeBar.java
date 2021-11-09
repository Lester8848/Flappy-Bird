import bagel.Image;

/**
 * Class of the life bar
 */
public class LifeBar {

    //attributes
    private final Image FULL_LIFE = new Image("res/level/fullLife.png");
    private final Image NO_LIFE = new Image("res/level/noLife.png");;
    private int maxLife;
    private int life;
    private final int LEVEL_0_LIFE = 3;

    /**
     * Constructor of the LifeBar
     */
    //initiate the life bar from level 0 (3 hearts)
    public LifeBar() {
        this.maxLife = LEVEL_0_LIFE;
        this.life = LEVEL_0_LIFE;
    }

    /**
     * This method return the lives left
     * @return int This returns the number of lives
     */
    public int getLife() {
        return this.life;
    }

    /**
     * This method set the lives left
     * @param life the new number of lives
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * This method set the maximum lives
     * @param maxLife the new number of maximum lives
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * This method draw the lives left on the screen
     */
    //draw life bars on the screen
    public void renderLifeBar() {
        // draw full hearts
        for (int i = 0; i < life; i++) {
            FULL_LIFE.drawFromTopLeft(100+i*50, 15);
        }
        // draw empty hearts
        for (int j = life; j < maxLife; j++) {
            NO_LIFE.drawFromTopLeft(100+j*50, 15);
        }
    }
}
