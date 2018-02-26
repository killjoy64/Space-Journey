package edu.gvsu.cis.spacejourney.entity.collectible

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entity.AnimatedEntity
import edu.gvsu.cis.spacejourney.entity.Graveyard
import edu.gvsu.cis.spacejourney.util.toMeters

/**
 * Abstract class that describes a template for a collectible that
 * the player can pickup.
 */
abstract class Collectible(stage: Stage?) : AnimatedEntity(stage) {

    private var sound: Sound? = null

    /**
     * Default constructor that initializes a sound to be played once picked up.
     */
    init {
        sound = SpaceJourney.assetManager.get("default_pickup.wav", Sound::class.java)
    }

    /**
     * Inherited method from the Collidable interface that
     * creates a body, and spawns it in the physics world.
     * @param world current Box2D world that is being
     */
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

    /**
     * Function that is called whenever the player collides with a collectible.
     */
    open fun collect() {
        sound?.play(0.05f)
        Graveyard.BODIES.add(body)
        Graveyard.ACTORS.add(this)
    }

    /**
     * Simple dispose method that stops any current sounds being played, and
     * disposes of those sounds.
     */
    override fun dispose() {
        super.dispose()
        sound?.stop()
        sound?.dispose()
    }
}