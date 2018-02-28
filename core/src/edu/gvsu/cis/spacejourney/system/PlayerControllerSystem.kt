package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.entity.EntityDirection
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager
import edu.gvsu.cis.spacejourney.util.Mappers
import ktx.log.debug
import ktx.math.plus
import ktx.math.times
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.mappings.Xbox
import com.badlogic.gdx.graphics.Texture
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.*
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.ashley.add
import ktx.ashley.entity
import ktx.math.div


class PlayerControllerSystem : EntitySystem() {
    private var entities: ImmutableArray<Entity>? = null

    private val CONTROLLER_DEADZONE = 0.1

    private var time = 0.0

    init {
        priority = SystemPriorities.PlayerControllerSystem
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(Player::class.java, StaticSprite::class.java, Transform::class.java).get())
    }

    override fun update(deltaTime: Float) {
        for (i in 0 until entities!!.size()) {
            val entity = entities!!.get(i)

            val transform = Mappers.transform.get(entity)
            val player = Mappers.player.get(entity)
            val staticSprite = Mappers.staticSprite.get(entity)

            val movement = Vector2()
            var attack = false

            Controllers.getControllers().asSequence().take(1).forEach {
                if (it.getAxis(Xbox.L_STICK_VERTICAL_AXIS) > CONTROLLER_DEADZONE) {
                    movement.y = player.movespeed * it.getAxis(Xbox.L_STICK_VERTICAL_AXIS)
                }
                if (it.getAxis(Xbox.L_STICK_VERTICAL_AXIS) < CONTROLLER_DEADZONE) {
                    movement.y = player.movespeed * it.getAxis(Xbox.L_STICK_VERTICAL_AXIS)
                }
                if (it.getAxis(Xbox.L_STICK_HORIZONTAL_AXIS) > CONTROLLER_DEADZONE) {
                    movement.x = player.movespeed * it.getAxis(Xbox.L_STICK_HORIZONTAL_AXIS)
                }
                if (it.getAxis(Xbox.L_STICK_HORIZONTAL_AXIS) < CONTROLLER_DEADZONE) {
                    movement.x = player.movespeed * it.getAxis(Xbox.L_STICK_HORIZONTAL_AXIS)
                }
                if (it.getButton(Xbox.A)) {
                    attack = true
                }
            }

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

            val deltaMovement = movement * deltaTime

            transform.position += deltaMovement

            val bulletInheritedVelocityFactor = 0.2f

            time += deltaTime
            if (attack && time >= player.firerate) {

                val playerCenter = Vector2(transform.position)

                engine.add {
                    entity {
                        with<Projectile> {}
                        with<BoxCollider> {}
                        with<Velocity> {
                            value = Vector2(deltaMovement.x * bulletInheritedVelocityFactor, 6.0f)
                        }
                        with<Transform> {
                            position = Vector2(playerCenter.x + 16, playerCenter.y)
                        }
                        with<StaticSprite> {
                            zindex = ZIndex.PROJECTILES
                            texture = SpaceJourney.assetManager.get("laser.png", Texture::class.java)
                        }
                    }
                }

                engine.add {
                    entity {
                        with<Projectile> {}
                        with<BoxCollider> {}
                        with<Velocity> {
                            value = Vector2(deltaMovement.x * bulletInheritedVelocityFactor, 6.0f)
                        }
                        with<Transform> {
                            position = Vector2(playerCenter.x - 16, playerCenter.y)
                        }
                        with<StaticSprite> {
                            zindex = ZIndex.PROJECTILES
                            texture = SpaceJourney.assetManager.get("laser.png", Texture::class.java)
                        }
                    }
                }

                this.time = 0.0
            }

        }



    }
}