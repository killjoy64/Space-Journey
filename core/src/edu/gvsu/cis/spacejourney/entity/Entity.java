package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import edu.gvsu.cis.spacejourney.Constants;

/**
 * Abstract class that designs a template for creating any type of entity.
 * These entities are meant to be have created bodies in a Box2D world.
 */
public abstract class Entity extends Actor implements Collidable, Disposable {

  private Body body;
  private Stage stage;
  private TextureRegion textureRegion;
  private World world;

  /**
   * Creates a textureless entity, and sets
   * the state to be used for future reference.
   * @param stage current stage that updates actors.
   */
  public Entity(Stage stage) {
    this.stage = stage;
    this.textureRegion = null;
    this.setPosition(0.0f, 0.0f);
  }

  /**
   * Creates an entity using a desired texture to attach to a
   * Box2D body.
   * @param stage current stage that updates actors.
   * @param textureRegion texture to bind to the Box2D.
   */
  public Entity(Stage stage, TextureRegion textureRegion) {
    this.stage = stage;
    this.textureRegion = textureRegion;
    this.setPosition(0.0f, 0.0f);
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    if (body != null) {
      setPosition((body.getPosition().x * Constants.PX_PER_M) - (getWidth() / 2),
          (body.getPosition().y * Constants.PX_PER_M) - (getHeight() / 2));
    }

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

    if (this.textureRegion != null) {
      batch.draw(this.textureRegion,
          getX() / Constants.PX_PER_M, getY() / Constants.PX_PER_M,
          getWidth() / Constants.PX_PER_M, getHeight() / Constants.PX_PER_M);
    }
  }

  /**
   * Method that checks if the current actor is out of the screen's bounds.
   * @return <b>true</b> if the actor is out of bounds, or <b>false</b> if
   *         the actor is clearly visible on the screen.
   */
  public boolean outOfBounds() {
    int screenW = (int) (this.stage.getViewport().getWorldWidth() * Constants.PX_PER_M);
    int screenH = (int) (this.stage.getViewport().getWorldHeight() * Constants.PX_PER_M);
    int x = (int) getX();
    int y = (int) getY();
    int w = (int) getWidth();
    int h = (int) getHeight();

    return (x + w) > screenW || x < 0 || (y + h) > screenH || y < 0;
  }

  public void setBody(Body body) {
    this.body = body;
  }

  public Body getBody() {
    return body;
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World world) {
    this.world = world;
  }

  public TextureRegion getTextureRegion() {
    return textureRegion;
  }

  public void setTextureRegion(TextureRegion textureRegion) {
    this.textureRegion = textureRegion;
  }

}
