package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.Array
import edu.gvsu.cis.spacejourney.util.ParallaxBackground
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entities.Direction
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity
import edu.gvsu.cis.spacejourney.entities.projectiles.Laser
import com.badlogic.gdx.utils.Pool
import edu.gvsu.cis.spacejourney.input.PlayerInputListener
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager


/**
 * Where the magic happens
 */
class LevelScreen(game : SpaceJourney) : BaseScreen(game, "LevelScreen") {

    private var camera: OrthographicCamera? = null
    private var viewport: ExtendViewport? = null

    private var stage : Stage? = null

    private var background : ParallaxBackground? = null
    private var spaceship: SpaceshipEntity? = null

    private var projManager: ActiveProjectileManager? = null

    override fun show() {
        super.show()

        camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        viewport = ExtendViewport(480f, 360f, camera)

        stage = Stage(viewport)

        spaceship = SpaceshipEntity(stage)

        spaceship?.setSize(50.0f, 50.0f)

        stage?.addActor(spaceship)

        background = ParallaxBackground()

        projManager = ActiveProjectileManager.getInstance()
        projManager?.setStage(stage)
        projManager?.init()

        Gdx.input.inputProcessor = PlayerInputListener(spaceship)

    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        viewport?.update(width, height, true)
        camera?.viewportWidth = width.toFloat()
        camera?.viewportHeight = height.toFloat()
        camera?.update()
    }

    override fun render(delta: Float) {
        super.render(delta)

        batch?.projectionMatrix = camera?.combined

        projManager?.poll()

        background?.scroll(0.1f * Gdx.graphics.deltaTime)
        background?.draw(batch!!)

        stage?.act()
        stage?.draw()
    }

    override fun dispose() {
        batch?.dispose()
        spaceship?.dispose()
        stage?.dispose()
    }

}