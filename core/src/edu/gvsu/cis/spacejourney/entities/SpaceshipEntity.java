package edu.gvsu.cis.spacejourney.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SpaceshipEntity extends Entity {

    public SpaceshipEntity(Stage stage) {
        super(stage, new TextureRegion(new Texture(Gdx.files.internal("spaceship.png"))));

        this.setX(5.0f);
        this.setY(5.0f);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        return null;
    }
}
