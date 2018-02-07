package edu.gvsu.cis.spacejourney.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class EvilSpaceship extends Enemy {

    public EvilSpaceship(Stage stage) {
        super(stage, new TextureRegion(SpaceJourney.Companion.getAssetManager().get("spaceship3.png", Texture.class)));
        getTextureRegion().flip(false, true);
        setMaxHitPoints(3);
    }

    @Override
    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                getX() + ((getWidth() / 2) / Constants.PX_PER_M),
                getY() + ((getHeight() / 2) / Constants.PX_PER_M)
        );


        CircleShape circle = new CircleShape();
        circle.setRadius(((getWidth() / 2) - 2.5f) / Constants.PX_PER_M);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;

        Body body = world.createBody(bodyDef);
        body.setUserData(this);
        body.createFixture(fixtureDef);

        setBody(body);
        setWorld(world);

        circle.dispose();
    }

    @Override
    public void dispose() {

    }
}
