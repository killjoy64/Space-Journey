package edu.gvsu.cis.spacejourney.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.utils.ImmutableArray
import ktx.box2d.createWorld
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.systems.SortedIteratingSystem
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

class RenderingSystem : SortedIteratingSystem(Family.all(StaticSprite::class.java, Transform::class.java).get(), ZComparator()) {

    private val staticSprites: ComponentMapper<StaticSprite>
    private var spriteBatch : SpriteBatch? = null
    private var camera : OrthographicCamera = OrthographicCamera()

    private val debugBox2D = true
    private val box2DFamily = Family.all(Box2D::class.java).get()
    private var box2DEntities: ImmutableArray<Entity>? = null
    private var box2DShapeRenderer : ShapeRenderer? = null // #TODO Dispose me

    init {
        staticSprites = ComponentMapper.getFor(StaticSprite::class.java)
        spriteBatch = SpriteBatch()
    }

    override fun addedToEngine(engine: Engine) {
        box2DEntities = engine.getEntitiesFor(Family.all(Box2D::class.java).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val transform = Mappers.transform.get(entity);
        val staticSprite = Mappers.staticSprite.get(entity);

        spriteBatch?.projectionMatrix = camera.combined

        // Render the entity
        spriteBatch?.use {
            // #TODO Add Rotation to this draw call
            it.draw(staticSprite.texture, transform.position.x, transform.position.y)
        }
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        if (debugBox2D){

            box2DShapeRenderer?.projectionMatrix = camera.combined

            for (i in 0 until entities!!.size()) {
                val entity = entities!!.get(i)

                if (entity.has(Mappers.box2D) && entity.has(Mappers.boxCollider)){
                    val boxCollider = entity.get(Mappers.boxCollider)!!
                    val box2D = entity.get(Mappers.box2D)!!

                    // Render the entity
                    // #TODO Add Rotation to this draw call

                    val position = box2D.body?.position?.toPixels()!!

                    box2DShapeRenderer?.color = Color.RED
                    box2DShapeRenderer?.box(position.x, position.y, 1.0f, boxCollider.size, boxCollider.size, 0.0f)

                }

            }
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
