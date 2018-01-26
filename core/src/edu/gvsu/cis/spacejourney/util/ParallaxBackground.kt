package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Disposable
import ktx.app.use

class ParallaxBackground : Actor(), Disposable {

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        batch?.draw(region, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        this.zIndex = ZIndex.BACKGROUND
    }

    override fun act(delta: Float) {
        super.act(delta)

        region?.scroll(0f, -0.25f * delta)
    }

    var texture : Texture? = null
    var region : TextureRegion? = null

    init {
        this.texture = Texture(Gdx.files.internal("Parallax100_0.png"))
        this.texture?.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        this.region = TextureRegion(this.texture, this.texture?.width!!, this.texture?.height!!)
    }

    override fun dispose(){
        texture?.dispose()
    }

}