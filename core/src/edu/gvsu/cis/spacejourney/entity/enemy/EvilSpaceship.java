package edu.gvsu.cis.spacejourney.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class EvilSpaceship extends Enemy {

    public EvilSpaceship(Stage stage) {
        super(stage, new TextureRegion(SpaceJourney.Companion.getAssetManager().get("spaceship3.png", Texture.class)));
        getTexture().flip(false, true);
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
        fixtureDef.isSensor = true;

        body.setUserData(this);
        body.createFixture(fixtureDef);

        setBody(body);

        square.dispose();
    }

    @Override
    public void dispose() {

    }
}