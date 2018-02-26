package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Builder class that builds the body
 * from the ground up, including
 * fixtures, sensors, etc.
 */
public class BodyBuilder {

  private BodyDef bodyDef;
  private FixtureDef fixtureDef;

  private Object userData;

  /**
   * Default constructor that initializes defaults for a
   * new Box2D body.
   */
  public BodyBuilder() {
    this.bodyDef = new BodyDef();
    this.fixtureDef = new FixtureDef();

    // By default, we will leave this.
    this.bodyDef.type = BodyType.DynamicBody;
    this.fixtureDef.isSensor = true;

    this.userData = null;
  }

  /**
   * Method that sets the body type of a body.
   * @param bodyType BodyType enum variable from box2d.
   * @return {@link edu.gvsu.cis.spacejourney.entity.BodyBuilder} instance.
   */
  public BodyBuilder setBodyType(BodyType bodyType) {
    this.bodyDef.type = bodyType;
    return this;
  }

  /**
   * Method that sets the body's position.
   * @param position Vector2 from Box2D. X and Y coordinate.
   * @return {@link edu.gvsu.cis.spacejourney.entity.BodyBuilder} instance.
   */
  public BodyBuilder setBodyPosition(Vector2 position) {
    this.bodyDef.position.set(position);
    return this;
  }

  /**
   * Method that sets the shape of the body. This really
   * gives the body... some shape...
   * @param shape Box2D physics shape.
   * @return {@link edu.gvsu.cis.spacejourney.entity.BodyBuilder} instance.
   */
  public BodyBuilder setBodyShape(Shape shape) {
    this.fixtureDef.shape = shape;
    return this;
  }

  /**
   * Method that sets whether or not a given body is a sensor and
   * can feel things around the world.
   * @param sensor <b>true</b> if the body is a sensor. <b>false</b> 
   * if it is not.
   * @return {@link edu.gvsu.cis.spacejourney.entity.BodyBuilder} instance.
   */
  public BodyBuilder setIsSensor(boolean sensor) {
    this.fixtureDef.isSensor = sensor;
    return this;
  }

  /**
   * Method that sets user data for the body.
   * @param o Any object that might be needed for collision detection.
   * @return {@link edu.gvsu.cis.spacejourney.entity.BodyBuilder} instance.
   */
  public BodyBuilder setUserData(Object o) {
    this.userData = o;
    return this;
  }

  /**
   * Method that finally pieces together the body as a tangible object.
   * @param world Current world that the body is created in.
   * @return Box2D body instance.
   */
  public Body build(World world) {
    Body body = world.createBody(this.bodyDef);
    body.createFixture(this.fixtureDef);
    body.setUserData(this.userData);
    return body;
  }

}
