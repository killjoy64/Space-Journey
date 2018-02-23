package edu.gvsu.cis.spacejourney.level.choreography

import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.collections.GdxArray
import ktx.collections.isNotEmpty

abstract class ChoreographEvent {

    // Implement Me!
    abstract fun onEvent(stage: Stage, world: World)
}

data class ScheduledEvent(
        val scheduled_time: Float,
        val event: ChoreographEvent
)

class LevelChoreographer(val stage: Stage, val world: World) {

    private var paused = false

    // Sorted array, the first element should always be the next.
    private var events = GdxArray<ScheduledEvent>()
    private var time: Float = 0f

    private var lastEvent: ScheduledEvent? = null

    fun pause() {
        this.paused = true
    }

    fun resume() {
        this.paused = false
    }

    // Load pre-defined events from a file
    fun loadEventsFromFile() {
    }

    private fun popNextEvent(): ScheduledEvent? {
        return this.events.removeIndex(0)
    }

    private fun getNextEvent(): ScheduledEvent? {
        if (this.events.isNotEmpty()) {
            return this.events[0]
        }
        return null
    }

    fun getLastEvent(): ScheduledEvent? {
        return this.lastEvent
    }

    fun schedule(scheduled_time: Float, event: ChoreographEvent) {
        this.events.add(ScheduledEvent(scheduled_time, event))
        this.events.sortedBy { it.scheduled_time }
    }

    fun update(delta: Float) {
        time += delta

        if (getNextEvent() != null) {
            if (getNextEvent()!!.scheduled_time < time) {
                lastEvent = popNextEvent()
                lastEvent?.event?.onEvent(stage, world)
            }
        }
    }

    fun isEmpty(): Boolean {
        return this.events.size == 0
    }
}