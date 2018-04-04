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

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun render(delta: Float) {
        super.render(delta)

        spriteBatch?.use {
            ui?.draw(spriteBatch, 1.0f)
        }
    }

    override fun dispose() {
        spriteBatch?.dispose()
    }

    override fun hide() {
        super.hide()
    }
}