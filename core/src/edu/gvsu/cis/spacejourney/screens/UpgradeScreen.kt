package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Table
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.util.JMP
import ktx.app.use
import ktx.scene2d.label
import ktx.scene2d.table
import ktx.style.label
import ktx.style.skin

/**
 * Placeholder class for Upgrading the Player spaceship
 */
class UpgradeScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {

    var ui: Table? = null

    var spriteBatch: SpriteBatch? = null

    /**
     * Overriden method from BaseScreen that initializes a viewport, table labels, and
     * data placeholders.
     */
    override fun show() {
        super.show()

        val skin = skin {
            label {
                font = SpaceJourney.assetManager.get("fonts/default.fnt")
                fontColor = JMP.White
            }
        }

        ui = table {}
        ui?.skin = skin

        ui = table {
            pad(4f) // Setting table padding.
            label("Test.") {
                color = JMP.Red // Setting text's color.
                setWrap(true) // Setting label's text wrapping.
            }
        }

        spriteBatch = SpriteBatch()
    }

    /**
     * Overriden method that is called whenever the screen changes sizes.
     * @param width New width that the screen has resized to.
     * @param height New height that the screen has resized to.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    /**
     * Overriden method that periodically updates the screen.
     */
    override fun render(delta: Float) {
        super.render(delta)

        spriteBatch?.use {
            ui?.draw(spriteBatch, 1.0f)
        }
    }

    /**
     * Overriden method that disposes of the view stage, and display font.
     */
    override fun dispose() {
        spriteBatch?.dispose()
    }

    /**
     * Function that hides the screen, and stops the current music.
     */
    override fun hide() {
        super.hide()
    }
}