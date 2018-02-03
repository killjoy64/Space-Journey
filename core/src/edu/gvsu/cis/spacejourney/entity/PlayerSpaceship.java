package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class PlayerSpaceship extends Entity {

    public PlayerSpaceship(Stage stage) {
        super(stage, new TextureRegion(SpaceJourney.Companion.getAssetManager().get("spaceship2.png", Texture.class)));
    }

    @Override
    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() + ((getWidth() / 2) / Constants.PX_PER_M),
                getY() + ((getHeight() / 2) / Constants.PX_PER_M));

        Body body = world.createBody(bodyDef);

        PolygonShape square = new PolygonShape();
        square.setAsBox((getWidth() / 2) / Constants.PX_PER_M, (getHeight() / 2) / Constants.PX_PER_M);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = square;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);

        setBody(body);

        square.dispose();
    }

    @Override
    public void dispose() {

    }
}
