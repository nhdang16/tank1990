package jsd.project.tank90.model.tanks;

import jsd.project.tank90.utils.Direction;
import jsd.project.tank90.utils.Images;

import java.awt.*;

import static jsd.project.tank90.utils.SoundManager.playExplosionSound;
import static jsd.project.tank90.utils.SoundManager.playShotSound;


public class PlayerTank extends Tank {

    private static final int FIRE_INTERVAL = 6; // Frames between shots (to avoid too much shoot)
    // Get images
    private static final Image SHIELD_IMAGE_1 = Images.SHIELD_1;
    private static final Image SHIELD_IMAGE_2 = Images.SHIELD_2;
    // Define spawn position
    private final int spawnX;
    private final int spawnY;
    private final int speed = 1;
    private final int bulletSize = 5;
    private int fireCooldown; // Cooldown timer for firing bullets
    private Image TANK_UP_1 = Images.PLAYER_UP_1_S1;
    private Image TANK_DOWN_1 = Images.PLAYER_DOWN_1_S1;
    private Image TANK_LEFT_1 = Images.PLAYER_LEFT_1_S1;
    private Image TANK_RIGHT_1 = Images.PLAYER_RIGHT_1_S1;
    private Image TANK_UP_2 = Images.PLAYER_UP_2_S1;
    private Image TANK_DOWN_2 = Images.PLAYER_DOWN_2_S1;
    private Image TANK_LEFT_2 = Images.PLAYER_LEFT_2_S1;
    private Image TANK_RIGHT_2 = Images.PLAYER_RIGHT_2_S1;
    private int lifePlusPoints = 0; // To check if 20k points got
    private int points = 0;
    private int life = 4;
    private int bulletSpeed = 2;
    private int maxBullets = 1;
    private int bulletDamage = 1;

    private int star = 0; // Number of star claimed

    private boolean shielded = false; // Shield status
    private boolean shieldToggle = false; // Used to alternate between the two images
    private boolean baseDestroyed = false; // Base status

    public PlayerTank(int x, int y, int size) {
        super(x, y, size, Direction.UP);
        this.spawnX = x;
        this.spawnY = y;
        this.tankImage = TANK_UP_1;
        activateShield(); // Shield when spawned
    }

    @Override
    protected int getBulletSize() {
        return bulletSize;
    }


    @Override
    protected int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    @Override
    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public Image getTankUpImage1() {
        return TANK_UP_1;
    }

    @Override
    public Image getTankDownImage1() {
        return TANK_DOWN_1;
    }

    @Override
    public Image getTankLeftImage1() {
        return TANK_LEFT_1;
    }

    @Override
    public Image getTankRightImage1() {
        return TANK_RIGHT_1;
    }

    @Override
    protected Image getTankUpImage2() {
        return TANK_UP_2;
    }

    @Override
    protected Image getTankDownImage2() {
        return TANK_DOWN_2;
    }

    @Override
    protected Image getTankLeftImage2() {
        return TANK_LEFT_2;
    }

