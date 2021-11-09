import bagel.Image;
import bagel.Input;

/**
 * Class of the Rock
 */
public class Rock extends Weapon{

    //attributes
    private final int SHOOT_FRAME = 25;

    /**
     * Constructor of Rock
     * @param WEAPON_IMAGE image of the rock
     */
    public Rock(Image WEAPON_IMAGE) {
        super(WEAPON_IMAGE);
    }

    /**
     * This method update the state of the rock
     * @param input input of pressing keys
     * @param bird the bird
     * @param timeScale current timescale
     */
    public void update(Input input, Bird bird, int timeScale) {
        super.update(input, bird, timeScale);
        super.shootWeapon(SHOOT_FRAME);
    }
}
