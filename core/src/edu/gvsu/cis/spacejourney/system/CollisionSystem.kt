package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.component.colliders.CircleCollider
import edu.gvsu.cis.spacejourney.util.Mappers
import ktx.ashley.has
import ktx.log.debug

private data class CollisionRectangle(
        val x : Float,
        val y : Float,
        val width : Float,
        val height : Float){

    companion object {
        fun fromComponents(transform: Transform, collider : BoxCollider) : CollisionRectangle {
            return CollisionRectangle(transform.position.x, transform.position.y, collider.width.toFloat(), collider.height.toFloat())
        }
    }

}

class CollisionSystem : EntitySystem() {

    // All related entities used by this system
    private var entities: ImmutableArray<Entity>? = null

    init {
        priority = SystemPriorities.CollisionSystem
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
                Family.all(Transform::class.java) // Object must have a transform
                .one(BoxCollider::class.java, CircleCollider::class.java) // Object has to at least have a Box Collider or a Circle Collider
                .get())
    }

    private fun rectangleCollision(rect1 : CollisionRectangle, rect2 : CollisionRectangle) : Boolean {
        return (rect1.x < rect2.x + rect2.width &&
                rect1.x + rect1.width > rect2.x &&
                rect1.y < rect2.y + rect2.height &&
                rect1.height + rect1.y > rect2.y)
    }

    fun onCollision(){
        /*val currentLives = gameData.getLives()

        val a = contact.getFixtureA()
        val b = contact.getFixtureB()

        if (entityB is EvilSpaceship && entityA is PlayerSpaceship) {
            val player = entityA as PlayerSpaceship
            gameData.setLives(currentLives - 1)
            player.takeDamage()
        }

        if (entityA is Collectible && entityB is PlayerSpaceship) {
            val c = entityA as Collectible
            c.collect()
        }

        if (entityA is Laser && entityB is Enemy) {
            val e = entityB as Enemy
            val l = entityA as Laser
            e.takeDamage()
            l.reset()
            Graveyard.bodies.add(a.getBody())
        }*/
    }

    fun collisionCheck(entityA: Entity, entityB :Entity){

        val boxA = Mappers.boxCollider.get(entityA)
        val boxB = Mappers.boxCollider.get(entityB)

        //val circleA = Mappers.circleCollider.get(entityA)
        //val circleB = Mappers.circleCollider.get(entityB)

        if (boxA != null && boxB != null){

            // #TODO Handle rotation

            val boxATransform = Mappers.transform.get(entityA)
            val boxBTransform = Mappers.transform.get(entityB)

            if (rectangleCollision(
                CollisionRectangle.fromComponents(boxATransform, boxA),
                CollisionRectangle.fromComponents(boxBTransform, boxB)
            )){
                //debug { "Collision Occured" }

                if (entityA.has(Mappers.enemy) && entityB.has(Mappers.projectile)){
                    engine.removeEntity(entityA)
                    engine.removeEntity(entityB)
                }


            }

        }

    }

    override fun update(deltaTime: Float) {

        // This very high level code simply iterates every entity
        // against every other entity while removing EVERY duplication
        // that could occur.
        //
        // More simply this generates the permutation of `entities` against `entities`
        entities?.asSequence()!!.map { entityA ->
            entities?.map { entityB ->
                collisionCheck(entityA, entityB)
                collisionCheck(entityB, entityA)
                debug { entityA.toString() + " : " + entityB.toString() }

            }
        }

        for (a in 0 until entities!!.size()) {
            val entityA = entities!!.get(a)

            for (b in 0 until entities!!.size()) {
                val entityB = entities!!.get(b)

                if (entityA != null && entityB != null) {
                    collisionCheck(entityA, entityB)
                    collisionCheck(entityB, entityA)
                }
            }
        }



    }
}

