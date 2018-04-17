package edu.gvsu.cis.spacejourney.system

import aurelienribon.tweenengine.BaseTween
import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.equations.Elastic
import aurelienribon.tweenengine.equations.Linear
import aurelienribon.tweenengine.equations.Sine
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.Health
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.component.colliders.CircleCollider
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.util.Mappers
import edu.gvsu.cis.spacejourney.util.StaticSpriteAccessor
import edu.gvsu.cis.spacejourney.util.VelocityAccessor
import ktx.ashley.has
import ktx.log.debug

/**
 * Private data holder that defines a collidable rectangle.
 */
private data class CollisionRectangle(
        val x: Float,
        val y: Float,
        val width: Float,
        val height: Float) {

    /**
     * Companion object that is used to store conversions between components.
     */
    companion object {

        /**
         * Conversion function that takes a collider component and a transform component to
         * return a CollisionRectangle class.
         * @param transform [edu.gvsu.cis.spacejourney.component.Transform] component of the entity.
         * @param collider [edu.gvsu.cis.spacejourney.component.colliders.BoxCollider] collision component of the entity.
         */
        fun fromComponents(transform: Transform, collider: BoxCollider): CollisionRectangle {
            return CollisionRectangle(
                    transform.position.x + collider.offset.x,
                    transform.position.y + collider.offset.y,
                    collider.width.toFloat(),
                    collider.height.toFloat()
            )
        }
    }
}

/**
 * System class that handles everything that has to do with collision between two
 * entities.
 */
class CollisionSystem : EntitySystem() {

    /**
     * All related entities used by this system.
     */
    private var entities: ImmutableArray<Entity>? = null

    /**
     * @constructor initializes the priorities.
     */
    init {
        priority = SystemPriorities.CollisionSystem
    }

