package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.physics.box2d.BodyDef
import edu.gvsu.cis.spacejourney.component.Box2D
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.Velocity
import edu.gvsu.cis.spacejourney.util.Mappers
import edu.gvsu.cis.spacejourney.util.toMeters
import edu.gvsu.cis.spacejourney.util.toPixels
import ktx.ashley.get
import ktx.ashley.has
import ktx.box2d.body
import ktx.box2d.createWorld
import ktx.math.plus

class VelocitySystem : EntitySystem() {

    // All related entities used by this system
    private var entities: ImmutableArray<Entity>? = null

    init {
        priority = 1
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
        }
    }
}

