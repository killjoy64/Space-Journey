package edu.gvsu.cis.spacejourney

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Disposable
import ktx.app.use

class ParallaxBackground : Disposable {

    var texture : Texture? = null
    var region : TextureRegion? = null

    init {
        this.texture = Texture(Gdx.files.internal("Parallax100_0.png"))
        this.texture?.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        this.region = TextureRegion(this.texture, this.texture?.width!!, this.texture?.height!!)
    }

    fun scroll(percent : Float){
        region?.scroll(0f, -percent)
    }

    fun draw(batch : SpriteBatch){

        batch.use {
            it.draw(region, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        }

    }

    override fun dispose(){
        texture?.dispose()
    }

}