package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Strings
import edu.gvsu.cis.spacejourney.input.MainMenuInputListener
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import ktx.log.debug

class LevelSelectScreen(game: SpaceJourney) : BaseScreen(game, "LevelSelectScreen") {

  private var camera: OrthographicCamera? = null
  private var viewport: FitViewport? = null
  private var stage: Stage? = null

  private var screenData: Table? = null
  private var option1: Label? = null
  private var option2: Label? = null
  private var option3: Label? = null

  private var font: BitmapFont? = null

  private var inputListener: MainMenuInputListener? = null

  override fun show() {
    super.show()

    camera = OrthographicCamera()
    viewport = FitViewport(Constants.VIRTUAL_WIDTH*2,
            Constants.VIRTUAL_HEIGHT*2, camera)
    stage = Stage(viewport)

    screenData = Table()

    font = SpaceJourney.assetManager.get("fonts/default.fnt")
    font?.data?.scale(0.01f)
    option1 = Label(String.format(Strings.LEVEL_ONE, "  "), Label.LabelStyle(font, Color.WHITE))
    option2 = Label(String.format(Strings.LEVEL_TWO, "  "), Label.LabelStyle(font, Color.WHITE))
    option3 = Label(String.format(Strings.LEVEL_THREE, "  "), Label.LabelStyle(font, Color.WHITE))

    screenData?.add(Label(Strings.LEVEL_SELECT_TITLE, Label.LabelStyle(font, Color.WHITE)))?.padBottom(50.0f)?.expandX
    screenData?.row()
    screenData?.add(option1)?.expandX
    screenData?.row()
    screenData?.add(option2)?.expandX
    screenData?.row()
    screenData?.add(option3)?.expandX

    stage?.addActor(screenData)

    inputListener = MainMenuInputListener(3)
    Gdx.input.inputProcessor = inputListener
  }

  override fun render(delta: Float) {
    super.render(delta)

    viewport?.apply()
    batch?.projectionMatrix = camera?.combined

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

    if (inputListener!!.gameCanStart()) {
      GameDataManager.getInstance().levelNumber = inputListener!!.currentChoice
      this.game.setScreen<LevelScreen>()
    }
  }

  override fun resize(width: Int, height: Int) {
    super.resize(width, height)
    viewport?.update(width, height, true)
  }

  override fun dispose() {
    super.dispose()
    stage?.dispose()
    font?.dispose()
  }

}