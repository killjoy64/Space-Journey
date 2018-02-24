package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.*
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.component.Player
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.Velocity
import edu.gvsu.cis.spacejourney.entity.EntityDirection
import edu.gvsu.cis.spacejourney.entity.PlayerSpaceship
import edu.gvsu.cis.spacejourney.managers.ActiveProjectileManager
import edu.gvsu.cis.spacejourney.util.Mappers
import ktx.log.debug
import ktx.math.plus

class PlayerControllerSystem : EntitySystem() {
    private var entities: ImmutableArray<Entity>? = null

    init {
        priority = 0
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(Family.all(Player::class.java, Transform::class.java, Velocity::class.java).get())
    }

    override fun update(deltaTime: Float) {
        for (i in 0 until entities!!.size()) {
            val entity = entities!!.get(i)

            val transform = Mappers.transform.get(entity)
            val velocity = Mappers.velocity.get(entity)

            val newVelocity = Vector2()

            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                newVelocity.x = 1.0f
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                newVelocity.x = -1.0f
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                newVelocity.y = 1.0f
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                newVelocity.y = -1.0f
            }

            //velocity.value = newVelocity
            transform.position += newVelocity

            debug { "Player Position: ${transform.position} Player Velocity: ${velocity.value}" }
        }


        /*if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            time += delta
            if (time >= spawnFrequency) {
                val x = (this.player.getX() + this.player.getWidth() / 2) / Constants.PX_PER_M
                var y = (this.player.getY() + this.player.getHeight()) / Constants.PX_PER_M

                y = y - 32 / Constants.PX_PER_M

                this.projManager.spawnLaser(x + 16 / Constants.PX_PER_M, y)
                this.projManager.spawnLaser(x - 16 / Constants.PX_PER_M, y)

                this.time = 0.0f
            }
        }*/


    }
}