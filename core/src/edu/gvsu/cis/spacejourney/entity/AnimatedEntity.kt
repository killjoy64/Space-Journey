package edu.gvsu.cis.spacejourney.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.collections.GdxArray

/**
 * Abstract class that creates an entity that has animations.
 */
abstract class AnimatedEntity(stage: Stage?) : Entity(stage, null) {

    private var animation: Animation<TextureRegion>? = null
    private var elapsedTime = 0f

    /**
     * Function that adds an animation to the animation strip of an entity.
     */
    fun addAnimation(id: String, texture: Texture, start: Int, end: Int, cellWidth: Int, cellHeight: Int) {

        this.zIndex = ZIndex.COLLECTIBLE

        val regions = TextureRegion.split(texture, cellWidth, cellHeight)

        val animationFrames = GdxArray<TextureRegion>(end - start)

        for (i in start until end) {
            animationFrames.add(regions[0][i])
        }

        animation = Animation(1f / 16f, animationFrames)
    }

    /**
     * Method that updates every render() call.
     * @param delta The time difference betwen the last and current render() method call.
     */
    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += Gdx.graphics.deltaTime
    }

    /**
     * Draws the three parallax layers on to the screen.
     * @param batch The sprite batch used in the game.
     * @param parentAlpha Alpha value of the entire object.
     */
    override fun draw(batch: Batch?, parentAlpha: Float) {
        this.textureRegion = animation!!.getKeyFrame(elapsedTime, true)

        super.draw(batch, parentAlpha)
    }

    /**
     * Function that disposes of un-needed textures.
     */
    override fun dispose() {
    }
}