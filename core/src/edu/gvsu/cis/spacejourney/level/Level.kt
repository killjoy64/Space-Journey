package edu.gvsu.cis.spacejourney.level

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable
import com.bitfire.postprocessing.PostProcessor
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay

/**
 * Abstract level class that defines a template to be used for all other level classes.
 */
abstract class Level : Disposable {

    protected var engine: Engine? = null

    var music: Music? = null
    var hud: DefaultOverlay? = null
    var complete: Boolean? = false

    /**
     * Abstracted function that only initializes the engine object.
     */
    open fun init(engine: Engine) {
        this.engine = engine
    }

    /**
     * Abstracted function that defines level-specific post processing effects.
     */
    abstract fun initEffects(postProcessor: PostProcessor)

    /**
     * Overriden method that periodically updates the screen.
     */
    open fun update(delta: Float) {

        val pixel_perfect = if (Gdx.graphics.width == 1920 && Gdx.graphics.height == 1080) "Yes" else "No"

        Gdx.app.graphics.setTitle("Space Journey - " +
                "FPS: ${Gdx.app.graphics.framesPerSecond}, " +
                "Java Heap: ${Gdx.app.javaHeap / 1000000}MB, " +
                "Native Heap: ${Gdx.app.nativeHeap / 1000000}MB, " +
                "${engine?.entities?.size()} Entities, " +
                "Pixel Perfect: $pixel_perfect")
    }
}
