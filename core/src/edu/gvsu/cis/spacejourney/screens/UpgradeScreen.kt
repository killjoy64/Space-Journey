package edu.gvsu.cis.spacejourney.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Table
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.Player
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.level.Level
import edu.gvsu.cis.spacejourney.level.Levels
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.system.CollisionSystem
import edu.gvsu.cis.spacejourney.system.PlayerControllerSystem
import edu.gvsu.cis.spacejourney.system.RenderingSystem
import edu.gvsu.cis.spacejourney.system.VelocitySystem
import edu.gvsu.cis.spacejourney.util.JMP
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.app.use
import ktx.ashley.add
import ktx.ashley.entity
import ktx.scene2d.label
import ktx.scene2d.table
import ktx.style.label
import ktx.style.skin

/**
 * Placeholder class for Upgrading the Player spaceship
 */
class UpgradeScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {

    var ui : Table? = null

    var spriteBatch : SpriteBatch? = null

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