package edu.gvsu.cis.spacejourney.level;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;

/**
 * Abstract class that defines a layout for each level in the game.
 */
public abstract class Level implements Disposable {

  private Stage stage;
  private World world;

  private Music music;
  private Table hud;
  private PlayerSpaceship player;

  /**
   * Default constructor that initializes the
   * level and world
   * @param stage for the current stage of the game.
   * @param world for the current world of the game.
   */
  public void init(Stage stage, World world) {
    this.stage = stage;
    this.world = world;
  }

  /**
   * Abstract method that is called every update cycle
   * inside of the render() method in the {@link edu.gvsu.cis.spacejourney.screens.LevelScreen}.
   * @param delta
   */
  public abstract void update(float delta);

  /**
   * Set the designated music for the level.
   * @param music to be set for the level.
   */
  public void setMusic(Music music) {
    this.music = music;
  }

  /**
   * Set the table to be used as a heads up display.
   * @param hud a table class that displays information.
   */
  public void setHud(Table hud) {
    this.hud = hud;
  }

  /**
   * Sets the designated player object.
   * @param player a player object.
   */
  public void setPlayer(PlayerSpaceship player) {
    this.player = player;
  }

  /**
   * Gets the music designated for the level.
   * @return a {@link com.badlogic.gdx.audio.Music} object.
   */
  public Music getMusic() {
    return music;
  }

  /**
   * Gets the heads up display table.
   * @return a {@link com.badlogic.gdx.scenes.scene2d.ui.Table} object.
   */
  public Table getHud() {
    return hud;
  }

  /**
   * Gets the player in the level.
   * @return a {@link edu.gvsu.cis.spacejourney.entity.PlayerSpaceship} object.
   */
  public PlayerSpaceship getPlayer() {
    return player;
  }

  /**
   * Gets the current world the level is in.
   * @return a {@link com.badlogic.gdx.physics.box2d.World} object.
   */
  public World getWorld() {
    return world;
  }

  /**
   * Gets the current stage the level is in.
   * @return a {@link com.badlogic.gdx.scenes.scene2d.Stage} object.
   */
  public Stage getStage() {
    return stage;
  }
}

