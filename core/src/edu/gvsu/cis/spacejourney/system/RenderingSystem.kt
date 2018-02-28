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
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
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
        priority = SystemPriorities.RenderingSystem
        spriteBatch = SpriteBatch()

        camera.viewportWidth = Gdx.graphics.width.toFloat()
        camera.viewportHeight = Gdx.graphics.height.toFloat()
        camera.translate(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f)
        camera.update()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val transform = Mappers.transform.get(entity)
        val staticSprite = Mappers.staticSprite.get(entity)

        camera.update()

        spriteBatch?.projectionMatrix = camera.combined

        // Render the entity
        spriteBatch?.use {

            // #TODO Add Rotation to this draw call
            val position = transform.position
            val size = Vector2(staticSprite.texture?.width!!.toFloat(), staticSprite.texture?.height!!.toFloat())
            val scale = staticSprite.scale.toFloat()

            it.draw(TextureRegion(staticSprite.texture), position.x, position.y, (size.x * scale) / 2.0f, (size.y * scale) / 2.0f, size.x, size.y, scale, scale, transform.rotation)


        }
    }

    private class ZComparator : Comparator<Entity> {
        private val staticSprite = ComponentMapper.getFor(StaticSprite::class.java)

        override fun compare(e1: Entity, e2: Entity): Int {
            return staticSprite.get(e2).zindex - staticSprite.get(e1).zindex
        }
    }

    fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        //camera.translate(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f)
        camera.update()
    }
}
