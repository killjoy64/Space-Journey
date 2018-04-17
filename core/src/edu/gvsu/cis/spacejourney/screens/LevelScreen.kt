package edu.gvsu.cis.spacejourney.screens

import edu.gvsu.cis.spacejourney.managers.MusicManager

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
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
import com.bitfire.postprocessing.PostProcessor
import com.bitfire.utils.ShaderLoader
import com.badlogic.gdx.Application.ApplicationType
import edu.gvsu.cis.spacejourney.component.Health
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import ktx.log.debug

/**
 * Where the magic happens. This is the main class for each
 * level inside of the game.
 */
class LevelScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {

    private var gameData: GameDataManager? = null
    private var level: Level? = null

    var engine = Engine()

    var renderingSystem: RenderingSystem? = null

    var postProcessor: PostProcessor? = null

    private val isDesktop = Gdx.app.type == ApplicationType.Desktop

    private var music: MusicManager? = null

    /**
     * Overriden method from BaseScreen that initializes a viewport, table labels, and
     * data placeholders.
     */
    override fun show() {
        super.show()

        ShaderLoader.BasePath = "data/shaders/"
        postProcessor = PostProcessor(false, false, isDesktop)

        renderingSystem = RenderingSystem()

        engine.addSystem(VelocitySystem())
        engine.addSystem(PlayerControllerSystem())
        engine.addSystem(renderingSystem)
        engine.addSystem(CollisionSystem())
        val playerTexture = SpaceJourney.assetManager.get("player_spaceship.png", Texture::class.java)

        engine.add {
          entity {
            with<Player> {
              movespeed = 300.0f
            }
            with<BoxCollider> {
                width = playerTexture.width
                height = playerTexture.height
                offset = Vector2(-24f, -24f)
            }
            with<Health> {
                value = 4
                maxValue = 4
            }
            with<Transform> {
              position = Vector2(Gdx.graphics.width.toFloat() / 2.0f, 45.0f)
            }
            with<StaticSprite> {
              scale = 2.0f
              zindex = ZIndex.PLAYER
              texture = playerTexture
            }
          }
        }

        gameData = GameDataManager.getInstance()
        gameData?.reset()

        debug { "Level: ${gameData?.levelNumber}" }
        level = Levels.getFromId(gameData?.levelNumber!!)
        level?.init(engine)
        level?.initEffects(postProcessor!!)

        MusicManager.getInstance().music = level?.music
    }

    /**
     * Overriden method that is called whenever the screen changes sizes.
     * @param width New width that the screen has resized to.
     * @param height New height that the screen has resized to.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        renderingSystem?.resize(width, height)
    }

    /**
     * Overriden method that periodically updates the screen.
     */
    override fun render(delta: Float) {
        super.render(delta)

        postProcessor?.capture()

        engine.update(delta)
        level?.update(delta)

        postProcessor?.render()

        // #TODO Replace the following code, it's a bit hacky, spriteBatch should be private.
        // Maybe add .drawGUI(Table) to renderingSystem
        renderingSystem?.spriteBatch?.begin()
        level?.hud?.setPosition(0.0f, Gdx.graphics.height.toFloat())
        level?.hud?.act(delta)
        level?.hud?.draw(renderingSystem?.spriteBatch, 1.0f)
        renderingSystem?.spriteBatch?.end()

        val players = engine.getEntitiesFor(Family.all(Player::class.java).get())

        if (level?.complete!!) {
            this.level?.dispose()
            this.game.setScreen<LevelEndScreen>()
        } else if (players.size() <= 0) {
            this.level?.dispose()
            this.game.setScreen<LevelSelectScreen>()
        }
    }

    /**
     * Overriden method that disposes of the view stage, and display font.
     */
    override fun dispose() {
        postProcessor?.dispose()
        level?.dispose()
    }

    /**
     * Function that hides the screen, and stops the current music.
     */
    override fun hide() {
        super.hide()
        MusicManager.getInstance().stop()
    }
}