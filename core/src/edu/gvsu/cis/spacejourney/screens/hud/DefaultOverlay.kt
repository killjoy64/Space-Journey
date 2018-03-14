package edu.gvsu.cis.spacejourney.screens.hud

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.Strings
import edu.gvsu.cis.spacejourney.component.Player
import edu.gvsu.cis.spacejourney.managers.GameDataManager
import edu.gvsu.cis.spacejourney.managers.GameDataManager.MAX_LIVES
import edu.gvsu.cis.spacejourney.util.JMP
import edu.gvsu.cis.spacejourney.util.Mappers

/**
 * Default head-up display (HUD) for the game play screen.
 */
class DefaultOverlay : Table() {

    private val lives: ArrayList<Image> = arrayListOf()
    private val gameData: GameDataManager

    private val font: BitmapFont

    private val scoreLabel: Label

    /**
     * Default constructor that initially maxes
     * out space on the screen, which is then updated by
     * the actual [edu.gvsu.cis.spacejourney.managers.GameDataManager] class.
     */
    init {
        this.gameData = GameDataManager.getInstance()

        font = BitmapFont(Gdx.files.internal("fonts/default.fnt"))
        font.data.scale(0.5f)

        this.scoreLabel = Label(String.format(Strings.HUD_SCORE, gameData.score), Label.LabelStyle(font, JMP.White))

        this.width = Gdx.graphics.width.toFloat()
        this.debug = true

        for (i in 0..MAX_LIVES - 1) {
            lives.add(Image(
                    SpaceJourney.assetManager.get("player_spaceship.png", Texture::class.java)))
            top().left().padLeft(32.0f).padTop(16.0f).add(lives[i]).size(32f * 3f, 32f * 3f).padRight(16.0f)
        }

        add(scoreLabel).align(Align.right).padTop(10f).padRight(32f).expandX()
    }

    /**
     * Polls for updates that need to be done to the HUD by
     * the [edu.gvsu.cis.spacejourney.managers.GameDataManager] class.
     */
    fun poll(engine : Engine) {

        val players = engine.getEntitiesFor(Family.all(Player::class.java).get())

        for (player in players){
            val health = Mappers.health.get(player)

            for (i in 0..health.maxValue - 1) {
                lives[i].isVisible = false
            }

            for (i in 0..health.value - 1) {
                lives[i].isVisible = true
            }
        }

        scoreLabel.setText(String.format(Strings.HUD_SCORE, gameData.score))
    }

}
