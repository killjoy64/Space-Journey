package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import edu.gvsu.cis.spacejourney.ParallaxBackground
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Spaceship
import edu.gvsu.cis.spacejourney.entities.Direction
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity
import ktx.actors.onKey
import ktx.app.use

/**
 * Where the magic happens
 */
class LevelScreen(game : SpaceJourney) : BaseScreen(game, "LevelScreen") {

    private var img: Texture? = null
    private var camera: OrthographicCamera? = null
    private var viewport: ExtendViewport? = null

    private var player : Spaceship? = null

    private var stage : Stage? = null

    private var background : ParallaxBackground? = null

    private var spaceship: SpaceshipEntity? = null

    override fun show() {
        super.show()

        stage = Stage()
        img = Texture("badlogic.jpg")

//        player = Spaceship(img!!)
        spaceship = SpaceshipEntity(stage)

//        stage?.addActor(player)
        stage?.addActor(spaceship)

        camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        viewport = ExtendViewport(480f, 360f, camera)

        background = ParallaxBackground()

    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)


        viewport?.update(width, height, true)
        //camera?.setToOrtho(false, width.toFloat(), height.toFloat())
        camera?.viewportWidth = width.toFloat()
        camera?.viewportHeight = height.toFloat()
        camera?.update()


    }

    override fun render(delta: Float) {
        super.render(delta)

        batch?.projectionMatrix = camera?.combined

        val moveSpeed = 10.0f

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            spaceship?.move(Direction.UP, moveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            spaceship?.move(Direction.DOWN, moveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            spaceship?.move(Direction.LEFT, moveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            spaceship?.move(Direction.RIGHT, moveSpeed)
        }

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