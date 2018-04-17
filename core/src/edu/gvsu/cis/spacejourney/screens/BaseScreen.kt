package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import edu.gvsu.cis.spacejourney.SpaceJourney
import ktx.app.KtxScreen
import ktx.log.debug

/**
* Convenient base class for all Screens where common functionality can be implemented
*/
open class BaseScreen(val game: SpaceJourney, val screenName: String) : KtxScreen {

    /**
     * Overriden function that disposes of the screen once it is finished.
     */
    override fun dispose() {
        super.dispose()
        debug { "Screen Disposed: $screenName " }
    }

    /**
     * Overriden method that hides the screen on demand.
     */
    override fun hide() {
        super.hide()
        debug { "Screen Hidden: $screenName " }
    }

    /**
     * Overriden method that pauses the screen on demand.
     */
    override fun pause() {
        super.pause()
        debug { "Screen Paused: $screenName " }
    }

    /**
     * Overriden method that constantly renders the screen.
     */
    override fun render(delta: Float) {
        super.render(delta)

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        SpaceJourney.tweenManager.update(delta)
    }

    /**
     * Overriden method that resizes the screen, and its viewports.
     * @param width the new width of the screen.
     * @param height the new height of the screen.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        debug { "Screen Resized: $screenName to $width, $height" }
    }

    /**
     * Overriden function that resumes the screen after it has been paused.
     */
    override fun resume() {
        super.resume()
        debug { "Screen Resumed: $screenName" }
    }

    /**
     * Overriden function that shows the screen. This might be called after the
     * resize method, so objects should be initialized carefully in show/resize.
     */
    override fun show() {
        super.show()
        //Controllers.addListener(this)

        debug { "Screen Shown: $screenName " }
    }
}