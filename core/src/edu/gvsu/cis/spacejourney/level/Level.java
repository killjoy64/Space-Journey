package edu.gvsu.cis.spacejourney.level;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship;

public abstract class Level implements Disposable {

  private Stage stage;
  private World world;

  private Music music;
  private Table hud;
  private PlayerSpaceship player;

  public void init(Stage stage, World world) {
    this.stage = stage;
    this.world = world;
  }

  public abstract void update(float delta);

  public void setMusic(Music music) {
    this.music = music;
  }

  public void setHud(Table hud) {
    this.hud = hud;
  }

  public void setPlayer(PlayerSpaceship player) {
    this.player = player;
  }

  public Music getMusic() {
    return music;
  }

  public Table getHud() {
    return hud;
  }

  public PlayerSpaceship getPlayer() {
    return player;
  }

  public World getWorld() {
    return world;
  }

  public Stage getStage() {
    return stage;
  }
}

