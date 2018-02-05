package edu.gvsu.cis.spacejourney.entity

import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import edu.gvsu.cis.spacejourney.SpaceJourney
import com.badlogic.gdx.math.Rectangle.tmp
import com.badlogic.gdx.graphics.g2d.TextureRegion
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.collections.GdxArray
import ktx.log.debug


class TestCollectable : Actor() {

    override fun act(delta: Float) {
        super.act(delta)
        elapsedTime += Gdx.graphics.deltaTime


        this.setSize(48f, 48f)
        this.setPosition(50f, 50f)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        val keyframe = animation!!.getKeyFrame(elapsedTime, true)

        batch?.draw(keyframe, x / Constants.PX_PER_M, y / Constants.PX_PER_M, width / Constants.PX_PER_M, height / Constants.PX_PER_M)
    }

    private var textureAtlas: TextureAtlas? = null
    private var animation: Animation<TextureRegion>? = null
    private var elapsedTime = 0f
    private var texture_ref : Texture? = null

    init {

        this.zIndex = ZIndex.COLLECTABLE

        texture_ref = SpaceJourney.assetManager.get<Texture>("rotating_pickup.png")

        val regions = TextureRegion.split(texture_ref, 48, 48);

        val animationFrames = GdxArray<TextureRegion>(24)

        for (i in 0 until 23) {
            animationFrames.add(regions[0][i])
        }

        animation = Animation(1f / 16f, animationFrames)
    }

    fun dispose() {
        textureAtlas!!.dispose()
    }
}