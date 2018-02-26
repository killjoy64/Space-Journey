package edu.gvsu.cis.spacejourney.level.choreography

import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.collections.GdxArray
import ktx.collections.isNotEmpty

/**
 * Small abstract helper class that defines a
 * way to call events.
 */
abstract class ChoreographEvent {

    /**
     * Abstract method that is called whenever the Choreographer
     * schedules an event, and calls it.
     */
    abstract fun onEvent(stage: Stage, world: World)
}

/**
 * Data class that holds a ChoreographEvent and
 * it's scheduled time.
 */
data class ScheduledEvent(
        val scheduled_time: Float,
        val event: ChoreographEvent
)

/**
 * Class that manages all of the level choreography for a
 * specific level. Events can be scheduled and called upon
 * through the use of a queue/stack ADT.
 */
class LevelChoreographer(val stage: Stage, val world: World) {

    private var paused = false

    // Sorted array, the first element should always be the next.
    private var events = GdxArray<ScheduledEvent>()
    private var time: Float = 0f

    private var lastEvent: ScheduledEvent? = null

    /**
     * Pauses the level choreographer.
     */
    fun pause() {
        this.paused = true
    }

    /**
     * Resumes the level choreographer.
     */
    fun resume() {
        this.paused = false
    }

    /**
     * TODO - Load events from file.
     */
    fun loadEventsFromFile() {
    }

    /**
     * Private method that pops the next event to queue it up for
     * execution.
     */
    private fun popNextEvent(): ScheduledEvent? {
        return this.events.removeIndex(0)
    }

    /**
     * Private method that retrieves the next event in the queue.
     */
    private fun getNextEvent(): ScheduledEvent? {
        if (this.events.isNotEmpty()) {
            return this.events[0]
        }
        return null
    }

    /**
     * Returns the last ScheduledEvent that was
     * executed from the choreographer.
     * @return a ScheduleEvent object.
     */
    fun getLastEvent(): ScheduledEvent? {
        return this.lastEvent
    }

    /**
     * Schedules an event at a designated time.
     * @param scheduled_time time to schedule the event.
     * @param event ChoreographEvent to call.
     */
    fun schedule(scheduled_time: Float, event: ChoreographEvent) {
        this.events.add(ScheduledEvent(scheduled_time, event))
        this.events.sortedBy { it.scheduled_time }
    }

    /**
     * Update cycle for the choreographer.
     * @param delta a float variable that is called every render() cycle.
     */
    fun update(delta: Float) {
        time += delta

        if (getNextEvent() != null) {
            if (getNextEvent()!!.scheduled_time < time) {
                lastEvent = popNextEvent()
                lastEvent?.event?.onEvent(stage, world)
            }
        }
    }

    /**
     * Method that determines if the event queue is empty.
     * @return a boolean that tells if the event queue is empty.
     */
    fun isEmpty(): Boolean {
        return this.events.size == 0
    }
}