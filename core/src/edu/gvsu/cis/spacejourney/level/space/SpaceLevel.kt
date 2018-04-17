package edu.gvsu.cis.spacejourney.level.space

import aurelienribon.tweenengine.BaseTween
import aurelienribon.tweenengine.Timeline
import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.equations.Linear
import aurelienribon.tweenengine.equations.Quad
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.bitfire.postprocessing.PostProcessor
import com.bitfire.postprocessing.effects.Bloom
import com.bitfire.postprocessing.effects.Vignette
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.Parallax
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.level.Level
import edu.gvsu.cis.spacejourney.level.choreography.events.EnemySpawnEvent
import edu.gvsu.cis.spacejourney.level.choreography.LevelChoreographer
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay
import edu.gvsu.cis.spacejourney.system.PlayerControllerSystem
import edu.gvsu.cis.spacejourney.util.*
import ktx.ashley.add
import ktx.ashley.entity
import ktx.ashley.has
import ktx.log.debug

/**
 * Level class that defines logic for the space level.
 */
class SpaceLevel : Level() {

    private var choreographer: LevelChoreographer? = null

    private var backgroundOne: Entity? = null
    private var backgroundTwo: Entity? = null
    private var backgroundThree: Entity? = null

    /**
     * Overriden function that initializes entities, and schedules events.
     */
    override fun init(engine: Engine) {
        super.init(engine)

        this.hud = DefaultOverlay()
        this.music = SpaceJourney.assetManager.get("Space Background Music.mp3", Music::class.java)

        this.backgroundOne = engine.entity {
            with<Transform> {
                position = Vector2(0f, 0f)
            }
            with<StaticSprite> {
                zindex = ZIndex.PARALLAX_BACKGROUND_LAYER1
                texture = SpaceJourney.assetManager.get("parallax_medium_star_layer.png", Texture::class.java)
                repeating = true
                size = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
                scale = 1.0f
            }
            with<Parallax> {
                speed = 0.25f
            }
        }

        this.backgroundTwo = engine.entity {
            with<Transform> {
                position = Vector2(0f, 0f)
            }
            with<StaticSprite> {
                zindex = ZIndex.PARALLAX_BACKGROUND_LAYER2
                texture = SpaceJourney.assetManager.get("parallax_planet_layer.png", Texture::class.java)
                repeating = true
                size = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
                scale = 1.0f
            }
            with<Parallax> {
                speed = 0.1f
            }
        }

        this.backgroundThree = engine.entity {
            with<Transform> {
                position = Vector2(0f, 0f)
            }
            with<StaticSprite> {
                zindex = ZIndex.PARALLAX_BACKGROUND_LAYER3
                texture = SpaceJourney.assetManager.get("parallax_small_star_layer.png", Texture::class.java)
                repeating = true
                size = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
                scale = 1.0f
            }
            with<Parallax> {
                speed = 0.075f
            }
        }

        engine.add { backgroundOne }
        engine.add { backgroundTwo }
        engine.add { backgroundThree }

        choreographer = LevelChoreographer(engine)

        for (i in 0..9) {
            choreographer!!.schedule(1.0f + i * 0.2f, EnemySpawnEvent())
        }
    }

    /**
     * Overriden method that initializes bloom, and vignette post processing effects.
     */
    override fun initEffects(postProcessor: PostProcessor) {
        val bloom = Bloom((Gdx.graphics.width * 0.25f).toInt(), (Gdx.graphics.height * 0.25f).toInt())
        bloom.setBloomIntesity(2.25f)
        postProcessor.addEffect(bloom)

        val vignette = Vignette(Gdx.graphics.width, Gdx.graphics.height, false)
        postProcessor.addEffect(vignette)
    }

    /**
     * Overriden method that updates the screen periodically.
     */
    override fun update(delta: Float) {
        super.update(delta)

        choreographer!!.update(delta)
        hud?.poll(engine!!)

        if (choreographer!!.isEmpty()) {
//            val lastEvent = choreographer!!.getLastEvent()?.event as? EnemySpawnEvent
            val inputEnabled = engine?.getSystem(PlayerControllerSystem::class.java)?.inputEnabled
            var noEnemies = true
            engine!!.entities.forEach {
                if (it.has(Mappers.enemy)) {
                    noEnemies = false
                }
            }
            if (noEnemies && inputEnabled!!) {
                engine?.getSystem(PlayerControllerSystem::class.java)?.inputEnabled = false
                debug { "Last entity removed. Starting level end sequence." }
                this.startAnimationSequence()
            }
        }
    }

