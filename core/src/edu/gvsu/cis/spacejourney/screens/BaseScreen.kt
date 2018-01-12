package edu.gvsu.cis.spacejourney.screens

import edu.gvsu.cis.spacejourney.SpaceJourney
import ktx.app.KtxScreen
import ktx.log.debug

/*
 Convenient base class for all Screens where common functionality can be implemented
 #TODO only override methods that need to be overriden
*/
open class BaseScreen(val game: SpaceJourney, val screenName : String) : KtxScreen {

    override fun dispose() {
        debug { "Screen Disposed: " + screenName }
        super.dispose()
    }

    override fun hide() {
        debug { "Screen Hidden: " + screenName }
        super.hide()
    }

    override fun pause() {
        debug { "Screen Paused: " + screenName }
        super.pause()
    }

    override fun render(delta: Float) {
        super.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        debug { "Screen Resized: " + screenName }
        super.resize(width, height)
    }

    override fun resume() {
        debug { "Screen Resumed: " + screenName }
        super.resume()
    }

    override fun show() {
        debug { "Screen Shown: " + screenName }
        super.show()
    }

}