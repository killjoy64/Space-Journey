package edu.gvsu.cis.spacejourney.screens

import edu.gvsu.cis.spacejourney.managers.MusicManager

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
 * Where the magic happens. This is the main class for each
 * level inside of the game.
 */
class LevelScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {


    private var gameData: GameDataManager? = null
    private var level: Level? = null

    var engine = Engine()

    var renderingSystem : RenderingSystem? = null

    override fun show() {
        super.show()

        renderingSystem = RenderingSystem()

        engine.addSystem(VelocitySystem())
        engine.addSystem(PlayerControllerSystem())
        engine.addSystem(renderingSystem)
        engine.addSystem(CollisionSystem())

        engine.add {
          entity {
            with<Player> {
              movespeed = 300.0f
            }
            with<Transform> {
              position = Vector2(Gdx.graphics.width.toFloat() / 2.0f, 45.0f)
            }
            with<StaticSprite> {
              scale = 2
              zindex = ZIndex.PLAYER
              texture = SpaceJourney.assetManager.get("player_spaceship.png", Texture::class.java)
            }
          }
        }

        gameData = GameDataManager.getInstance()
        gameData?.reset()

        level = Levels.getFromId(gameData?.levelNumber!!).level
        level?.init(engine)

        level?.music?.volume = 0.3f
        level?.music?.isLooping = true
        level?.music?.play()

    }

    /**
     * Overriden method that is called whenever the screen changes sizes.
     * @param width New width that the screen has resized to.
     * @param height New height that the screen has resized to.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        renderingSystem?.resize(width, height);
    }

    override fun render(delta: Float) {
        super.render(delta)

        // Switch level if needed
        /*if (level != null && level?.player!!.isDead) {
        game.setScreen<LevelSelectScreen>()
        }*/

        engine.update(delta)

        level?.update(delta)

        // #TODO Replace the following code, it's a bit hacky, spriteBatch should be private.
        // Maybe add .drawGUI(Table) to renderingSystem
        renderingSystem?.spriteBatch?.begin()
        level?.hud?.setPosition(0.0f, Gdx.graphics.height.toFloat())
        level?.hud?.act(delta)
        level?.hud?.draw(renderingSystem?.spriteBatch, 1.0f)
        renderingSystem?.spriteBatch?.end()

    }

    override fun dispose() {

    }

    /**
     * Fucntion that hides the screen, and stops the current music.
     */
    override fun hide() {
        super.hide()
        MusicManager.getInstance().stop()
    }


}