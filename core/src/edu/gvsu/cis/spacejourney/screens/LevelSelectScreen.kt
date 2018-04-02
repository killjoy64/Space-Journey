package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FillViewport
import com.sun.org.apache.xpath.internal.operations.Bool
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Strings
import edu.gvsu.cis.spacejourney.input.MainMenuInputListener
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.managers.MusicManager
import ktx.actors.onClick
import ktx.log.debug

/**
 * Class that designates all logic within the level select screen.
 */
class LevelSelectScreen(game: SpaceJourney) : BaseScreen(game, "LevelSelectScreen") {

    private var stage: Stage? = null

    private var screenData: Table? = null
    private var option1: Label? = null
    private var option2: Label? = null
    private var option3: Label? = null

    private var font: BitmapFont? = null

    private var inputListener: MainMenuInputListener? = null

    private var touched : Boolean = false

    /**
     * Method that creates the initial screen logic, creates a default font, and adds the options
     * to the screen.
     */
    override fun show() {
        super.show()

        val viewport = FillViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        stage = Stage(viewport)

        font = SpaceJourney.assetManager.get("fonts/default.fnt")
        font?.data?.scale(0.01f)
        option1 = Label(String.format(Strings.LEVEL_ONE, "  "), Label.LabelStyle(font, Color.WHITE))
        option2 = Label(String.format(Strings.LEVEL_TWO, "  "), Label.LabelStyle(font, Color.WHITE))
        option3 = Label(String.format(Strings.LEVEL_THREE, "  "), Label.LabelStyle(font, Color.WHITE))

        screenData = Table()
        screenData?.setFillParent(true)
        screenData?.add(Label(Strings.LEVEL_SELECT_TITLE, Label.LabelStyle(font, Color.WHITE)))?.padBottom(50.0f)?.expandX
        screenData?.row()
        screenData?.add(option1)?.expandX
        screenData?.row()
        screenData?.add(option2)?.expandX
        screenData?.row()
        screenData?.add(option3)?.expandX

        stage?.addActor(screenData)

        inputListener = MainMenuInputListener(3)
        Gdx.input.inputProcessor = InputMultiplexer(inputListener, stage)

        if (!MusicManager.getInstance().music.isPlaying) {
            MusicManager.getInstance().music = SpaceJourney.assetManager.get("title.mp3", Music::class.java)
        }

        option1?.onClick {
            option1?.setText(String.format(Strings.LEVEL_ONE, "->"))
            option2?.setText(String.format(Strings.LEVEL_TWO, "  "))
            option3?.setText(String.format(Strings.LEVEL_THREE, "  "))
            inputListener!!.currentChoice = 1
            touched = true
        }

        option2?.onClick {
            option1?.setText(String.format(Strings.LEVEL_ONE, "  "))
            option2?.setText(String.format(Strings.LEVEL_TWO, "->"))
            option3?.setText(String.format(Strings.LEVEL_THREE, "  "))
            inputListener!!.currentChoice = 2
            touched = true
        }

        option3?.onClick {
            option1?.setText(String.format(Strings.LEVEL_ONE, "  "))
            option2?.setText(String.format(Strings.LEVEL_TWO, "  "))
            option3?.setText(String.format(Strings.LEVEL_THREE, "->"))
            inputListener!!.currentChoice = 3
            touched = true
        }
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

        when {
            inputListener!!.currentChoice == 1 -> {
                option1?.setText(String.format(Strings.LEVEL_ONE, "->"))
                option2?.setText(String.format(Strings.LEVEL_TWO, "  "))
                option3?.setText(String.format(Strings.LEVEL_THREE, "  "))
            }
            inputListener!!.currentChoice == 2 -> {
                option1?.setText(String.format(Strings.LEVEL_ONE, "  "))
                option2?.setText(String.format(Strings.LEVEL_TWO, "->"))
                option3?.setText(String.format(Strings.LEVEL_THREE, "  "))
            }
            inputListener!!.currentChoice == 3 -> {
                option1?.setText(String.format(Strings.LEVEL_ONE, "  "))
                option2?.setText(String.format(Strings.LEVEL_TWO, "  "))
                option3?.setText(String.format(Strings.LEVEL_THREE, "->"))
            }
        }

        if (inputListener!!.gameCanStart() || touched) {
            GameDataManager.getInstance().levelNumber = inputListener!!.currentChoice

            if (MusicManager.getInstance().music != null) {
                MusicManager.getInstance().stop()
            }

            this.game.setScreen<LevelScreen>()
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

    /**
     * Method that disposes of fonts, and stages used to render the text.
     */
    override fun dispose() {
        super.dispose()
        stage?.dispose()
        font?.dispose()
    }
}