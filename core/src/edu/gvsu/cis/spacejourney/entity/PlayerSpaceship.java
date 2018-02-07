package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class PlayerSpaceship extends Entity {

  private final float moveSpeed = 2.0f;

  private EntityDirection direction;

  private AlphaAction disappearAction;
  private AlphaAction reappearAction;
  private SequenceAction damageAction;
  private RepeatAction damageActions;

  private Sound damageSound;

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
  }

  public void takeDamage() {
    damageActions.restart();
    addAction(damageActions);
    damageSound.play(0.05f);
  }

  public void move(EntityDirection direction) {
    this.direction = direction;

    if (canMove(direction)) {
      getBody().setLinearVelocity(moveSpeed * direction.getX(), moveSpeed * direction.getY());
    }
  }

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

  @Override
  public void act(float delta) {
    super.act(delta);

    if (!canMove(direction)) {
      getBody().setLinearVelocity(0.0f, 0.0f);
    }

  }

  @Override
  public void createBody(World world) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(getX() + ((getWidth() / 2) / Constants.PX_PER_M),
        getY() + ((getHeight() / 2) / Constants.PX_PER_M));

    CircleShape circle = new CircleShape();
    circle.setRadius(((getWidth() / 2) - 2.5f) / Constants.PX_PER_M);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.isSensor = true;
    fixtureDef.restitution = 0.0f;

    Body body = world.createBody(bodyDef);
    body.setUserData(this);
    body.createFixture(fixtureDef);

    setBody(body);
    setWorld(world);

    circle.dispose();
  }

  @Override
  public void dispose() {
    damageSound.dispose();
  }
}
