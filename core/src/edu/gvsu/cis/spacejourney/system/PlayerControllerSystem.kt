package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.Projectile
import edu.gvsu.cis.spacejourney.component.Player
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.Velocity
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.util.Mappers
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.ashley.add
import ktx.ashley.entity
import ktx.log.debug
import ktx.math.plus
import ktx.math.times

/**
 * Player Controller System that handles everything the player can do in the level.
 */
class PlayerControllerSystem : EntitySystem() {
    private var entities: ImmutableArray<Entity>? = null

    private val CONTROLLER_DEADZONE = 0.05

    private var time = 0.0

    var inputEnabled: Boolean = true

    /**
     * @constructor initializes the system priorities, and enables input on
     * instantiation.
     */
    init {
        priority = SystemPriorities.PlayerControllerSystem
        inputEnabled = true
    }

    /**
     * Overriden function that gets all entites that are added to the current engine.
     * @param engine the Ashley engine object that is currently used throughout the game.
     */
    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(Player::class.java, StaticSprite::class.java, Transform::class.java).get())

        for (controller in Controllers.getControllers()) {
            debug { "Found controller: " + controller.name }
        }
    }

    /**
     * Function that is used to update the CollisionSystem periodically from a screen's
     * update method.
     * @param deltaTime the time between the last and current update cycle.
     */
    override fun update(deltaTime: Float) {
        for (i in 0 until entities!!.size()) {
            val entity = entities!!.get(i)

            val transform = Mappers.transform.get(entity)
            val player = Mappers.player.get(entity)
            val staticSprite = Mappers.staticSprite.get(entity)

            val movement = Vector2()
            var attack = false

            Controllers.getControllers().asSequence().withIndex().forEach {

                val controllerID = it.index
                val controller = it.value

                if (controllerID == 0 && inputEnabled) {

                    // The official controller mappings by LibGDX for Xbox360 controllers
                    // don't work on Windows of all platforms... so we have to hardcode them
                    val L_STICK_HORIZONTAL_AXIS = 1
                    val L_STICK_VERTICAL_AXIS = 0
                    val A_BUTTON = 0

                    if (controller.getAxis(L_STICK_VERTICAL_AXIS) > CONTROLLER_DEADZONE) {
                        movement.y = player.movespeed * -controller.getAxis(L_STICK_VERTICAL_AXIS)
                    }
                    if (controller.getAxis(L_STICK_VERTICAL_AXIS) < -CONTROLLER_DEADZONE) {
                        movement.y = player.movespeed * -controller.getAxis(L_STICK_VERTICAL_AXIS)
                    }
                    if (controller.getAxis(L_STICK_HORIZONTAL_AXIS) > CONTROLLER_DEADZONE) {
                        movement.x = player.movespeed * controller.getAxis(L_STICK_HORIZONTAL_AXIS)
                    }
                    if (controller.getAxis(L_STICK_HORIZONTAL_AXIS) < -CONTROLLER_DEADZONE) {
                        movement.x = player.movespeed * controller.getAxis(L_STICK_HORIZONTAL_AXIS)
                    }
                    if (controller.getButton(A_BUTTON)) {
                        attack = true
                    }
                }
            }

            if (inputEnabled) {
                if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    movement.x = player.movespeed
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    movement.x = -player.movespeed
                }
                if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    movement.y = player.movespeed
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    movement.y = -player.movespeed
                }
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                    attack = true
                }
            }

            val deltaMovement = movement * deltaTime

            transform.position += deltaMovement

            // Bounds checking for the player

            val boundsPadding = 30.0f
            val bounds = Rectangle(
                    boundsPadding,
                    boundsPadding,
                    Gdx.graphics.width.toFloat() - boundsPadding * 2f,
                    Gdx.graphics.height.toFloat() - boundsPadding * 2f
            )

            if (transform.position.x < bounds.x) {
                transform.position.x = bounds.x
            }
            if (transform.position.y < bounds.y) {
                transform.position.y = bounds.y
            }
            if (transform.position.x > bounds.x + bounds.width) {
                transform.position.x = bounds.x + bounds.width
            }
            if (transform.position.y > bounds.y + bounds.height) {
                transform.position.y = bounds.y + bounds.height
            }

            // Attacking Logic

            val bulletInheritedVelocityFactor = 0.2f

            time += deltaTime
            if (attack && time >= player.firerate) {

                val playerCenter = Vector2(transform.position)

                val laserTexture = SpaceJourney.assetManager.get("laser.png", Texture::class.java)

                val projectileVelocity = 10.0f

                engine.add {
                    entity {
                        with<Projectile> {
                            damage = 1
                        }
                        with<BoxCollider> {
                            width = laserTexture.height
                            height = laserTexture.width
                        }
                        with<Velocity> {
                            value = Vector2(deltaMovement.x * bulletInheritedVelocityFactor, projectileVelocity)
                        }
                        with<Transform> {
                            position = Vector2(playerCenter.x + 16, playerCenter.y)
                        }
                        with<StaticSprite> {
                            zindex = ZIndex.PROJECTILES
                            texture = laserTexture
                        }
                    }
                }

                engine.add {
                    entity {
                        with<Projectile> {}
                        with<BoxCollider> {
                            width = laserTexture.height
                            height = laserTexture.width
                        }
                        with<Velocity> {
                            value = Vector2(deltaMovement.x * bulletInheritedVelocityFactor, projectileVelocity)
                        }
                        with<Transform> {
                            position = Vector2(playerCenter.x - 32, playerCenter.y)
                        }
                        with<StaticSprite> {
                            zindex = ZIndex.PROJECTILES
                            texture = laserTexture
                        }
                    }
                }

                this.time = 0.0
            }
        }
    }
}