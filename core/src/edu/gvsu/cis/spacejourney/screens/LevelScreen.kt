package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Spaceship
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

    override fun show() {
        super.show()

        stage = Stage()
        img = Texture("badlogic.jpg")

        player = Spaceship(img!!)

        stage?.addActor(player)

        camera = OrthographicCamera()
        viewport = ExtendViewport(480f, 360f, camera)
    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        if (camera != null) {
            camera!!.viewportWidth = width.toFloat()
            camera!!.viewportHeight = height.toFloat()
            camera!!.update()
        }

        viewport?.update(width, height, true)
        batch?.projectionMatrix = camera?.combined

    }

    override fun render(delta: Float) {
        super.render(delta)

        val move_speed = 20.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player?.moveUp(move_speed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player?.moveDown(move_speed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            player?.moveLeft(move_speed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            player?.moveRight(move_speed)
        }

        stage?.act()
        stage?.draw()
    }

    override fun dispose() {
        batch?.dispose()
        img?.dispose()
        stage?.dispose()
    }

}