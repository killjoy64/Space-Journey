package edu.gvsu.cis.spacejourney.level.choreography

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.*
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.ashley.add
import ktx.ashley.entity
import ktx.collections.GdxArray
import ktx.collections.isNotEmpty


class EnemySpawnEvent : ChoreographEvent() {

    val position: Vector2? = null

    override fun onEvent(engine: Engine) {

        //debug { "Enemy added" }

        val randomPosition = Vector2((Math.random().toFloat() * Gdx.graphics.width.toFloat() - 50.0f) + 50.0f, Gdx.graphics.height.toFloat())

        val enemyTexture = SpaceJourney.assetManager.get("enemy_spaceship.png", Texture::class.java)

        engine.add {
            entity {
                with<Enemy> {

                }
                with<Health> {
                    value = 3
                    maxValue = 3
                }
                with<BoxCollider> {
                    width = enemyTexture.width - 16
                    height = enemyTexture.height - 16
                    offset = Vector2(8f, 8f)
                }
                with<Transform> {
                    position = randomPosition
                    rotation = 180.0f
                }
                with<Velocity> {
                    value = Vector2(0.0f, -1.5f)
                }
                with<StaticSprite> {
                    zindex = ZIndex.ENEMY
                    texture = enemyTexture
                }
            }
        }

    }

}

/**
 * Small abstract helper class that defines a
 * way to call events.
 */
abstract class ChoreographEvent {

    /**
    * Abstract method that is called whenever the Choreographer
    * schedules an event, and calls it.
    */
    abstract fun onEvent(engine : Engine)
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
class LevelChoreographer(val engine: Engine) {

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
                lastEvent?.event?.onEvent(engine)
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