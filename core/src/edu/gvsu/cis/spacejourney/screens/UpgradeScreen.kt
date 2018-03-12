package edu.gvsu.cis.spacejourney.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
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
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.ashley.add
import ktx.ashley.entity

/**
 * Placeholder class for Upgrading the Player spaceship
 */
class UpgradeScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {

    override fun show() {
        super.show()

    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

    }

    override fun render(delta: Float) {
        super.render(delta)

    }

    override fun dispose() {

    }

    override fun hide() {
        super.hide()
    }

}