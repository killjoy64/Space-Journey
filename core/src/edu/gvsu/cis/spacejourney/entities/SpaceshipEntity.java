package edu.gvsu.cis.spacejourney.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.util.ZIndex;

public class SpaceshipEntity extends Entity {

    public SpaceshipEntity(Stage stage) {
        super(stage, new TextureRegion(new Texture(Gdx.files.internal("spaceship.png"))));
        this.setZIndex(ZIndex.PLAYER);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        // This returns null because there are no available frames!
        // The underlying Entity class handles this properly.
        return null;
    }

    public void move(Direction direction, float value) {
        float oldX = this.getX();
        float oldY = this.getY();
        switch(direction) {
            case UP:
                this.setY(oldY + value);
                break;
            case DOWN:
                this.setY(oldY - value);
                break;
            case LEFT:
                this.setX(oldX - value);
                break;
            case RIGHT:
                this.setX(oldX + value);
        }

        if (this.outOfBounds()) {
            this.setX(oldX);
            this.setY(oldY);
        }

    }

}