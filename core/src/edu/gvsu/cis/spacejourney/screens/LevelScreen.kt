package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FillViewport
import edu.gvsu.cis.spacejourney.util.ParallaxBackground
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity
import com.badlogic.gdx.utils.viewport.ScreenViewport
import edu.gvsu.cis.spacejourney.input.PlayerInputListener
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager
import edu.gvsu.cis.spacejourney.util.ZIndex

/**
 * Where the magic happens
 */
class LevelScreen(game : SpaceJourney) : BaseScreen(game, "LevelScreen") {

    //private var camera: OrthographicCamera? = null
    private var viewport: FillViewport? = null

    private var stage : Stage? = null

    private var background : ParallaxBackground? = null
    private var spaceship: SpaceshipEntity? = null

    private var projManager: ActiveProjectileManager? = null

    override fun show() {
        super.show()

        viewport = FillViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        //viewport?.update()
        //viewport?.unitsPerPixel = 1.0f

        stage = Stage(viewport)

        spaceship = SpaceshipEntity(stage, this.game.assets)

        spaceship?.setSize(32.0f * 2.0f, 32.0f * 2.0f)

        stage?.addActor(spaceship)

        background = ParallaxBackground(this.game.assets)
        background?.zIndex = ZIndex.BACKGROUND
        stage?.addActor(background)

        projManager = ActiveProjectileManager.getInstance()
        projManager?.setStage(stage)
        projManager?.setAssetManager(this.game.assets)
        projManager?.init()

        Gdx.input.inputProcessor = PlayerInputListener(spaceship)

    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        viewport?.update(width, height, true)

    }

    override fun render(delta: Float) {
        super.render(delta)

        viewport?.apply()

        projManager?.poll()

        batch?.projectionMatrix = viewport?.camera?.combined

        stage?.act()
        stage?.draw()
    }

    override fun dispose() {
        batch?.dispose()
        spaceship?.dispose()
        stage?.dispose()
    }

}