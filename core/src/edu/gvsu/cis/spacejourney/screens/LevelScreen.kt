package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import edu.gvsu.cis.spacejourney.util.ParallaxBackground
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity
import com.badlogic.gdx.utils.viewport.ScreenViewport
import edu.gvsu.cis.spacejourney.input.PlayerInputListener
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager

/**
 * Where the magic happens
 */
class LevelScreen(game : SpaceJourney) : BaseScreen(game, "LevelScreen") {

    //private var camera: OrthographicCamera? = null
    private var viewport: ScreenViewport? = null

    private var stage : Stage? = null

    private var background : ParallaxBackground? = null
    private var spaceship: SpaceshipEntity? = null

    private var projManager: ActiveProjectileManager? = null

    override fun show() {
        super.show()

        viewport = ScreenViewport()
        viewport?.update(Gdx.graphics.width, Gdx.graphics.height)
        viewport?.unitsPerPixel = Gdx.graphics.density

        stage = Stage(viewport)

        spaceship = SpaceshipEntity(stage)

        spaceship?.setSize(50.0f, 50.0f)

        stage?.addActor(spaceship)

        background = ParallaxBackground()
        stage?.addActor(background)

        projManager = ActiveProjectileManager.getInstance()
        projManager?.setStage(stage)
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