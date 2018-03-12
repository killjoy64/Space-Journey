package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import edu.gvsu.cis.spacejourney.SpaceJourney
import ktx.app.KtxScreen
import ktx.log.debug

/*
 Convenient base class for all Screens where common functionality can be implemented
 #TODO only override methods that need to be overriden
*/
open class BaseScreen(val game: SpaceJourney, val screenName: String) : KtxScreen {

    override fun dispose() {
        super.dispose()
        debug { "Screen Disposed: $screenName " }
    }

    override fun hide() {
        super.hide()
        debug { "Screen Hidden: $screenName " }
    }

    override fun pause() {
        super.pause()
        debug { "Screen Paused: $screenName " }
    }

    override fun render(delta: Float) {
        super.render(delta)

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit()
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        debug { "Screen Resized: $screenName to $width, $height" }
    }

    override fun resume() {
        super.resume()
        debug { "Screen Resumed: $screenName" }
    }

    override fun show() {
        super.show()
        //Controllers.addListener(this)

        debug { "Screen Shown: $screenName " }
    }

}