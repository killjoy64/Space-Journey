package edu.gvsu.cis.spacejourney.screens

import edu.gvsu.cis.spacejourney.SpaceJourney

/*
The main menu for the game where we can change settings, start a game, load, save, quit, etc.
#TODO only override methods that need to be overriden
*/
class MainMenuScreen(game : SpaceJourney) : BaseScreen(game, "MainMenuScreen") {

    override fun show() {
        super.show()

        // for now just skip this screen
        this.game.setScreen<LevelScreen>()
    }

    override fun dispose() {
        super.dispose()
    }

    override fun hide() {
        super.hide()
    }

    override fun pause() {
        super.pause()
    }

    override fun render(delta: Float) {
        super.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
    }

    override fun resume() {
        super.resume()
    }

}