package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import ktx.box2d.createWorld
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import edu.gvsu.cis.spacejourney.component.*
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.util.Mappers
import edu.gvsu.cis.spacejourney.util.toMeters
import edu.gvsu.cis.spacejourney.util.toPixels
import ktx.app.use
import ktx.ashley.get
import ktx.ashley.has
import ktx.box2d.body
import ktx.log.debug

class RenderingSystem : SortedIteratingSystem(Family.all(StaticSprite::class.java, Transform::class.java).get(), ZComparator()) {

    private var spriteBatch : SpriteBatch? = null
    private var camera : OrthographicCamera = OrthographicCamera()

    init {
        spriteBatch = SpriteBatch()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val transform = Mappers.transform.get(entity);
        val staticSprite = Mappers.staticSprite.get(entity);

        camera.viewportWidth = Gdx.graphics.width.toFloat()
        camera.viewportHeight = Gdx.graphics.height.toFloat()
        //camera.translate(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f)
        camera.update()

        spriteBatch?.projectionMatrix = camera.combined

        // Render the entity
        spriteBatch?.use {
            // #TODO Add Rotation to this draw call
            debug { "Drawing" }
            it.draw(staticSprite.texture, transform.position.x, transform.position.y)
        }
    }

    private class ZComparator : Comparator<Entity> {
        private val staticSprite = ComponentMapper.getFor(StaticSprite::class.java)

        override fun compare(e1: Entity, e2: Entity): Int {
            return Math.signum(staticSprite.get(e1).zindex - staticSprite.get(e2).zindex).toInt()
        }
    }

    fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.translate(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f)
        camera.update()
    }
}
