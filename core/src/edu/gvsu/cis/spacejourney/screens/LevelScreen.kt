package edu.gvsu.cis.spacejourney.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.*
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.level.Level
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.system.VelocitySystem
import edu.gvsu.cis.spacejourney.system.PlayerControllerSystem
import edu.gvsu.cis.spacejourney.system.RenderingSystem
import ktx.ashley.add
import ktx.ashley.entity

/**
 * Where the magic happens
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

    engine.add {
      entity {
        with<Player> {}
        with<Transform> {
          position = Vector2(10f, 10f)
        }
        with<StaticSprite> {
          texture = SpaceJourney.assetManager.get("spaceship2.png", Texture::class.java)
        }
      }
    }

    //val viewport = FitViewport(Constants.VIRTUAL_WIDTH.toMeters(), Constants.VIRTUAL_HEIGHT.toMeters())

    gameData = GameDataManager.getInstance()
    gameData?.reset()

    /*level = Levels.getFromId(gameData?.levelNumber!!).level
    //level?.init(stage, world)
    level?.music?.volume = 0.3f
    level?.music?.isLooping = true
    level?.music?.play()*/

  }

  // Be mindful about nullable-types, as resize is called before show
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

  }

  override fun dispose() {


  }

  override fun hide() {
    super.hide()
    dispose()
    level?.music?.stop()
  }

}