    /**
     * Private helper method that starts the level end animation sequence.
     */
    private fun startAnimationSequence() {
        val xGoal = Gdx.graphics.width.toFloat() / 2
        val yGoal = Gdx.graphics.height.toFloat() / 2
        val player = engine!!.entities.get(0)
        val playerTransform = Mappers.transform.get(player)
        val parallaxOne = Mappers.parallax.get(backgroundOne)
        val parallaxTwo = Mappers.parallax.get(backgroundTwo)
        val parallaxThree = Mappers.parallax.get(backgroundThree)
        val spriteOne = Mappers.staticSprite.get(backgroundOne)
        val spriteTwo = Mappers.staticSprite.get(backgroundTwo)
        val spriteThree = Mappers.staticSprite.get(backgroundThree)
        val colorOne = spriteOne?.color
        val colorTwo = spriteTwo?.color
        val colorThree = spriteThree?.color
        val transitionColor = Color(0.0f, 0.0f, 0.0f, 0.0f)

        val sequenceOne = Timeline.createSequence()
                .beginParallel()
                .push(Tween.to(playerTransform, TransformAccessor.TYPE_POSITION, 4.0f)
                        .target(xGoal, yGoal)
                        .ease(Linear.INOUT))
                .push(Tween.to(parallaxOne, ParallaxAccessor.TYPE_PARALLAX, 4.0f)
                        .target(2.25f)
                        .ease(Quad.IN))
                .push(Tween.to(parallaxTwo, ParallaxAccessor.TYPE_PARALLAX, 4.0f)
                        .target(1.0f)
                        .ease(Quad.IN))
                .push(Tween.to(parallaxThree, ParallaxAccessor.TYPE_PARALLAX, 4.0f)
                        .target(0.75f)
                        .ease(Quad.IN))
                .end()

        Timeline.createSequence()
                .push(sequenceOne)
                .beginParallel()
                .push(Tween.to(playerTransform, TransformAccessor.TYPE_POSITION, 2.75f)
                        .target(xGoal, yGoal * 2)
                        .ease(Quad.IN))
                .push(Tween.to(parallaxOne, ParallaxAccessor.TYPE_PARALLAX, 3.0f)
                        .target(4.25f)
                        .ease(Linear.INOUT))
                .push(Tween.to(parallaxTwo, ParallaxAccessor.TYPE_PARALLAX, 3.0f)
                        .target(3.0f)
                        .ease(Linear.INOUT))
                .push(Tween.to(parallaxThree, ParallaxAccessor.TYPE_PARALLAX, 3.0f)
                        .target(2.75f)
                        .ease(Linear.INOUT))
                .push(Tween.to(spriteOne, StaticSpriteAccessor.TYPE_COLOR, 3.0f)
                        .target(
                                transitionColor.r,
                                transitionColor.g,
                                transitionColor.b,
                                transitionColor.a)
                        .ease(Quad.IN))
                .push(Tween.to(spriteTwo, StaticSpriteAccessor.TYPE_COLOR, 3.0f)
                        .target(
                                transitionColor.r,
                                transitionColor.g,
                                transitionColor.b,
                                transitionColor.a)
                        .ease(Quad.IN))
                .push(Tween.to(spriteThree, StaticSpriteAccessor.TYPE_COLOR, 3.0f)
                        .target(
                                transitionColor.r,
                                transitionColor.g,
                                transitionColor.b,
                                transitionColor.a)
                        .ease(Quad.IN))
                .end()
                .start(SpaceJourney.tweenManager)
                .setCallback({ _: Int, _: BaseTween<*> ->
//                    spriteOne?.color = colorOne
//                    spriteTwo?.color = colorTwo
//                    spriteThree?.color = colorThree
                    this.complete = true
                })
    }

    /**
     * Overriden method that disposes of music, and removes all entities from the engine.
     */
    override fun dispose() {
        music?.stop()
        music?.dispose()
        engine?.removeAllEntities()
    }
}
