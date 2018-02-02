package edu.gvsu.cis.spacejourney.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.util.ZIndex;

public class SpaceshipEntity extends Entity {

    public SpaceshipEntity(Stage stage, World world, AssetManager assets) {
        super(stage, world, new TextureRegion(assets.get("player_spaceship_white.png", Texture.class)));
        this.getTextureRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        this.setZIndex(ZIndex.PLAYER);
    }

    @Override
    public TextureRegion getFrame(float delta) {
        // This returns null because there are no available frames!
        // The underlying Entity class handles this properly.
        return null;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // TODO - This needs to be fleshed out a little more.
        if (this.outOfBoundsX()) {
            this.setVelocityX(0.0f);
        }

        if (this.outOfBoundsY()) {
            this.setVelocityY(0.0f);
        }

    }

    @Override
    public void createBody() {
        setBounds(0, 0, getWidth() / Constants.PX_PER_M, getHeight() / Constants.PX_PER_M);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());

        Body body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius((getWidth() / 2) / Constants.PX_PER_M);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);

        circle.dispose();
    }
}