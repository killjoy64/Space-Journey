package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeType
import com.badlogic.gdx.math.Vector3
import edu.gvsu.cis.spacejourney.SpaceJourney
import ktx.app.KtxScreen
import ktx.app.use
import ktx.collections.GdxArray
import ktx.log.debug

/*
 Convenient base class for all Screens where common functionality can be implemented
 #TODO only override methods that need to be overriden
*/
open class BaseScreen(val game: SpaceJourney, val screenName: String) : ControllerListener, KtxScreen {

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

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
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
        Controllers.addListener(this)

        debug { "Screen Shown: $screenName " }
    }

    override fun connected(controller: Controller?) {
        debug { "Controller ${controller?.name} connected" }
    }

    override fun disconnected(controller: Controller?) {
        debug { "Controller ${controller?.name} disconnected" }
    }

    override fun buttonUp(controller: Controller?, buttonCode: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun ySliderMoved(controller: Controller?, sliderCode: Int, value: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun accelerometerMoved(controller: Controller?, accelerometerCode: Int, value: Vector3?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun axisMoved(controller: Controller?, axisCode: Int, value: Float): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun xSliderMoved(controller: Controller?, sliderCode: Int, value: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun povMoved(controller: Controller?, povCode: Int, value: PovDirection?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun buttonDown(controller: Controller?, buttonCode: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}