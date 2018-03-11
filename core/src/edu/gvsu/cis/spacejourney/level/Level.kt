package edu.gvsu.cis.spacejourney.level

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.utils.Disposable

abstract class Level : Disposable {

    private var engine: Engine? = null

    /*
  public void setMusic(Music music) {
    this.music = music;
  }

  public void setHud(Table hud) {
    this.hud = hud;
  }
  */

    /*public void setPlayer(PlayerSpaceship player) {
    this.player = player;
  }*/

    val music: Music? = null
    //private Table hud;
    //private PlayerSpaceship player;

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

    /*public Table getHud() {
    return hud;
  }*/

    /*public PlayerSpaceship getPlayer() {
    return player;
  }*/

}