    /**
     * Method that populates the entities object with proper data.
     * @param engine the current Ashley engine object used in the game.
     */
    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(
                Family.all(Transform::class.java) // Object must have a transform
                .one(BoxCollider::class.java, CircleCollider::class.java) // Object has to at least have a Box Collider or a Circle Collider
                .get())
    }

    /**
     * Private helper function that returns whether or not two CollisionRectangles had collided.
     * @param rect1 the first rectangle.
     * @param rect2 the second rectangle.
     * @return true if the two CollisionRectangles have collided, and false otherwise.
     */
    private fun rectangleCollision(rect1: CollisionRectangle, rect2: CollisionRectangle): Boolean {
        return (rect1.x < rect2.x + rect2.width &&
                rect1.x + rect1.width > rect2.x &&
                rect1.y < rect2.y + rect2.height &&
                rect1.height + rect1.y > rect2.y)
    }

    /**
     * Private helper function that checks the collision between two entities.
     * @param entityA the first entity that initiated the collision.
     * @param entityB the second entity that did not initiate the collision.
     * @return true if the collision did happen, and false if the event should be canceled.
     */
    private fun collisionCheck(entityA: Entity, entityB: Entity): Boolean {

        val boxA = Mappers.boxCollider.get(entityA)
        val boxB = Mappers.boxCollider.get(entityB)

        //val circleA = Mappers.circleCollider.get(entityA)
        //val circleB = Mappers.circleCollider.get(entityB)

        if (boxA != null && boxB != null) {

            // #TODO Handle rotation

            val boxATransform = Mappers.transform.get(entityA)
            val boxBTransform = Mappers.transform.get(entityB)

            if (rectangleCollision(
                CollisionRectangle.fromComponents(boxATransform, boxA),
                CollisionRectangle.fromComponents(boxBTransform, boxB)
            )) {
                if (entityA.has(Mappers.enemy) && entityB.has(Mappers.projectile)) {

                    val enemyEntity = entityA
                    val projectileEntity = entityB

                    val health = Mappers.health.get(enemyEntity)
                    val projectile = Mappers.projectile.get(projectileEntity)

                    health.value -= projectile.damage

                    if (health.value <= 0) {

                        GameDataManager.getInstance().score += 100

                        enemyEntity.remove(BoxCollider::class.java)
                        enemyEntity.remove(Health::class.java)

                        val enemyPosition = Mappers.transform.get(enemyEntity)
                        val enemyTexture = SpaceJourney.assetManager.get("enemy_spaceship.png", Texture::class.java)
                        val enemySprite = Mappers.staticSprite.get(enemyEntity)
                        val enemyVelocity = Mappers.velocity.get(enemyEntity)
                        Tween.to(enemySprite, StaticSpriteAccessor
                            .TYPE_SCALE, 1.0f).target(0.0f).ease(Sine.OUT).start(SpaceJourney.tweenManager)
                            .setCallback({ _: Int, _: BaseTween<*> ->
                                engine.removeEntity(enemyEntity)
                            })
                        Tween.to(enemyVelocity, VelocityAccessor
                            .TYPE_ANGULAR, 0.5f).target(-10f).ease(Linear.INOUT).start(SpaceJourney.tweenManager)
                    }

                    engine.removeEntity(projectileEntity)

                    return true
                }
                if (entityA.has(Mappers.player) && entityB.has(Mappers.enemy)) {

                    val playerEntity = entityA
                    val enemyEntity = entityB

                    val health = Mappers.health.get(playerEntity)

                    health.value -= 1

                    GameDataManager.getInstance().lives = health.value

                    if (health.value <= 0) {
                        debug { "Player has died" }
                        engine.removeEntity(playerEntity)
                    } else {
                        debug { "Player now has ${health.value} lives remaining" }
                    }

                    GameDataManager.getInstance().score += 100

                    // Logic to try and change the player's pixmap

                    enemyEntity.remove(BoxCollider::class.java)
                    enemyEntity.remove(Health::class.java)

                    val enemyPosition = Mappers.transform.get(enemyEntity)
                    val enemyTexture = SpaceJourney.assetManager.get("enemy_spaceship.png", Texture::class.java)
                    val enemySprite = Mappers.staticSprite.get(enemyEntity)
                    val enemyVelocity = Mappers.velocity.get(enemyEntity)
                    val playerSprite = Mappers.staticSprite.get(playerEntity)
                    Tween.to(enemySprite, StaticSpriteAccessor
                        .TYPE_SCALE, 2.0f).target(0.0f).ease(Elastic.INOUT).start(SpaceJourney.tweenManager)
                        .setCallback({ _: Int, _: BaseTween<*> ->
                            if (engine != null) {
                                engine.removeEntity(enemyEntity)
                            }
                    })
                    Tween.to(enemyVelocity, VelocityAccessor
                        .TYPE_ANGULAR, 0.5f).target(-6f).ease(Linear.INOUT).start(SpaceJourney.tweenManager)

                    // TODO - Implement temporary invincibility period, and checking if player has box collision.
//                    val collider = engine.createComponent(BoxCollider::class.java)
//                    collider.width = playerSprite.texture!!.width
//                    collider.height = playerSprite.texture!!.height
//                    collider.offset = Vector2(-24f, -24f)

                    val originalColor = playerSprite.color
                    playerSprite.color = Color(1.0f, 0.0f, 0.0f, 1.0f)
//                    playerEntity.remove(BoxCollider::class.java)
                    Tween.to(playerSprite, StaticSpriteAccessor
                            .TYPE_COLOR, 0.175f).target(
                                originalColor?.r!!,
                                originalColor.g,
                                originalColor.b,
                                originalColor.a
                            )
                            .ease(Linear.INOUT)
                            .repeat(3, 0.0f)
                            .start(SpaceJourney.tweenManager)
                            .setCallback({ _: Int, _: BaseTween<*> ->
                                playerSprite?.color = originalColor
//                                playerEntity?.add(collider)
                            })
//                    playerSprite.transparency = 0.0f
//                    Tween.to(playerSprite, StaticSpriteAccessor
//                            .TYPE_ALPHA, 0.175f).target(1.0f).ease(Elastic.INOUT)
//                            .repeat(3, 0.0f)
//                            .start(SpaceJourney.tweenManager)
                    return true
                }
            }
        }

        return false
    }

    /**
     * Function that is used to update the CollisionSystem periodically from a screen's
     * update method.
     * @param deltaTime the time between the last and current update cycle.
     */
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
                    if (collisionCheck(entityA, entityB)) {
                        collisionCheck(entityB, entityA)
                    }
                }
            }
        }
    }
}
