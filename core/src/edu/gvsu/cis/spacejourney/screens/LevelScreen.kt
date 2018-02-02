package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage

import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity
import edu.gvsu.cis.spacejourney.input.PlayerInputListener
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager
import edu.gvsu.cis.spacejourney.util.ParallaxBackground
import edu.gvsu.cis.spacejourney.util.ZIndex

/**
 * Where the magic happens
 */
class LevelScreen(game : SpaceJourney) : BaseScreen(game, "LevelScreen") {

    private var camera: OrthographicCamera? = null
    private var viewport: FitViewport? = null
    private var stage : Stage? = null

    private var overlayCam: OrthographicCamera? = null
    private var overlayViewport: FillViewport? = null
    private var overlayStage: Stage? = null

    private var world: World? = null

    private var background : ParallaxBackground? = null
    private var spaceship: SpaceshipEntity? = null

    private var projManager: ActiveProjectileManager? = null
    private var inputListener: PlayerInputListener? = null

    override fun show() {
        super.show()

        camera = OrthographicCamera()
        viewport = FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT)
        stage = Stage(viewport)

        overlayCam = OrthographicCamera()
        overlayViewport = FillViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, overlayCam)
        overlayStage = Stage(overlayViewport)

        world = World(Vector2(0.0f, 0.0f), true)

        spaceship = SpaceshipEntity(stage, this.game.assets)

        spaceship?.setSize(50.0f, 50.0f)
        spaceship?.x = 5.0f / Constants.PX_PER_M
        spaceship?.y = 5.0f / Constants.PX_PER_M

        stage?.addActor(spaceship)

        background = ParallaxBackground(this.game.assets)
        background?.zIndex = ZIndex.BACKGROUND
        stage?.addActor(background)

        projManager = ActiveProjectileManager.getInstance()
        projManager?.setStage(stage)
        projManager?.setAssetManager(this.game.assets)
        projManager?.init()

        inputListener = PlayerInputListener(spaceship)
        Gdx.input.inputProcessor = inputListener

        val table : Table? = Table()
        table?.setFillParent(true)
//        table?.add(Label("Hello World!", Label.LabelStyle(
//                BitmapFont(Gdx.files.internal("fonts/default.fnt")),
//                Color.YELLOW
//        )))

        overlayStage?.addActor(table)
    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        viewport?.update(width, height, true)

        overlayViewport?.update(width, height);
    }

    override fun render(delta: Float) {
        super.render(delta)

        viewport?.apply()

        projManager?.poll()
        inputListener?.poll()

        camera?.update()
        batch?.projectionMatrix = camera?.combined

        stage?.act()
        stage?.draw()

        overlayStage?.act()
        overlayStage?.draw()

        world?.step(1.0f/60.0f, 6, 2)
    }

    override fun dispose() {
        batch?.dispose()
        spaceship?.dispose()
        stage?.dispose()
        overlayStage?.dispose()
    }

}