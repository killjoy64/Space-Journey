package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage

import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entity.Graveyard
import edu.gvsu.cis.spacejourney.input.GameContactListener
import edu.gvsu.cis.spacejourney.level.Level
import edu.gvsu.cis.spacejourney.level.Levels
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.managers.MusicManager
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay
import edu.gvsu.cis.spacejourney.util.DebugInfo
import edu.gvsu.cis.spacejourney.util.toMeters

/**
 * Where the magic happens
 */
class LevelScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {

  private var debugRenderer: Box2DDebugRenderer? = null

  private var stage: Stage? = null
  private var overlayStage: Stage? = null

  private var world: World? = null
  private var contactListener: GameContactListener? = null

  private var projManager: ActiveProjectileManager? = null

  private var gameData: GameDataManager? = null
  private var level: Level? = null

  override fun show() {
    super.show()

    val viewport = FitViewport(Constants.VIRTUAL_WIDTH.toMeters(), Constants.VIRTUAL_HEIGHT.toMeters())
    stage = Stage(viewport)

    val overlayViewport = ScreenViewport()
    overlayStage = Stage(overlayViewport)

    world = World(Vector2(0.0f, 0.0f), true)
    contactListener = GameContactListener()

    world?.setContactListener(contactListener)

    debugRenderer = Box2DDebugRenderer()

    projManager = ActiveProjectileManager.getInstance()
    projManager?.setStage(stage)
    projManager?.setWorld(world)
    projManager?.init()

    gameData = GameDataManager.getInstance()
    gameData?.reset()
    level = Levels.getFromId(gameData?.levelNumber!!).level
    level?.init(stage, world)
    MusicManager.getInstance().music = level?.music

    //val info = DebugInfo()
    //info.setPosition(1f, 1f)
    //overlayStage?.addActor(info)

    if (level?.hud != null) {
      overlayStage?.addActor(level?.hud)
    }
  }

  // Be mindful about nullable-types, as resize is called before show
  override fun resize(width: Int, height: Int) {
    super.resize(width, height)

    stage?.viewport?.update(width, height, true)
    overlayStage?.viewport?.update(width, height, true)
  }

  override fun render(delta: Float) {
    super.render(delta)

    // Switch level if needed
    if (level != null && level?.player!!.isDead) {
      game.setScreen<LevelSelectScreen>()
    }

    // Update all the things
    projManager?.poll()
    level?.update(delta)
    overlayStage?.act()
    stage?.act()
    world?.step(1.0f / 60.0f, 6, 2)
    getRidOfBodies()

    // Draw the game
    stage?.viewport?.apply()
//    debugRenderer?.render(world, stage?.viewport?.camera?.combined)
    stage?.draw()

    // Draw the UI
    overlayStage?.viewport?.apply() // This should work but it breaks the UI!?
    overlayStage?.draw()

  }

  private fun getRidOfBodies() {
    for (body: Body in Graveyard.bodies) {
      world?.destroyBody(body)
    }
    Graveyard.bodies.clear()

    for (actor: Actor in Graveyard.actors) {
      actor.remove()
    }
    Graveyard.actors.clear()
  }

  override fun dispose() {
    getRidOfBodies()
    level?.dispose()
    overlayStage?.dispose()
    stage?.dispose()
  }

  override fun hide() {
    super.hide()
//    dispose()
    MusicManager.getInstance().stop()
  }

}