package edu.gvsu.cis.spacejourney.entity.enemy

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entity.AnimatedEntity
import edu.gvsu.cis.spacejourney.entity.Graveyard
import edu.gvsu.cis.spacejourney.util.toMeters

abstract class DestructibleAsteroid(stage: Stage?) : AnimatedEntity(stage) {

    init {
        this.setSize(25f, 25f)
        this.setPosition(1.0f, 0.5f)

        this.addAnimation("idle", SpaceJourney.assetManager.get<Texture>("rotating_pickup.png"), 0, 23, 48, 48)
    }

    override fun createBody(world: World?) {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(
                (x + width / 2).toMeters(),
                (y + height / 2).toMeters()
        )

        val body = world?.createBody(bodyDef)

        val circle = CircleShape()
        circle.radius = (width / 2).toMeters()

        val fixtureDef = FixtureDef()
        fixtureDef.shape = circle
        fixtureDef.isSensor = true

        body?.userData = this
        body?.createFixture(fixtureDef)

        setBody(body)
        setWorld(world)

        circle.dispose()
    }

}