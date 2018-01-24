package edu.gvsu.cis.spacejourney

import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.collections.GdxArray

abstract class ChoreographEvent {

    // Returns the seconds from the start of the choreographer starting for the event to fire
    abstract fun getScheduledTime() : Long;

}

class StageChoreographer(val stage : Stage) {

    private var paused = false
    private var events = GdxArray<ChoreographEvent>()
    private var time : Long = 0

    fun pause(){
        this.paused = true
    }

    fun resume(){
        this.paused = false
    }

    fun update(){

        //time =

    }

    fun schedule(){

    }

}