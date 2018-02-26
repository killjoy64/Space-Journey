package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import edu.gvsu.cis.spacejourney.SpaceJourney

/**
 * Class that carries simple debug info for
 * easy tracking during runtime.
 */
class DebugInfo : Actor() {

    private var enabled = true

    private var debugFont: BitmapFont? = null

    /**
     * Method that overrides Actor.draw() in order to draw
     * a few fonts and tables to the screen.
     */
    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        val spacing = 10f.toMeters()

        // #TODO Add Player Position
        //debugFont!!.draw(batch, "Hello World!", x.toMeters(), y.toMeters())
        debugFont!!.draw(batch, "Java Heap Memory ${Gdx.app.javaHeap}", x.toMeters(), (y + spacing).toMeters())
        debugFont!!.draw(batch, "Native Heap Memory ${Gdx.app.nativeHeap}", x.toMeters(), (y + spacing * 2).toMeters())
    }

    /**
     * Default constructor that is called whenever an instance of this class is created.
     * The constructor initializes the debug font and creates a Zindex for the GUI.
     */
    init {
        debugFont = SpaceJourney.assetManager.get("fonts/default.fnt")
        debugFont!!.data.scale(1.0f)

        this.zIndex = ZIndex.GUI
    }
}