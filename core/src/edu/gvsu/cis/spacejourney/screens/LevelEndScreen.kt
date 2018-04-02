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

class LevelEndScreen(game: SpaceJourney) : BaseScreen(game, "LevelEndScreen") {

    private var stage: Stage? = null

    private var screenData: Table? = null
    private var font: BitmapFont? = null

    private var time: Float = 0.0f

    override fun show() {
        super.show()

        val viewport = FillViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        stage = Stage(viewport)

        font = SpaceJourney.assetManager.get("fonts/default.fnt")
        font?.data?.scale(0.01f)

        screenData = Table()
        screenData?.setFillParent(true)
        screenData?.add(Label(Strings.LEVEL_END, Label.LabelStyle(font, Color.WHITE)))?.padBottom(50.0f)?.expandX

        stage?.addActor(screenData)
    }

    override fun render(delta: Float) {
        super.render(delta)

        stage?.viewport?.apply()
        stage?.act()
        stage?.draw()

        time += delta

        if (time > 2.0f) {
            this.game.setScreen<LevelSelectScreen>()
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage?.viewport?.update(width, height, true)
    }

    override fun dispose() {
        super.dispose()
        stage?.dispose()
        font?.dispose()
    }

}