    @Override
    protected Image getTankRightImage2() {
        return TANK_RIGHT_2;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    public int getMaxBullets() {
        return maxBullets;
    }

    public void setMaxBullets(int maxBullets) {
        this.maxBullets = maxBullets;
    }

    public int getPoints() {
        return points;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    // Increase points
    public void increasePoints(int p) {
        points += p;
        lifePlusPoints += p;
    }

    // Increase 1 life every 20k points
    public void checkBonusLife() {
        if (lifePlusPoints >= 20000) {
            life++;
            lifePlusPoints -= 20000;
        }
    }

    @Override
    public void move() {
        if (!baseDestroyed) super.move();
    }

    @Override
    public Bullet shoot() {

        if (fireCooldown <= 0 && !baseDestroyed) {
            Bullet bullet = super.shoot();
            if (bullet != null) {
                playShotSound(); // play sound
                fireCooldown = FIRE_INTERVAL;
            }
            return bullet;
        }
        updateCooldown();
        return null;
    }

    // Update cooldown for shooting
    public void updateCooldown() {
        if (fireCooldown > 0) {
            fireCooldown--;
        }
    }

    // Change images and stats based on star
    public void starCheck() {
        switch (getStar()) {
            case 0 -> {
                setBulletSpeed(2);
                setMaxBullets(1);
                setBulletDamage(1);
                TANK_UP_1 = Images.PLAYER_UP_1_S1;
                TANK_DOWN_1 = Images.PLAYER_DOWN_1_S1;
                TANK_LEFT_1 = Images.PLAYER_LEFT_1_S1;
                TANK_RIGHT_1 = Images.PLAYER_RIGHT_1_S1;
                TANK_UP_2 = Images.PLAYER_UP_2_S1;
                TANK_DOWN_2 = Images.PLAYER_DOWN_2_S1;
                TANK_LEFT_2 = Images.PLAYER_LEFT_2_S1;
                TANK_RIGHT_2 = Images.PLAYER_RIGHT_2_S1;
            }
            case 1 -> {
                setBulletSpeed(3);
                TANK_UP_1 = Images.PLAYER_UP_1_S1;
                TANK_DOWN_1 = Images.PLAYER_DOWN_1_S1;
                TANK_LEFT_1 = Images.PLAYER_LEFT_1_S1;
                TANK_RIGHT_1 = Images.PLAYER_RIGHT_1_S1;
                TANK_UP_2 = Images.PLAYER_UP_2_S1;
                TANK_DOWN_2 = Images.PLAYER_DOWN_2_S1;
                TANK_LEFT_2 = Images.PLAYER_LEFT_2_S1;
                TANK_RIGHT_2 = Images.PLAYER_RIGHT_2_S1;
            }
            case 2 -> {
                setMaxBullets(2);
                TANK_UP_1 = Images.PLAYER_UP_1_S2;
                TANK_DOWN_1 = Images.PLAYER_DOWN_1_S2;
                TANK_LEFT_1 = Images.PLAYER_LEFT_1_S2;
                TANK_RIGHT_1 = Images.PLAYER_RIGHT_1_S2;
                TANK_UP_2 = Images.PLAYER_UP_2_S2;
                TANK_DOWN_2 = Images.PLAYER_DOWN_2_S2;
                TANK_LEFT_2 = Images.PLAYER_LEFT_2_S2;
                TANK_RIGHT_2 = Images.PLAYER_RIGHT_2_S2;
            }
            case 3 -> {
                setBulletDamage(2);
                TANK_UP_1 = Images.PLAYER_UP_1_S3;
                TANK_DOWN_1 = Images.PLAYER_DOWN_1_S3;
                TANK_LEFT_1 = Images.PLAYER_LEFT_1_S3;
                TANK_RIGHT_1 = Images.PLAYER_RIGHT_1_S3;
                TANK_UP_2 = Images.PLAYER_UP_2_S3;
                TANK_DOWN_2 = Images.PLAYER_DOWN_2_S3;
                TANK_LEFT_2 = Images.PLAYER_LEFT_2_S3;
                TANK_RIGHT_2 = Images.PLAYER_RIGHT_2_S3;
            }
        }
    }

    public void claimStar() {
        setStar(Math.min(3, getStar() + 1));// Increase star up to 3
        starCheck();
    }

    // Activate shield
    public void activateShield() {
        new Thread(() -> {
            this.shielded = true;
            try {
                Thread.sleep(5000); // Shield duration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle thread interruption
            } finally {
                this.shielded = false; // Deactivate shield after the duration
            }

        }).start();
    }


    public boolean isShielded() {
        return shielded;
    }

    // Turn base status to destroyed
    public void destroyBase() {
        baseDestroyed = true;
        playExplosionSound();
    }

    @Override
    public void render(Graphics g) {
        if (isDisabled())
            g.drawImage(DEAD_IMAGE, x, y, size, size, null); // render the dead tank image
        else {
            g.drawImage(tankImage, x, y, size, size, null);
            renderBullets(g); // Render bullets fired by the player tank
        }

        checkBonusLife();

        // Render shield effect if active
        if (shielded) {
            int offset = 5; // Adjust for positioning
            Image currentShieldImage = toggleShieldImage(); // Get the blinking shield image
            g.drawImage(currentShieldImage, x - offset, y - offset, size + 2 * offset, size + 2 * offset, null);
            shieldToggle = !shieldToggle;
        }
    }

    private Image toggleShieldImage() {
        // Toggle between the two images for blinking effect
        return shieldToggle ? SHIELD_IMAGE_1 : SHIELD_IMAGE_2;
    }

    // Spawn method (get back to the spawn position)
    public void spawn() {
        enable();
        this.x = spawnX;
        this.y = spawnY;
        setDirection(Direction.UP);
        activateShield();
    }

    // Respawn method
    private void respawn() {
        enable();
        this.x = spawnX;
        this.y = spawnY;
        setDirection(Direction.UP);
        setStar(0);// Reset star
        starCheck();
        activateShield();
    }

    public void takeDamage() {
        if (isShielded()) return;
        new Thread(() -> {
            try {
                setLife(getLife() - 1);//Decrease life
                disable();
                playExplosionSound(); // play sound
                Thread.sleep(1000);// delay 1s before respawn
                if (life > 0) respawn();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
