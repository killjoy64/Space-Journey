package edu.gvsu.cis.spacejourney

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.physics.box2d.Box2D
import edu.gvsu.cis.spacejourney.screens.*
import ktx.app.KtxGame

/*
The core of the game, where we spawn the first screen.
 */
class SpaceJourney : KtxGame<BaseScreen>() {

    // Global asset manager shared between all screens via reference to this class
    // Most of the assets should be pre-loaded in the `LoadingScreen` class
    private object Holder {
        val ASSETS = AssetManager()
    }

    companion object {
        val assetManager: AssetManager by lazy { Holder.ASSETS }
    }

    override fun create() {

        Gdx.graphics.setTitle(Strings.GAME_TITLE)

        // Reference: https://github.com/libktx/ktx/tree/master/log
        Gdx.app.logLevel = LOG_DEBUG

        // Initialize our Box2D physics.
        Box2D.init()

        // Register all of the screens upfront so we can easily switch between
        // them by classname and make sure we don't end up in an invalid state
        this.addScreen(LoadingScreen(this))
        this.addScreen(MainMenuScreen(this))
        this.addScreen(LevelSelectScreen(this))
        this.addScreen(LevelScreen(this))

        // Switch to the first screen
        this.setScreen<LoadingScreen>()
    }

}
