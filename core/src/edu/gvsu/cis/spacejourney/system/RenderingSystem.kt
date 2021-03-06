package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.util.Mappers

/**
 * Rendering System, renders entities in order of their Z-Index.
 */
class RenderingSystem : SortedIteratingSystem(Family.all(StaticSprite::class.java, Transform::class.java).get(), ZComparator()) {

    var spriteBatch: SpriteBatch? = null
    private var camera: OrthographicCamera = OrthographicCamera()
    private var viewport: FitViewport? = null
    private var debugBatch: ShapeRenderer? = null
    private var debug: Boolean = false

    /**
     * @constructor initializes system priorities, batches, and debug viewports.
     */
    init {
        priority = SystemPriorities.RenderingSystem
        spriteBatch = SpriteBatch()

        debugBatch = ShapeRenderer()

        viewport = FitViewport(1920f, 1080f)

        viewport?.update(1920, 1080, true)
    }

    /**
     * Function that is used to update the CollisionSystem periodically from a screen's
     * update method.
     * @param deltaTime the time between the last and current update cycle.
     */
    override fun update(deltaTime: Float) {

        // Toggle Debug Rendering
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debug = !debug
        }

        camera.update()

        viewport.apply {

            debugBatch?.projectionMatrix = viewport?.camera?.combined

            spriteBatch?.projectionMatrix = viewport?.camera?.combined

            debugBatch?.setAutoShapeType(true)
            debugBatch?.begin()

            spriteBatch?.begin()

            super.update(deltaTime)

            spriteBatch?.end()

            debugBatch?.end()
        }
    }

    /**
     * Overriden function that is called every update() cycle, on every entity that is present
     * in the engine. This is a rather large, costly task, so we try to keep all entity-specific
     * logic inside of this method.
     * @param entity Ashley entity that is currently being processed.
     * @param deltaTime the time between the last and current update cycle.
     */
    override fun processEntity(entity: Entity, deltaTime: Float) {

        val transform = Mappers.transform.get(entity)
        val staticSprite = Mappers.staticSprite.get(entity)

        // Parallax Processing

        if (Mappers.parallax.has(entity)) {
            val parallax = Mappers.parallax.get(entity)

            parallax.offset -= parallax.speed * deltaTime
        }

        // Debug Rendering

        if (debug) {

            if (Mappers.boxCollider.has(entity) && Mappers.transform.has(entity)) {

                val boxCollider = Mappers.boxCollider.get(entity)
                val transform = Mappers.transform.get(entity)

                val positionX = transform.position.x + boxCollider.offset.x
                val positionY = transform.position.y + boxCollider.offset.y

                debugBatch?.set(ShapeRenderer.ShapeType.Line)
                debugBatch?.setColor(1.0f, 1.0f, 1.0f, 1.0f)
                debugBatch?.box(positionX, positionY, 0.0f, boxCollider.width.toFloat(), boxCollider.height.toFloat(), 0.0f)
            }
        }

        // Hitpoint Bar Rendering

        if (Mappers.health.has(entity) && !Mappers.player.has(entity)) {
            val health = Mappers.health.get(entity)
            val x = transform.position.x
            val y = transform.position.y + staticSprite.texture!!.height + 5.0f
            val w = staticSprite.texture!!.width.toFloat()
            val hpW = (w / health.maxValue) * health.value
            val h = 2.5f
            debugBatch?.set(ShapeRenderer.ShapeType.Filled)
            debugBatch?.setColor(1.0f, 0.0f, 0.0f, 1.0f)
            debugBatch?.rect(x, y, w, h)
            debugBatch?.setColor(0.0f, 1.0f, 0.0f, 1.0f)
            debugBatch?.rect(x, y, hpW, h)
        }

        // Render the entity

        // #TODO Add Rotation to this draw call
        val position = transform.position
        val scale = staticSprite.scale.toFloat()
        val color = staticSprite.color
        val alpha = staticSprite.transparency

        // Get size
        val size: Vector2

        if (staticSprite.size == null) {
            size = Vector2(staticSprite.texture?.width!!.toFloat(), staticSprite.texture?.height!!.toFloat())
        } else {
            size = staticSprite.size!!
        }

        // Is the sprite specified to repeat?
        val repeating = staticSprite.repeating

        val curColor: Color = spriteBatch?.color!!
        if (color != null) {
            spriteBatch?.color = staticSprite.color
        } else {
            staticSprite?.color = spriteBatch?.color
            spriteBatch?.color = Color(curColor.r, curColor.g, curColor.b, alpha)
        }

        if (!repeating) {
            spriteBatch?.draw(TextureRegion(staticSprite.texture), position.x, position.y, (size.x / 2.0f) * scale, (size.y / 2.0f) * scale, size.x, size.y, scale, scale, transform.rotation)
        } else {

            staticSprite.texture?.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

            val texRegion = TextureRegion(staticSprite.texture)

            if (Mappers.parallax.has(entity)) {
                val parallax = Mappers.parallax.get(entity)

                texRegion.v = parallax.offset
                texRegion.v2 = parallax.offset + 1.0f

                texRegion.regionWidth = size.x.toInt()
                texRegion.regionHeight = size.y.toInt()
            }

            // #TODO Add other configurations
            spriteBatch?.draw(texRegion, position.x, position.y)
            //spriteBatch?.draw(texRegion, position.x, position.y, (size.x / 2.0f), (size.y / 2.0f), size.x, size.y, 1.0f, 1.0f, transform.rotation)
        }
    }

    /**
     * Inner-private class that compares two entity Z-indexes.
     */
    private class ZComparator : Comparator<Entity> {
        private val staticSprite = ComponentMapper.getFor(StaticSprite::class.java)

        /**
         * Overriden function that compares two entities and their Z-indexes.
         * @param e1 the first entity to compare.
         * @param e2 the second entity to compare.
         */
        override fun compare(e1: Entity, e2: Entity): Int {
            return staticSprite.get(e1).zindex - staticSprite.get(e2).zindex
        }
    }

    /**
     * Helper function that is called whenever the screen is resized.
     * @param width the width that was resized to.
     * @param height the height that was resized to.
     */
    fun resize(width: Int, height: Int) {

        viewport?.update(width, height, true)
    }
}
