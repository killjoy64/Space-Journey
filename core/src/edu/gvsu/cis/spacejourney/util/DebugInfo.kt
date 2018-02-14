package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import edu.gvsu.cis.spacejourney.SpaceJourney

class DebugInfo : Actor() {

    private var enabled = true

    private var debugFont : BitmapFont? = null

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        val spacing = 10f.toMeters()

        debugFont!!.draw(batch, "Hello World!", x.toMeters(), y.toMeters())
        debugFont!!.draw(batch, "Java Heap Memory ${Gdx.app.javaHeap}", x.toMeters(), (y + spacing).toMeters())
        debugFont!!.draw(batch, "Native Heap Memory ${Gdx.app.nativeHeap}", x.toMeters(), (y + spacing * 2).toMeters())
    }

    init {
        debugFont = SpaceJourney.assetManager.get("fonts/default.fnt")
        debugFont!!.data.scale(1.0f)

        this.zIndex = ZIndex.GUI
    }

}