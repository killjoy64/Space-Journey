package edu.gvsu.cis.spacejourney.entities.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;

public class Laser extends Projectile {

    public Laser(Stage stage) {
        super(stage, new TextureRegion(assets.get("laser.png", Texture.class)));
        setVelocity(0.0f, 200.0f / Constants.PX_PER_M);
        setSize(10.0f / Constants.PX_PER_M, 10.0f / Constants.PX_PER_M);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        return null;
    }
}
