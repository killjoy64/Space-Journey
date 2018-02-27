package edu.gvsu.cis.spacejourney.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.Entity;
import edu.gvsu.cis.spacejourney.entity.Graveyard;
import edu.gvsu.cis.spacejourney.entity.movement.MovementPattern;
import edu.gvsu.cis.spacejourney.managers.GameDataManager;

/**
 * Abstract class that defines a template for an enemy framework.
 */
public abstract class Enemy extends Entity {

  private int maxHitPoints;
  private int hitPoints;
  private int score;
  private ShapeRenderer shapeRenderer;
  private MovementPattern movementPattern;

  /**
   * Default constructor that creates a new enemy instance.
   * @param stage current stage that the entity will live in.
   * @param textureRegion texture to draw the entity with.
   */
  public Enemy(Stage stage, TextureRegion textureRegion) {
    super(stage, textureRegion);
    this.shapeRenderer = new ShapeRenderer();
    this.score = 10;
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    if (movementPattern != null && this.getBody() != null) {
      Vector2 direction = movementPattern.getMovement(this.getBody().getPosition());

      this.getBody().setLinearVelocity(direction.x * delta, direction.y * delta);
    }
  }

  /**
   * Method that sets the movement pattern for a given enemy. This could be a linear movement,
   * or a path-finding algorithm.
   * @param pattern A movement pattern.
   */
  public void setMovementPattern(MovementPattern pattern) {
    movementPattern = pattern;
  }

  /**
   * Method that gives the enemy damage.
   */
  public void takeDamage() {
    if (hitPoints > 1) {
      hitPoints--;
    } else {
      hitPoints = 0;
      Graveyard.BODIES.add(getBody());
      Graveyard.ACTORS.add(this);
      int prevScore = GameDataManager.getInstance().getScore();
      GameDataManager.getInstance().setScore(prevScore + 10);
    }
  }

  /**
   * Method that draws the enemy on the screen using the given sprite batch.
   * @param batch The sprite batch used in the game.
   * @param parentAlpha Alpha value of the entire object.
   */
  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (maxHitPoints > 2 && hitPoints > 0) {
      batch.end();

      float x = getX() / Constants.PX_PER_M;
      float y = (getY() + getHeight()) / Constants.PX_PER_M;
      float w = getWidth() / Constants.PX_PER_M;
      float hpW = (w / maxHitPoints) * hitPoints;
      float h = 2.5f / Constants.PX_PER_M;

      shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
      shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
      shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
      shapeRenderer.rect(x, y, w, h);
      shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1.0f);
      shapeRenderer.rect(x, y, hpW, h);
      shapeRenderer.end();

      batch.begin();
    }
  }

  /**
   * Method that returns the maximum hit points for the enemy.
   * @return maximum hit points as an integer.
   */
  public int getMaxHitPoints() {
    return maxHitPoints;
  }

  /**
   * Method that sets the current maximum hit points for an enemy. This also
   * resets their hit points to the new maximum.
   * @param maxHitPoints New maximum hit points to assign to an enemy.
   */
  public void setMaxHitPoints(int maxHitPoints) {
    this.maxHitPoints = maxHitPoints;
    this.hitPoints = maxHitPoints;
  }

  /**
   * Method that returns the current hit points of the enemy.
   * @return Hit points as an integer.
   */
  public int getHitPoints() {
    return hitPoints;
  }

  /**
   * Method that returns the score of an enemy.
   * @return Score as an integer.
   */
  public int getScore() {
    return score;
  }

  /**
   * Method that sets the current hit points of the enemy.
   * @param hitPoints new hit points to give to the enemy.
   */
  public void setHitPoints(int hitPoints) {
    if (hitPoints > maxHitPoints) {
      this.hitPoints = maxHitPoints;
    } else {
      this.hitPoints = hitPoints;
    }
  }

  /**
   * Method that sets the score that the player will receive once the enemy is destroyed.
   * @param score New score to assign to the enemy.
   */
  public void setScore(int score) {
    this.score = score;
  }
}
