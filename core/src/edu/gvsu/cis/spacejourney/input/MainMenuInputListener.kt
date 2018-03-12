package edu.gvsu.cis.spacejourney.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor

/**
 * Class that handles all given input for the main menu screen.
 */
/**
 * Input listener for the main menu.
 * @param maxChoices number of choices to represent on the menu.
 */
class MainMenuInputListener(private val maxChoices: Int) : InputProcessor {

    var currentChoice: Int = 0
    private var startGame: Boolean = false

    private fun decrementChoice(){
        currentChoice--
        if (currentChoice < 0){
            currentChoice = 0
        }
    }

    private fun incrementChoice(){
        currentChoice++
        if (currentChoice > maxChoices){
            currentChoice = maxChoices
        }
    }

    override fun keyDown(keycode: Int): Boolean {

        if (keycode == Input.Keys.DOWN) {
            incrementChoice()
        }

        if (keycode == Input.Keys.UP) {
            decrementChoice()
        }

        if (keycode == Input.Keys.ENTER) {
            startGame = true
        }

        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    fun gameCanStart(): Boolean {
        return startGame
    }

    init {
        this.currentChoice = 1
        this.startGame = false
    }

}
