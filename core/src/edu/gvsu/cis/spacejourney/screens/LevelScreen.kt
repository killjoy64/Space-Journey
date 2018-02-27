package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage

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
import edu.gvsu.cis.spacejourney.util.toMeters

/**
 * Where the magic happens. This is the main class for each
 * level inside of the game.
 */
class LevelScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {

    /**
     * Debug variable used to see which Box2D bodies are being drawn
     * to the screen.
     */
    private var debugRenderer: Box2DDebugRenderer? = null

    /**
     * Variables that designate what is being drawn, and what is physics
     * are happening.
     */
    private var stage: Stage? = null
    private var overlayStage: Stage? = null
    private var world: World? = null
    private var contactListener: GameContactListener? = null

    private var projManager: ActiveProjectileManager? = null

    private var gameData: GameDataManager? = null
    private var level: Level? = null

    /**
     * Method that creates a screen, initializes stages, worlds, and initial level logic.
     */
    override fun show() {
        super.show()

        val viewport = FitViewport(Constants.getVirtualWidth().toMeters(), Constants.getVirtualHeight().toMeters())
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

        if (level?.music != null) {
            MusicManager.getInstance().music = level?.music
        }

        //val info = DebugInfo()
        //info.setPosition(1f, 1f)
        //overlayStage?.addActor(info)

        if (level?.hud != null) {
            overlayStage?.addActor(level?.hud)
        }
    }

    /**
     * Overriden method that is called whenever the screen changes sizes.
     * @param width New width that the screen has resized to.
     * @param height New height that the screen has resized to.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        stage?.viewport?.update(width, height, true)
        overlayStage?.viewport?.update(width, height, true)
    }

    /**
     * Constantly called render method.
     * @param delta Time between last and current render() call.
     */
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
//        debugRenderer?.render(world, stage?.viewport?.camera?.combined)
        stage?.draw()

        // Draw the UI
        overlayStage?.viewport?.apply() // This should work but it breaks the UI!?
        overlayStage?.draw()
    }

    /**
     * Private helper function that destroys all bodies and removes all
     * actors from the screen.
     */
    private fun getRidOfBodies() {
        for (body: Body in Graveyard.BODIES) {
            world?.destroyBody(body)
        }
        Graveyard.BODIES.clear()

        for (actor: Actor in Graveyard.ACTORS) {
            actor.remove()
        }
        Graveyard.ACTORS.clear()
    }

    /**
     * Function that disposes of any remain memory from graphics, stages,
     * or additional level logic.
     */
    override fun dispose() {
        getRidOfBodies()
        level?.dispose()
        overlayStage?.dispose()
        stage?.dispose()
    }

    /**
     * Fucntion that hides the screen, and stops the current music.
     */
    override fun hide() {
        super.hide()
        MusicManager.getInstance().stop()
    }
}