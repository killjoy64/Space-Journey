package edu.gvsu.cis.spacejourney.screens.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.managers.GameDataManager;

/**
 * Created by Kyle Flynn on 2/3/2018.
 */
public class DefaultOverlay extends Table {

  private Image[] lives;
  private GameDataManager gameData;

  private int livesDisplayed;

  public DefaultOverlay() {
    setFillParent(true);

    this.lives = new Image[GameDataManager.MAX_LIVES];
    this.gameData = GameDataManager.getInstance();
    this.livesDisplayed = 0;

    for (int i = 0; i < this.lives.length; i++) {
      lives[i] = new Image(
          SpaceJourney.Companion.getAssetManager().get("spaceship2.png", Texture.class));
      lives[i].setScale(0.75f);
      top().left().padTop(-15.0f).add(lives[i]);
      livesDisplayed++;
    }
  }

  public void poll() {
    if (livesDisplayed > gameData.getLives() && livesDisplayed > 0) {
      removeActor(lives[gameData.getLives()]);
      livesDisplayed--;
    } else if (livesDisplayed < gameData.getLives()) {
      top().left().add(lives[gameData.getLives()]);
      livesDisplayed++;
    }
  }

}
