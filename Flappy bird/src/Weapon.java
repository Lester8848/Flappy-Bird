import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

/**
 * Class of weapons, including rocks and bombs
 */
public class Weapon {

    //attributes
    private double weaponSpeed = 5;
    private double weaponX = 1200;
    private boolean isPickedUp = false;
    private boolean isShoot = false;
    private boolean disappear = false;
    private int frames = 0;
    private final double START = 100;
    private final double END = 500;
    private double weaponY;
    private final Image WEAPON_IMAGE;
    private final double RANDOM_POSITION;
    private final double THE_SCALE = 1.5;
    private final double OG_SPEED = 5;

    /**
     * This method construct the Weapon
     * @param WEAPON_IMAGE Image of the weapon
     */
    public Weapon(Image WEAPON_IMAGE) {
        this.WEAPON_IMAGE = WEAPON_IMAGE;
        double rand = new Random().nextDouble();
        RANDOM_POSITION = START + (rand * (END-START));
        this.weaponY = RANDOM_POSITION;
    }

    /**
     * This method set the weaponX
     * @param weaponX param to set the weaponX
     */
    public void setWeaponX(double weaponX) {
        this.weaponX = weaponX;
    }

    /**
     * This method set the weaponY
     * @param weaponY param to set the weaponY
     */
    public void setWeaponY(double weaponY) {
        this.weaponY = weaponY;
    }

    /**
     * This method return if the weapon is picked up or not
     * @return boolean This return if the weapon is picked up or not
     */
    public boolean isPickedUp() {
        return isPickedUp;
    }

    /**
     * This method set the boolean of isPickedUp
     * @param pickedUp param to set the isPickedUp
     */
    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    /**
     * This method check if the weapon is disappeared
     * @return boolean This return if the weapon is disappeared or not
     */
    public boolean isDisappear() {
        return disappear;
    }

    /**
     * This method set the disappear boolean
     * @param disappear param to set the disappear
     */
    public void setDisappear(boolean disappear) {
        this.disappear = disappear;
    }

    /**
     * This method return if the weapon is shot
     * @return boolean This return if the weapon is shot
     */
    public boolean isShoot() {
        return isShoot;
    }

    /**
     * This method get the weapon image
     * @return Image This returns the image of the weapon
     */
    public Image getWEAPON_IMAGE() {
        return WEAPON_IMAGE;
    }

    /**
     * This method get the box of the weapon
     * @return Rectangle This returns the rectangle box of the weapon
     */
    public Rectangle getBox() {
        return WEAPON_IMAGE.getBoundingBoxAt(new Point(weaponX, weaponY));
    }

    /**
     * This method update the state of weapons with birds
     * @param input input of pressing keys
     * @param bird the bird
     * @param timescale timescale
     */
    //update relationships between weapons and the bird
    public void update(Input input, Bird bird, int timescale) {
        updateSpeed(timescale);
        // the weapon move from right to left before being picked up
        if (!isPickedUp) {
            this.weaponX -= weaponSpeed;
        }
        // when the weapon is picked up, it is ready to shoot
        else {
            if (bird.isHoldWeapon() && !isShoot) {
                // press S to shoot
                if (input.wasPressed(Keys.S)) {
                    bird.setHoldWeapon(false);
                    this.isShoot = true;
                }
                // before shooting, the weapon is attached to the bird
                setWeaponX(bird.getBox().right());
                setWeaponY(bird.getY());
            }
        }
    }

    /**
     * This method update the speed of weapon
     * @param timeScale timescale affect the speed
     */
    //update weapon speed due to the timescale
    public void updateSpeed(int timeScale) {
        weaponSpeed = OG_SPEED;
        for(int i = 1; i < timeScale; i++) {
            weaponSpeed *= THE_SCALE;
        }
    }

    /**
     * This method render the shooting weapon
     * @param shootFrame frames of shooting
     */
    //render weapons when shot
    public void shootWeapon(int shootFrame) {
        if (isShoot) {
            if (frames <= shootFrame) {
                this.weaponX += weaponSpeed;
                frames += 1;
            }
            else {
                this.disappear = true;
            }
        }
        // draw valid weapons
        if (!disappear) {
            WEAPON_IMAGE.draw(weaponX, weaponY);
        }
    }
}
