package edu.gvsu.cis.spacejourney.screens

import edu.gvsu.cis.spacejourney.SpaceJourney
import com.sun.awt.SecurityWarning.setPosition
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.ui.Skin

/**
 * Presents a progress bar while the assets are being loaded for the game,
 * then switches to the MainMenu screen.
 */
class LoadingScreen(game: SpaceJourney) : BaseScreen(game, "LoadingScreen") {

    private var stage: Stage? = null
    private var progress: ProgressBar? = null

    /**
     * Private helper function that adds any assets that need be loaded.
     * game.assets in other screens.
     */
    private fun actuallyLoadAssets() {

        SpaceJourney.assetManager.load("laser.png", Texture::class.java)
        SpaceJourney.assetManager.load("enemy_spaceship.png", Texture::class.java)
        SpaceJourney.assetManager.load("player_spaceship.png", Texture::class.java)
        SpaceJourney.assetManager.load("parallax_medium_star_layer.png", Texture::class.java)
        SpaceJourney.assetManager.load("parallax_planet_layer.png", Texture::class.java)
        SpaceJourney.assetManager.load("parallax_small_star_layer.png", Texture::class.java)
        SpaceJourney.assetManager.load("rotating_pickup.png", Texture::class.java)
        SpaceJourney.assetManager.load("Space Background Music.mp3", Music::class.java)
        SpaceJourney.assetManager.load("title.mp3", Music::class.java)
        SpaceJourney.assetManager.load("take_damage.wav", Sound::class.java)
        SpaceJourney.assetManager.load("default_pickup.wav", Sound::class.java)
        SpaceJourney.assetManager.load("fonts/default.fnt", BitmapFont::class.java)
    }

    /**
     * Method that creates initial load logic.
     */
    override fun show() {
        super.show()

        // Setup the stage for the UI
        stage = Stage()

        // Setup a basic UI Skin for the progress bar
        val skin = Skin()
        val pixmap = Pixmap(10, 10, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.WHITE)
        pixmap.fill()
        skin.add("white", Texture(pixmap))

        // Load a texture for the progress bar
        val textureBar = TextureRegionDrawable(TextureRegion(Texture(Gdx.files.internal("progress_bar.png"))))

        // Create a style for the progress bar
        val barStyle = ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureBar)
        barStyle.knobBefore = barStyle.knob

        // Create the progress bar
        progress = ProgressBar(0f, 100f, 0.5f, false, barStyle)
        progress?.setSize(290f, progress?.prefHeight!!)
        progress?.setPosition(Gdx.graphics.width.toFloat() / 2f - progress?.width!! / 2f, Gdx.graphics.height.toFloat() / 2f)

        // Just make it load crazy fast since we're not actually stalling for any loads yet.
        progress?.setAnimateDuration(0.2f)

        // Add the progress bar to the scene
        stage?.addActor(progress)

        actuallyLoadAssets()
    }

    /**
     * Constantly updates the screen with new content, but more specifically,
     * this updates the progress bar, and changes screens when everything is loaded.
     * @param delta The time different between the last and current render() call.
     */
    override fun render(delta: Float) {
        super.render(delta)

        // Update and render the scene
        stage?.act(delta)
        stage?.draw()

        // Normally this should be updated when assets are loaded,
        progress?.value = SpaceJourney.assetManager.progress * 100.0f

        // Once the progress bar hits max value it will start the MainMenu screen
        if (SpaceJourney.assetManager.update() && progress?.visualPercent == 1.0f) {
            this.game.setScreen<MainMenuScreen>()
        }
    }
}