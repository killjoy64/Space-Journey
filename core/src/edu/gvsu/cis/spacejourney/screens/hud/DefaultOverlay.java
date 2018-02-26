package edu.gvsu.cis.spacejourney.screens.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import edu.gvsu.cis.spacejourney.SpaceJourney;
import edu.gvsu.cis.spacejourney.Strings;
import edu.gvsu.cis.spacejourney.managers.GameDataManager;

/**
 * Default head-up display (HUD) for the game play screen.
 */
public class DefaultOverlay extends Table {

  private Image[] lives;
  private GameDataManager gameData;

  private int livesDisplayed;

  private BitmapFont font;

  private Label scoreLabel;

  /**
   * Default constructor that initially maxes
   * out space on the screen, which is then updated by
   * the actual {@link edu.gvsu.cis.spacejourney.managers.GameDataManager} class.
   */
  public DefaultOverlay() {
    this.lives = new Image[GameDataManager.MAX_LIVES];
    this.gameData = GameDataManager.getInstance();
    this.livesDisplayed = 0;

    font = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
    font.getData().scale(0.15f);

    this.scoreLabel = new Label(
        String.format(Strings.HUD_SCORE, gameData.getScore()),
        new Label.LabelStyle(font, Color.WHITE));

    this.setFillParent(true);

    for (int i = 0; i < this.lives.length; i++) {
      lives[i] = new Image(
          SpaceJourney.Companion.getAssetManager().get("spaceship2.png", Texture.class));
      top().left().padLeft(16f).padTop(16.0f).add(lives[i]).size(64f, 64f).padRight(16.0f);

      livesDisplayed++;
    }

    add(scoreLabel).align(Align.right).padTop(10f).padRight(16f).expandX();
  }

  /**
   * Polls for updates that need to be done to the HUD by
   * the {@link edu.gvsu.cis.spacejourney.managers.GameDataManager} class.
   */
  public void poll() {
    if (livesDisplayed > gameData.getLives() && livesDisplayed > 0) {
      removeActor(lives[gameData.getLives()]);
      livesDisplayed--;
    } else if (livesDisplayed < gameData.getLives()) {
      top().left().add(lives[gameData.getLives()]);
      livesDisplayed++;
    }

    scoreLabel.setText(String.format(Strings.HUD_SCORE, gameData.getScore()));
  }

}
