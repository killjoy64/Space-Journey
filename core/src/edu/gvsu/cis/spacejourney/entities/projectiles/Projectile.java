package edu.gvsu.cis.spacejourney.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool.Poolable;
import edu.gvsu.cis.spacejourney.entities.Entity;

public abstract class Projectile extends Entity implements Poolable {

    private boolean isAlive;

    public Projectile(Stage stage, World world, TextureRegion region) {
        super(stage, world, region);
        this.isAlive = false;
    }

    public void spawn(float x, float y) {
        this.setX(x);
        this.setY(y);
        this.isAlive = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (this.outOfBounds()) {
            this.isAlive = false;
        }

    }

    @Override
    public void reset() {
        this.setX(0.0f);
        this.setY(0.0f);
        this.isAlive = false;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

}
