package edu.gvsu.cis.spacejourney.entity

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.collections.GdxArray

abstract class AnimatedEntity(stage: Stage?) : Entity(stage, null) {

    private var animation: Animation<TextureRegion>? = null
    private var elapsedTime = 0f

    fun addAnimation(id: String, texture: Texture, start: Int, end: Int, cellWidth: Int, cellHeight: Int) {

        this.zIndex = ZIndex.COLLECTIBLE

        val regions = TextureRegion.split(texture, cellWidth, cellHeight)

        val animationFrames = GdxArray<TextureRegion>(end - start)

        for (i in start until end) {
            animationFrames.add(regions[0][i])
        }

        animation = Animation(1f / 16f, animationFrames)
    }

    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += Gdx.graphics.deltaTime
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        this.textureRegion = animation!!.getKeyFrame(elapsedTime, true)

        super.draw(batch, parentAlpha)
    }

    override fun dispose() {
    }
}