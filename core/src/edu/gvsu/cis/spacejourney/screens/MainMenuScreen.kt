package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Strings
import edu.gvsu.cis.spacejourney.input.MainMenuInputListener

/*
The main menu for the game where we can change settings, start a game, load, save, quit, etc.
*/
class MainMenuScreen(game: SpaceJourney) : BaseScreen(game, "MainMenuScreen") {

    private var stage: Stage? = null

    private var screenData: Table? = null
    private var option1: Label? = null
    private var option2: Label? = null

    private var font: BitmapFont? = null
    private var inputListener: MainMenuInputListener? = null

    private var music: Music? = null

    override fun show() {
        super.show()

        val camera = OrthographicCamera()
        val viewport = FitViewport(
                Constants.VIRTUAL_WIDTH,
                Constants.VIRTUAL_HEIGHT,
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
        Gdx.input.inputProcessor = inputListener

        music = SpaceJourney.assetManager.get("title.mp3", Music::class.java)
        music?.volume = 0.1f
        music?.isLooping = true
        music?.play()

//        this.game.setScreen<LevelSelectScreen>()
    }

    override fun dispose() {
        super.dispose()
        stage?.dispose()
        music?.stop()
        music?.dispose()
    }

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

        if (inputListener!!.gameCanStart()) {
            music?.stop()
            this.game.setScreen<LevelSelectScreen>()
        }

    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        stage?.viewport?.update(width, height, true)
    }
}