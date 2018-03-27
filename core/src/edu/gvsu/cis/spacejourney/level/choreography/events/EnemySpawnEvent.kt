package edu.gvsu.cis.spacejourney.level.choreography.events

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.*
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.level.choreography.ChoreographEvent
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.ashley.add
import ktx.ashley.entity
import ktx.log.debug

class EnemySpawnEvent : ChoreographEvent() {

    var enemyEntity: Entity? = null

    override fun onEvent(engine: Engine) {

        val randomPosition = Vector2(
            (Math.random().toFloat() * Gdx.graphics.width.toFloat() - 50.0f) + 50.0f,
            Gdx.graphics.height.toFloat())
        val enemyTexture = SpaceJourney.assetManager.get("enemy_spaceship.png", Texture::class.java)

        enemyEntity = engine.entity {
            with<Enemy> {

            }
            with<Health> {
                value = 3
                maxValue = 3
            }
            with<BoxCollider> {
                width = enemyTexture.width - 16
                height = enemyTexture.height - 16
                offset = Vector2(8f, 8f)
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
                texture = enemyTexture
            }
        }

        engine.add {enemyEntity}
    }

}