package edu.gvsu.cis.spacejourney.entities.projectiles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.util.ZIndex;

public class Laser extends Projectile {

    public Laser(Stage stage, World world, AssetManager assets) {
        super(stage, world, new TextureRegion(assets.get("laser.png", Texture.class)));
        setVelocity(0.0f, 200.0f / Constants.PX_PER_M);
        setSize(10.0f / Constants.PX_PER_M, 10.0f / Constants.PX_PER_M);
        setZIndex(ZIndex.PROJECTILES);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        return null;
    }

    @Override
    public void createBody() {

    }
}
