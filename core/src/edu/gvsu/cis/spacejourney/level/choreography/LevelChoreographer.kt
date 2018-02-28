package edu.gvsu.cis.spacejourney.level.choreography

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.Player
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.Velocity
import edu.gvsu.cis.spacejourney.component.colliders.CircleCollider
import edu.gvsu.cis.spacejourney.entity.Collidable
import edu.gvsu.cis.spacejourney.entity.enemy.Enemy
import edu.gvsu.cis.spacejourney.entity.enemy.EvilSpaceship
import edu.gvsu.cis.spacejourney.entity.enemy.OutOfBoundsListener
import edu.gvsu.cis.spacejourney.entity.movement.LinearMovement
import edu.gvsu.cis.spacejourney.util.ZIndex
import edu.gvsu.cis.spacejourney.util.toMeters
import ktx.ashley.add
import ktx.ashley.entity
import ktx.collections.GdxArray
import ktx.collections.isNotEmpty
import ktx.log.debug

class EnemySpawnEvent : ChoreographEvent() {

    val position: Vector2? = null

    override fun onEvent(engine: Engine) {

        debug { "Enemy added" }

        val randomPosition = Vector2(Math.random().toFloat() * Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        engine.add {
            entity {
                with<CircleCollider> {

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
                    texture = SpaceJourney.assetManager.get("spaceship3.png", Texture::class.java)
                }
            }
        }

    }

}

abstract class ChoreographEvent {

    // Implement Me!
    abstract fun onEvent(engine : Engine)
}

data class ScheduledEvent(
        val scheduled_time: Float,
        val event: ChoreographEvent
)

class LevelChoreographer(val engine: Engine) {

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
                popNextEvent()?.event?.onEvent(engine)
            }
        }
    }

}