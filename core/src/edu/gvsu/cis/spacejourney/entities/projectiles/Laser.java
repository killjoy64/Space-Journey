package edu.gvsu.cis.spacejourney.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Laser extends Projectile {

    public Laser(Stage stage) {
        super(stage, new TextureRegion(new Texture(Gdx.files.internal("laser.png"))));
        setVelocity(0.0f, 200.0f);

    }

    @Override
    public TextureRegion getFrame(float delta) {
        return null;
    }
}
