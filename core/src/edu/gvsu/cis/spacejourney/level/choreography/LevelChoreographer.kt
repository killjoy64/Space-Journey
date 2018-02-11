package edu.gvsu.cis.spacejourney.level.choreography

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship
import edu.gvsu.cis.spacejourney.entity.movement.LinearMovement
import ktx.collections.GdxArray
import ktx.collections.isNotEmpty
import ktx.log.debug

class EnemySpawnEvent : ChoreographEvent() {

    val position: Vector2? = null

    override fun onEvent(stage: Stage, world: World) {

        debug { "Enemy added" }

        // Add quick enemy
        val enemy: EvilSpaceship? = EvilSpaceship(stage)

        enemy?.width = 35.0f
        enemy?.height = 35.0f
        enemy?.setPosition((Math.random().toFloat() * 5f), 5f)
        enemy?.setMovementPattern(LinearMovement(Vector2(0f, -25f)))
        enemy?.createBody(world)


        stage.addActor(enemy)
    }

}

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

    fun schedule(scheduled_time: Float, event: ChoreographEvent) {
        this.events.add(ScheduledEvent(scheduled_time, event))
        this.events.sortedBy { it.scheduled_time }
    }

    fun update(delta: Float) {
        time += delta

        if (getNextEvent() != null) {
            if (getNextEvent()!!.scheduled_time < time) {
                popNextEvent()?.event?.onEvent(stage, world)
            }
        }
    }

}