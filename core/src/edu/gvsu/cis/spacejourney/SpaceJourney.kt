package edu.gvsu.cis.spacejourney

import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import edu.gvsu.cis.spacejourney.screens.LoadingScreen
import edu.gvsu.cis.spacejourney.screens.BaseScreen
import edu.gvsu.cis.spacejourney.screens.LevelScreen
import edu.gvsu.cis.spacejourney.screens.MainMenuScreen
import ktx.app.KtxGame

/*
The core of the game, where we spawn the first screen.
 */
class SpaceJourney : KtxGame<BaseScreen>() {

    override fun create() {

        // Reference: https://github.com/libktx/ktx/tree/master/log
        Gdx.app.logLevel = LOG_DEBUG

        // Register all of the screens upfront so we can easily switch between
        // them by classname and make sure we don't end up in an invalid state
        this.addScreen(LoadingScreen(this))
        this.addScreen(MainMenuScreen(this))
        this.addScreen(LevelScreen(this))

        // Switch to the first screen
        this.setScreen<LoadingScreen>()
    }


}
