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
    setFillParent(true);
    setDebug(true);

    this.lives = new Image[GameDataManager.MAX_LIVES];
    this.gameData = GameDataManager.getInstance();
    this.livesDisplayed = 0;

    font = new BitmapFont(Gdx.files.internal("fonts/default.fnt"));
    font.getData().scale(0.25f);

    this.scoreLabel = new Label(String.format(Strings.HUD_SCORE, gameData.getScore()), new Label.LabelStyle(font, Color.WHITE));

    System.out.println(this.lives.length + " spaces available");

    for (int i = 0; i < this.lives.length; i++) {
      lives[i] = new Image(
          SpaceJourney.Companion.getAssetManager().get("spaceship2.png", Texture.class));
      top().left().add(lives[i]).width(75.0f).height(75.0f).padRight(25.0f);
      System.out.println("life added");
      livesDisplayed++;
    }
    add(scoreLabel).padLeft(375.0f).padTop(-25.0f);
  }

  /**
   * Polls for updates that need to be done to the HUD by
   * the {@link edu.gvsu.cis.spacejourney.managers.GameDataManager} class.
   */
  public void poll() {
    if (livesDisplayed > gameData.getLives() && livesDisplayed > 0) {
      removeActor(lives[gameData.getLives()]);
      livesDisplayed--;
      System.out.println("life taken away");
    } else if (livesDisplayed < gameData.getLives()) {
      top().left().add(lives[gameData.getLives()]);
      livesDisplayed++;
      System.out.println("life added");
    }

    scoreLabel.setText(String.format(Strings.HUD_SCORE, gameData.getScore()));
  }

}
