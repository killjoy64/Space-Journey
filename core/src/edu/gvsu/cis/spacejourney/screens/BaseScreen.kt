package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector3
import edu.gvsu.cis.spacejourney.SpaceJourney
import ktx.app.KtxScreen
import ktx.log.debug

/**
 * Convenient base class for all Screens where common functionality can be implemented.
*/
open class BaseScreen(val game: SpaceJourney, val screenName: String) : KtxScreen {

    /**
     * Implemented method that will dispose a screen once
     * the game is closed.
     */
    override fun dispose() {
        super.dispose()
        debug { "Screen Disposed: $screenName " }
    }

    /**
     * Overriden method that hides the screen when called.
     */
    override fun hide() {
        super.hide()
        debug { "Screen Hidden: $screenName " }
    }

    /**
     * Overriden method that pauses the screen when called.
     */
    override fun pause() {
        super.pause()
        debug { "Screen Paused: $screenName " }
    }

    /**
     * Overriden method that constantly updates and render the contents of the screen.
     * @param delta Time between the last and current render updates. This value can be
     * added to get a value of time that has passed.
     */
    override fun render(delta: Float) {
        super.render(delta)

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    /**
     * Overriden method that is called whenever the screen changes sizes.
     * @param width New width that the screen has resized to.
     * @param height New height that the screen has resized to.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        debug { "Screen Resized: $screenName to $width, $height" }
    }

    /**
     * Overriden method that is called whenever the screen was paused, and then
     * re-focused.
     */
    override fun resume() {
        super.resume()
        debug { "Screen Resumed: $screenName" }
    }

    /**
     * Overriden method that is called whenever the screen is initially shown. Put setup
     * logic here, however the resize() method may be called before the show() method.
     */
    override fun show() {
        super.show()

        debug { "Screen Shown: $screenName " }
    }

}