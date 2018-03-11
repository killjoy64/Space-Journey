package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.util.Mappers

class RenderingSystem : SortedIteratingSystem(Family.all(StaticSprite::class.java, Transform::class.java).get(), ZComparator()) {

    private var spriteBatch : SpriteBatch? = null
    private var camera : OrthographicCamera = OrthographicCamera()
    private var viewport : FitViewport? = null

    init {
        priority = SystemPriorities.RenderingSystem
        spriteBatch = SpriteBatch()

        viewport = FitViewport(1920f, 1080f)

        //viewport?.camera?.translate(viewport!!.worldWidth / 2.0f, viewport!!.worldHeight / 2.0f, 0.0f)

        viewport?.update(1920, 1080, true)

    }

    override fun update(deltaTime: Float) {

        camera.update()

        viewport.apply {

            spriteBatch?.projectionMatrix = viewport?.camera?.combined

            spriteBatch?.begin()

            super.update(deltaTime)

            spriteBatch?.end()
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val transform = Mappers.transform.get(entity)
        val staticSprite = Mappers.staticSprite.get(entity)

        // Render the entity

        // #TODO Add Rotation to this draw call
        val position = transform.position
        val size = Vector2(staticSprite.texture?.width!!.toFloat(), staticSprite.texture?.height!!.toFloat())
        val scale = staticSprite.scale.toFloat()

        spriteBatch?.draw(TextureRegion(staticSprite.texture), position.x, position.y, (size.x / 2.0f) * scale, (size.y / 2.0f) * scale, size.x, size.y, scale, scale, transform.rotation)

    }

    private class ZComparator : Comparator<Entity> {
        private val staticSprite = ComponentMapper.getFor(StaticSprite::class.java)

        override fun compare(e1: Entity, e2: Entity): Int {
            return staticSprite.get(e2).zindex - staticSprite.get(e1).zindex
        }
    }

    fun resize(width: Int, height: Int) {

        viewport?.update(width, height, true)
    }
}
