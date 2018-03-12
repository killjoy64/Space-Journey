package edu.gvsu.cis.spacejourney.level

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Disposable
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay

abstract class Level : Disposable {

    private var engine: Engine? = null

    var music: Music? = null
    var hud: DefaultOverlay? = null
    //private PlayerSpaceship player;

    /*fun setHud(hud : Table) {
        this.hud = hud;
    }*/

    open fun init(engine: Engine) {
        this.engine = engine
    }

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

