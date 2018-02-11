package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage

import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entity.Graveyard
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship
import edu.gvsu.cis.spacejourney.entity.collectible.TestCollectible
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship
import edu.gvsu.cis.spacejourney.input.GameContactListener
import edu.gvsu.cis.spacejourney.input.PlayerInputListener
import edu.gvsu.cis.spacejourney.level.Level
import edu.gvsu.cis.spacejourney.level.Levels
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay
import edu.gvsu.cis.spacejourney.screens.backgrounds.ParallaxBackground
import edu.gvsu.cis.spacejourney.screens.util.EnemySpawnEvent
import edu.gvsu.cis.spacejourney.screens.util.StageChoreographer
import edu.gvsu.cis.spacejourney.util.ZIndex

/**
 * Where the magic happens
 */
class LevelScreen(game: SpaceJourney) : BaseScreen(game, "LevelScreen") {

    private var debugRenderer: Box2DDebugRenderer? = null

    private var camera: OrthographicCamera? = null
    private var viewport: FitViewport? = null
    private var stage: Stage? = null

    private var overlayCam: OrthographicCamera? = null
    private var overlayViewport: FillViewport? = null
    private var overlayStage: Stage? = null

    private var world: World? = null
    private var contactListener: GameContactListener? = null

    private var overlayTable: DefaultOverlay? = null

    private var projManager: ActiveProjectileManager? = null

    private var gameData: GameDataManager? = null
    private var level: Level? = null

    override fun show() {
        super.show()

        camera = OrthographicCamera()
        viewport = FitViewport(Constants.VIRTUAL_WIDTH / Constants.PX_PER_M,
                Constants.VIRTUAL_HEIGHT / Constants.PX_PER_M, camera)
        stage = Stage(viewport)

        overlayCam = OrthographicCamera()
        overlayViewport = FillViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, overlayCam)
        overlayStage = Stage(overlayViewport)

        world = World(Vector2(0.0f, 0.0f), true)
        contactListener = GameContactListener()

        world?.setContactListener(contactListener)

        debugRenderer = Box2DDebugRenderer()

        projManager = ActiveProjectileManager.getInstance()
        projManager?.setStage(stage)
        projManager?.setWorld(world)
        projManager?.init()

        overlayTable = DefaultOverlay()

        overlayStage?.addActor(overlayTable)

        val music: Music? = SpaceJourney.assetManager.get("Space Background Music.mp3")
        music?.volume = 0.3f
        music?.isLooping = true
        music?.play()

        gameData = GameDataManager.getInstance()
        level = Levels.getFromId(gameData?.levelNumber!!).level
        level?.init(stage, world)
    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        viewport?.update(width, height, true)
        overlayViewport?.update(width, height)

        camera?.update()
    }

    override fun render(delta: Float) {
        super.render(delta)

        batch?.begin()

        viewport?.apply()

        overlayTable?.poll()
        projManager?.poll()

        camera?.update()

        debugRenderer?.render(world, camera?.combined)

        batch?.projectionMatrix = camera?.combined

        stage?.act()
        stage?.draw()

        overlayStage?.act()
        overlayStage?.draw()

        level?.update(delta)

        batch?.end()

        world?.step(1.0f / 60.0f, 6, 2)

        getRidOfBodies()
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
        batch?.dispose()
        level?.dispose()
        stage?.dispose()
        overlayStage?.dispose()
    }

}