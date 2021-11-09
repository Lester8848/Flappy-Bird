import bagel.Image;
import bagel.Input;

/**
 * Class of the bomb
 */
public class Bomb extends Weapon{

    //attributes
    private final int SHOOT_FRAME = 50;

    /**
     * Constructor of the Bomb
     * @param WEAPON_IMAGE image of the bomb
     */
    public Bomb(Image WEAPON_IMAGE) {
        super(WEAPON_IMAGE);
    }

    /**
     * This method update the state of the bomb
     * @param input input of pressing keys
     * @param bird the bird
     * @param timeScale current timescale
     */
    public void update(Input input, Bird bird, int timeScale) {
        super.update(input, bird, timeScale);
        super.shootWeapon(SHOOT_FRAME);
    }
}
