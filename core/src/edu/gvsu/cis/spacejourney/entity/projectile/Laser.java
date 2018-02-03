package edu.gvsu.cis.spacejourney.entity.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.util.ZIndex;

/**
 * Created by Kyle Flynn on 2/3/2018.
 */
public class Laser extends Projectile {

    public Laser(Stage stage) {
        super(stage, new TextureRegion(SpaceJourney.Companion.getAssetManager().get("laser.png", Texture.class)));
        setZIndex(ZIndex.PROJECTILES);
    }

    @Override
    public void spawn(World world, float x, float y) {
        super.spawn(world, x, y);

        getBody().applyLinearImpulse(new Vector2(0.0f, 2.5f), getBody().getWorldCenter(), true);
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

        body.createFixture(fixtureDef);

        setBody(body);

        square.dispose();
    }

    @Override
    public void dispose() {

    }

}
