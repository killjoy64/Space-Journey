package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.Velocity
import edu.gvsu.cis.spacejourney.util.Bounds
import edu.gvsu.cis.spacejourney.util.Mappers
import ktx.ashley.remove
import ktx.math.plus

/*
 * Velocity System, moves entities transform component positions in accordance with their velocity
 */
class VelocitySystem : EntitySystem() {

    // All related entities used by this system
    private var entities: ImmutableArray<Entity>? = null

    init {
        priority = SystemPriorities.VelocitySystem
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(Transform::class.java, Velocity::class.java).get())
    }

    override fun update(deltaTime: Float) {

        for (i in 0 until entities!!.size()) {
            val entity = entities!!.get(i)

            val transform = Mappers.transform.get(entity)
            val velocity = Mappers.velocity.get(entity)

            if (entity != null){
                transform.position += velocity.value
            }

            if (Bounds.isOutOfBounds(
                position = transform.position,
                size = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()),
                padding = -10.0f
            )){
                if (Mappers.projectile.get(entity) != null) {
                    engine.removeEntity(entity)
                }
                if (Mappers.enemy.get(entity) != null) {
                    engine.removeEntity(entity)
                }
            }
        }
    }
}

