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

/**
 * Enemy class that defines an evil spaceship.
 */
public class EvilSpaceship extends Enemy {

  /**
   * Default constructor that creates an enemy spaceship.
   * @param stage current stage that the evil spaceship will live in.
   */
  public EvilSpaceship(Stage stage) {
    super(stage, new TextureRegion(
        SpaceJourney.Companion.getAssetManager().get("spaceship3.png", Texture.class)));
    getTextureRegion().flip(false, true);
    setMaxHitPoints(3);
  }

  /**
   * Creates a body that is readily available to populate a Box2D world.
   * @param world current Box2D world that is being
   */
  @Override
  public void createBody(World world) {

    setSize(64, 64);

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(
        getX() + ((getWidth()  / 2) / Constants.PX_PER_M),
        getY() + ((getHeight() / 2) / Constants.PX_PER_M)
    );

    CircleShape circle = new CircleShape();
    circle.setRadius((((getWidth() * 0.75f) / 2)) / Constants.PX_PER_M);

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

  /**
   * Method that disposes of any resources that implement
   * the Disposable interface.
   */
  @Override
  public void dispose() {

  }
}
