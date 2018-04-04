package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Strings
import edu.gvsu.cis.spacejourney.input.MainMenuInputListener
import edu.gvsu.cis.spacejourney.managers.MusicManager
import ktx.actors.onClick

/**
 * The main menu for the game where we can change settings, start a game, load, save, quit, etc.
 */
class MainMenuScreen(game: SpaceJourney) : BaseScreen(game, "MainMenuScreen") {

    private var stage: Stage? = null

    private var screenData: Table? = null
    private var option1: Label? = null
    private var option2: Label? = null

    private var font: BitmapFont? = null
    private var inputListener: MainMenuInputListener? = null

    private var music: Music? = null

    private var touched: Boolean = false

    /**
     * Method that creates initial logic for the screen, and shows the options
     * in a default font.
     */
    override fun show() {
        super.show()

        val camera = OrthographicCamera()
        val viewport = FitViewport(
                Constants.getVirtualWidth(),
                Constants.getVirtualHeight(),
                camera)

        stage = Stage(viewport)

        screenData = Table()

        font = SpaceJourney.assetManager.get("fonts/default.fnt")
        font?.data?.scale(0.175f)

        option1 = Label(String.format(Strings.MENU_OPTION_1, "  "), Label.LabelStyle(font, Color.WHITE))
        option2 = Label(String.format(Strings.MENU_OPTION_2, "  "), Label.LabelStyle(font, Color.WHITE))

        screenData?.setFillParent(true)
        screenData?.add(Label(Strings.GAME_TITLE, Label.LabelStyle(font, Color.WHITE)))?.expandX
        screenData?.row()
        screenData?.add(option1)?.expandX
        screenData?.row()
        screenData?.add(option2)?.expandX

        stage?.addActor(screenData)

        inputListener = MainMenuInputListener(2)

        /*music = SpaceJourney.assetManager.get("title.mp3", Music::class.java)
        music?.volume = 0.1f
        music?.isLooping = true
        music?.play()*/

        Gdx.input.inputProcessor = InputMultiplexer(inputListener, stage)

        music = SpaceJourney.assetManager.get("title.mp3", Music::class.java)
        MusicManager.getInstance().music = music
//        this.game.setScreen<LevelSelectScreen>()

        option1?.onClick {
            option1?.setText(String.format(Strings.MENU_OPTION_1, "->"))
            option2?.setText(String.format(Strings.MENU_OPTION_2, "  "))
            inputListener!!.currentChoice = 1
            touched = true
        }

        option2?.onClick {
            option1?.setText(String.format(Strings.MENU_OPTION_1, "  "))
            option2?.setText(String.format(Strings.MENU_OPTION_2, "->"))
            inputListener!!.currentChoice = 2
            touched = true
        }

        // For debugging purposes
        // this.game.setScreen<UpgradeScreen>()
    }

    /**
     * Disposes of the stage, and current game music.
     */
    override fun dispose() {
        super.dispose()
        stage?.dispose()
        music?.dispose()
    }

    /**
     * Constantly updates the screen with new content.
     * @param delta The time between the last and current call of the render() method.
     */
    override fun render(delta: Float) {
        super.render(delta)

        stage?.viewport?.apply()

        stage?.act()
        stage?.draw()

        if (inputListener!!.currentChoice == 1) {
            option1?.setText(String.format(Strings.MENU_OPTION_1, "->"))
            option2?.setText(String.format(Strings.MENU_OPTION_2, "  "))
        } else if (inputListener!!.currentChoice == 2) {
            option1?.setText(String.format(Strings.MENU_OPTION_1, "  "))
            option2?.setText(String.format(Strings.MENU_OPTION_2, "->"))
        }

        if (inputListener!!.gameCanStart() || touched) {
            this.game.setScreen<LevelSelectScreen>()
        }
    }

    /**
     * Overriden method that is called whenever the screen changes sizes.
     * @param width New width that the screen has resized to.
     * @param height New height that the screen has resized to.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        stage?.viewport?.update(width, height, true)
    }
}