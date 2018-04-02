package edu.gvsu.cis.spacejourney

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenManager
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.physics.box2d.Box2D
import edu.gvsu.cis.spacejourney.component.Parallax
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.Velocity
import edu.gvsu.cis.spacejourney.screens.*
import edu.gvsu.cis.spacejourney.util.ParallaxAccessor
import edu.gvsu.cis.spacejourney.util.StaticSpriteAccessor
import edu.gvsu.cis.spacejourney.util.TransformAccessor
import edu.gvsu.cis.spacejourney.util.VelocityAccessor
import ktx.app.KtxGame

/**
The core of the game, where we spawn the first screen.
 */
class SpaceJourney : KtxGame<BaseScreen>() {

    // Global asset manager shared between all screens via reference to this class
    // Most of the assets should be pre-loaded in the `LoadingScreen` class
    private object Holder {
        val ASSETS = AssetManager()
        val TWEEN  = TweenManager()
    }

    /**
     * Companion object that holds a static instance of the game's asset manager.
     */
    companion object {
        val tweenManager: TweenManager by lazy { Holder.TWEEN }
        val assetManager: AssetManager by lazy { Holder.ASSETS }
    }

    /**
     * Method that LibGDX calls to instantiate the game.
     */
    override fun create() {

        Gdx.graphics.setTitle(Strings.GAME_TITLE)

        // Reference: https://github.com/libktx/ktx/tree/master/log
        Gdx.app.logLevel = LOG_DEBUG

        // Initialize our Box2D physics.
        Box2D.init()

        // Register our custom Accessors
        Tween.registerAccessor(StaticSprite::class.java, StaticSpriteAccessor())
        Tween.registerAccessor(Velocity::class.java, VelocityAccessor())
        Tween.registerAccessor(Transform::class.java, TransformAccessor())
        Tween.registerAccessor(Parallax::class.java, ParallaxAccessor())
        Tween.setCombinedAttributesLimit(4)

        // Register all of the screens upfront so we can easily switch between
        // them by classname and make sure we don't end up in an invalid state
        this.addScreen(LoadingScreen(this))
        this.addScreen(MainMenuScreen(this))
        this.addScreen(LevelSelectScreen(this))
        this.addScreen(LevelScreen(this))
        this.addScreen(LevelEndScreen(this))

        // Switch to the first screen
        this.setScreen<LoadingScreen>()
    }
}
