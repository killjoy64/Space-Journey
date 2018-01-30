package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Disposable
import edu.gvsu.cis.spacejourney.Constants
import ktx.app.use

class ParallaxBackground : Actor(), Disposable {

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        batch?.draw(region, 0f, 0f, Constants.VIRTUAL_WIDTH / Constants.PX_PER_M,
                Constants.VIRTUAL_HEIGHT / Constants.PX_PER_M)

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