package edu.gvsu.cis.spacejourney.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Class that handles all given input for the main menu screen.
 */
public class MainMenuInputListener implements InputProcessor {

  private int maxChoices;
  private int currentChoice;
  private boolean startGame;

  /**
   * Input listener for the main menu.
   * @param maxChoices number of choices to represent on the menu.
   */
  public MainMenuInputListener(int maxChoices) {
    this.maxChoices = maxChoices;
    this.currentChoice = 1;
    this.startGame = false;
  }

  @Override
  public boolean keyDown(int keycode) {

    if (keycode == Input.Keys.UP) {
      if (currentChoice < maxChoices) {
        currentChoice++;
      } else {
        currentChoice = 1;
      }
    }

    if (keycode == Input.Keys.DOWN) {
      if (currentChoice > 1) {
        currentChoice--;
      } else {
        currentChoice = maxChoices;
      }
    }

    if (keycode == Input.Keys.ENTER) {
      startGame = true;
    }

    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }

  public int getCurrentChoice() {
    return currentChoice;
  }

  public boolean gameCanStart() {
    return startGame;
  }

}
