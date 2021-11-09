import bagel.Image;

/**
 * The class of plastic pipes
 */
public class PlasticPipeSet extends PipeSets{
    /**
     * The constructor
     * @param IMAGE image of the pipe
     * @param isLevelUp if the game is leveled up or not
     */
    public PlasticPipeSet(Image IMAGE, boolean isLevelUp) {
        super(IMAGE, isLevelUp);
    }

    /**
     * This method update the pipes state
     * @param timeScale the timescale
     */
    public void update(int timeScale) {
        super.update(timeScale);
    }
}
