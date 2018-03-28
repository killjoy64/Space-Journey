package edu.gvsu.cis.spacejourney.level.one

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.component.Parallax
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.level.Level
import edu.gvsu.cis.spacejourney.level.choreography.events.EnemySpawnEvent
import edu.gvsu.cis.spacejourney.level.choreography.LevelChoreographer
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.screens.hud.DefaultOverlay
import edu.gvsu.cis.spacejourney.system.PlayerControllerSystem
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.ashley.add
import ktx.ashley.entity
import ktx.log.debug

//private DefaultOverlay defaultHud;

class LevelOne : Level() {

    private var choreographer: LevelChoreographer? = null

    override fun init(engine: Engine) {
        super.init(engine)

        this.hud = DefaultOverlay()
        this.music = SpaceJourney.assetManager.get("Space Background Music.mp3", Music::class.java)

        engine.add {
            entity {
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
            entity {
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
            entity {
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
        }

        choreographer = LevelChoreographer(engine)

        for (i in 0..49) {
            choreographer!!.schedule(1.0f + i * 0.2f, EnemySpawnEvent())
        }
    }

    override fun update(delta: Float) {
        super.update(delta)

        choreographer!!.update(delta)
        hud?.poll(engine!!)

        if (choreographer!!.isEmpty()) {
            val lastEvent = choreographer!!.getLastEvent()?.event as? EnemySpawnEvent
            val inputEnabled = engine?.getSystem(PlayerControllerSystem::class.java)?.inputEnabled
            if (!engine!!.entities.contains(lastEvent?.enemyEntity) && inputEnabled!!) {
                // TODO - Implement level end transition!
                engine?.getSystem(PlayerControllerSystem::class.java)?.inputEnabled = false
                debug { "Last entity removed. Starting level end sequence." }

            }
        }
    }

    override fun dispose() {
        music?.stop()
        music?.dispose()
    }
}
