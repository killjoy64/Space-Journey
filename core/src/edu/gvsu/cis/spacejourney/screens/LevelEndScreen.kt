package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FillViewport
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Strings
import edu.gvsu.cis.spacejourney.managers.GameDataManager

/**
 * Screen class that is shown once a level is complete. It displays a short animation of
 * the points calculation, and how many lives were left.
 */
class LevelEndScreen(game: SpaceJourney) : BaseScreen(game, "LevelEndScreen") {

    private var stage: Stage? = null

    private var screenData: Table? = null
    private var font: BitmapFont? = null
    private var style: Label.LabelStyle? = null

    private var scoreLabel: Label? = null
    private var livesLabel: Label? = null

    private var time: Float = 0.0f
    private var score: Int = 0
    private var lives: Int = 0
    private var scoreIncrement: Int = 1

    /**
     * Overriden method from BaseScreen that initializes a viewport, table labels, and
     * data placeholders.
     */
    override fun show() {
        super.show()

        val viewport = FillViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        stage = Stage(viewport)

        font = SpaceJourney.assetManager.get("fonts/default.fnt")
        font?.data?.scale(0.01f)
        style = Label.LabelStyle(font, Color.WHITE)

        score = 0
        lives = 0

        scoreLabel = Label(String.format(Strings.HUD_SCORE, score), style)
        livesLabel = Label(String.format(Strings.LIVES_LABEL, lives), style)

        screenData = Table()
        screenData?.setFillParent(true)
        screenData?.add(Label(Strings.LEVEL_END, style))?.padBottom(50.0f)?.expandX
        screenData?.row()
        screenData?.add(scoreLabel)?.expandX
        screenData?.row()
        screenData?.add(livesLabel)?.expandX

        stage?.addActor(screenData)

        time = 0.0f
        scoreIncrement = if (GameDataManager.getInstance().score % 2 == 0) 2 else 3
    }

    /**
     * Overriden method that periodically updates the screen.
     */
    override fun render(delta: Float) {
        super.render(delta)

        stage?.viewport?.apply()
        stage?.act()
        stage?.draw()

        if (score >= GameDataManager.getInstance().score) {

            time += delta

            if (time >= 0.5f) {
                livesLabel?.setText(String.format(Strings.LIVES_LABEL, lives))
                lives++
                time = 0.0f
            }

            if (lives >= GameDataManager.getInstance().lives + 2) {
                this.game.setScreen<LevelSelectScreen>()
            }
        } else {
            score += scoreIncrement
            scoreLabel?.setText(String.format(Strings.HUD_SCORE, score))
        }
    }

    /**
     * Overriden method that resizes the screen, and its viewports.
     * @param width the new width of the screen.
     * @param height the new height of the screen.
     */
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage?.viewport?.update(width, height, true)
    }

    /**
     * Overriden method that disposes of the view stage, and display font.
     */
    override fun dispose() {
        super.dispose()
        stage?.dispose()
        font?.dispose()
    }
}