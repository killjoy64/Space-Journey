package edu.gvsu.cis.spacejourney.screens

import edu.gvsu.cis.spacejourney.SpaceJourney

/*
Presents a progress bar while the assets are being loaded for the game, then switches to the MainMenu screen.
#TODO only override methods that need to be overriden
 */
class LoadingScreen(game : SpaceJourney) : BaseScreen(game, "LoadingScreen") {

    override fun show() {
        super.show()

        // for now just skip this screen
        this.game.setScreen<MainMenuScreen>()
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