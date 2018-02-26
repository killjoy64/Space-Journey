package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.managers.GameDataManager;

/**
 * Class that creates an instance of the player, and assigns them a spaceship.
 */
public class PlayerSpaceship extends Entity {

  private static final float MOVE_SPEED = 3.0f;

  private EntityDirection direction;

  private AlphaAction disappearAction;
  private AlphaAction reappearAction;
  private SequenceAction damageAction;
  private RepeatAction damageActions;

  private Sound damageSound;

  private boolean dead;

  /**
   * Creates a player using the given stage.
   * @param stage the current stage that updates actors.
   */
  public PlayerSpaceship(Stage stage) {
    super(stage, new TextureRegion(
        SpaceJourney.Companion.getAssetManager().get("spaceship2.png", Texture.class)));

    direction = EntityDirection.IDLE;

    disappearAction = new AlphaAction();
    disappearAction.setAlpha(0.0f);
    disappearAction.setDuration(0.05f);

    reappearAction = new AlphaAction();
    reappearAction.setAlpha(1.0f);
    reappearAction.setDuration(0.05f);

    damageAction = new SequenceAction();
    damageAction.addAction(disappearAction);
    damageAction.addAction(reappearAction);

    damageActions = new RepeatAction();
    damageActions.setCount(4);
    damageActions.setAction(damageAction);

    damageSound = SpaceJourney.Companion.getAssetManager().get("take_damage.wav");

    dead = false;
  }

  /**
   * Damage the player when a collision happens. This is meant to harm the player.
   */
  public void takeDamage() {
    damageActions.restart();
    addAction(damageActions);
    damageSound.play(0.05f);
    if (GameDataManager.getInstance().getLives() <= 0) {
      dead = true;
      Graveyard.BODIES.add(getBody());
    }
  }

  /**
   * Method that moves the player using a given
   * {@link edu.gvsu.cis.spacejourney.entity.EntityDirection}.
   * @param direction the desired {@link edu.gvsu.cis.spacejourney.entity.EntityDirection}.
   */
  public void move(EntityDirection direction) {
    this.direction = direction;

    if (canMove(direction)) {
      getBody().setLinearVelocity(MOVE_SPEED * direction.getX(), MOVE_SPEED * direction.getY());
    }
  }

  /**
   * Self-explanatory method that stops all forces being applied to the player.
   */
  public void stopMoving() {
    getBody().setLinearVelocity(0.0f, 0.0f);
  }

  /**
   * Method that decides whether or not the player can move to that
   * given direction. This is to prevent the player from going offscreen.
   * @param direction the future {@link edu.gvsu.cis.spacejourney.entity.EntityDirection}.
   * @return <b>true</b> if the player can move that direction, and <b>false</b> if the
   *        player cannot.
   */
  public boolean canMove(EntityDirection direction) {
    int screenW = (int) (getStage().getViewport().getWorldWidth() * Constants.PX_PER_M);
    int screenH = (int) (getStage().getViewport().getWorldHeight() * Constants.PX_PER_M);
    int x = (int) getX();
    int y = (int) getY();
    int w = (int) getWidth();
    int h = (int) getHeight();
    switch (direction) {
      case UP:
        if (y + h <= screenH) {
          return true;
        }
        break;
      case DOWN:
        if (y > 0) {
          return true;
        }
        break;
      case LEFT:
        if (x > 0) {
          return true;
        }
        break;
      case RIGHT:
        if (x + w < screenW) {
          return true;
        }
        break;
      default:
        return false;
    }
    return false;
  }

  /**
   * Method that gets called constantly.
   * @param delta The time difference between the last and current render() method call.
   */
  @Override
  public void act(float delta) {
    super.act(delta);

    if (!canMove(direction)) {
      getBody().setLinearVelocity(0.0f, 0.0f);
    }

  }

  /**
   * Inherited method from the Collidable interface that
   * creates a body, and spawns it in the physics world.
   * @param world current Box2D world that is being
   */
  @Override
  public void createBody(World world) {

    setSize(96, 96);

    BodyBuilder bodyBuilder = new BodyBuilder();
    float x = getX() + ((getWidth() / 2) / Constants.PX_PER_M);
    float y = getY() + ((getHeight() / 2) / Constants.PX_PER_M);
    CircleShape circle = new CircleShape();
    circle.setRadius((((getWidth() * 0.625f) / 2)) / Constants.PX_PER_M);

    bodyBuilder.setBodyShape(circle);
    bodyBuilder.setBodyPosition(new Vector2(x, y));
    bodyBuilder.setUserData(this);

    setBody(bodyBuilder.build(world));
    setWorld(world);

    circle.dispose();
  }

  /**
   * Is the player dead? Find out by calling this method.
   * @return Dead, or not dead (boolean value).
   */
  public boolean isDead() {
    return this.dead;
  }

  /**
   * Simple dispose method that stops any current sounds being played, and
   * disposes of those sounds.
   */
  @Override
  public void dispose() {
    damageSound.stop();
    damageSound.dispose();
  }
}
