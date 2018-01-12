package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import edu.gvsu.cis.spacejourney.SpaceJourney
import ktx.app.use

/**
 * Where the magic happens
 */
class LevelScreen(game : SpaceJourney) : BaseScreen(game, "LevelScreen") {

    private var batch: SpriteBatch? = null
    private var img: Texture? = null
    private var camera: OrthographicCamera? = null
    private var viewport: ExtendViewport? = null

    override fun show() {
        super.show()

        batch = SpriteBatch()
        img = Texture("badlogic.jpg")
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

        batch?.use {
            it.draw(img!!, 0f, 0f)
        }
    }

    override fun dispose() {
        batch?.dispose()
        img?.dispose()
    }

}