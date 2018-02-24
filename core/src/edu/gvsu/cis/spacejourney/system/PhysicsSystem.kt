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

class PhysicsSystem : EntitySystem(), EntityListener {

    // All related entities used by this system
    private var entities: ImmutableArray<Entity>? = null

    // Box2D World
    val world = createWorld()

    init {
        priority = 1
    }

    // Create Box2D objects accordingly
    override fun entityAdded(entity: Entity?) {
        if (entity != null){
            if (entity.has(Mappers.box2D) && entity.has(Mappers.transform) && entity.has(Mappers.boxCollider)){
                val boxCollider = entity.get(Mappers.boxCollider)!!
                val box2D = entity.get(Mappers.box2D)!!

                box2D.body = world.body {
                    box(boxCollider.size.toMeters(), boxCollider.size.toMeters()) {

                    }
                }

            }
            else if (entity.has(Mappers.box2D) && entity.has(Mappers.transform)  && entity.has(Mappers.circleCollider)){
                val circleCollider = entity.get(Mappers.circleCollider)!!
                val box2D = entity.get(Mappers.box2D)!!

                box2D.body = world.body {
                    circle(radius = circleCollider.radius.toMeters(), init = {})
                }
            }

            if (entity.has(Mappers.box2D) && entity.has(Mappers.transform)) {
                val transform = entity.get(Mappers.transform)!!
                val box2D = entity.get(Mappers.box2D)!!

                box2D.body?.setTransform(transform.position.toMeters(), transform.rotation.toMeters())
            }
        }
    }

    // Destroy Box2D objects accordingly
    override fun entityRemoved(entity: Entity?) {
        if (entity != null) {
            if (entity.has(Mappers.box2D)) {
                val box2D = entity.get(Mappers.box2D)!!
                world.destroyBody(box2D.body)
            }
        }
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(Box2D::class.java, Transform::class.java, Velocity::class.java).get())
    }

    override fun update(deltaTime: Float) {

        for (i in 0 until entities!!.size()) {
            val entity = entities!!.get(i)

            val transform = Mappers.transform.get(entity)
            val velocity = Mappers.velocity.get(entity)
            val box2DBody = Mappers.box2D.get(entity).body

            if (box2DBody != null){
                if (transform.position != box2DBody.position?.toPixels()){
                    box2DBody.setTransform(transform.position.toMeters(), transform.rotation)
                }

                if (transform.position != box2DBody.linearVelocity.toPixels()){
                    box2DBody.linearVelocity = velocity.value.toMeters()
                }
            }
        }

        world.step(1.0f / 60.0f, 6, 2)

        for (i in 0 until entities!!.size()) {
            val entity = entities!!.get(i)

            val transform = Mappers.transform.get(entity)
            val velocity = Mappers.velocity.get(entity)
            val box2DBody = Mappers.box2D.get(entity).body

            if (box2DBody != null){
                transform.position = box2DBody.position?.toPixels()!!
                velocity.value = box2DBody.linearVelocity?.toPixels()!!
            }
        }
    }
}

