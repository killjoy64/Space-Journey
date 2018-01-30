package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Disposable
import java.util.*

class ParallaxLayer (
    val texture : Texture? = null,
    val scroll_factor : Float,
    val zindex : Int)
{
    var region : TextureRegion? = null

    init {
        this.region = TextureRegion(texture)
        this.region?.setRegion(0, 0, this.texture!!.width, this.texture!!.height)

        this.texture?.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }

    fun dispose() {
        this.texture?.dispose()
    }
}

class ParallaxBackground(val assets : AssetManager) : Actor(), Disposable {

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        for (layer in this.layers) {
            batch?.draw(layer.region, 0f, 0f, this.stage.viewport.worldWidth, this.stage.viewport.worldHeight)
        }

        this.zIndex = ZIndex.BACKGROUND
    }

    override fun act(delta: Float) {
        super.act(delta)

        for (layer in this.layers) {
            layer.region?.scroll(0f, -layer.scroll_factor * Gdx.graphics.rawDeltaTime)
        }

    }

    var layers : Vector<ParallaxLayer> = Vector()

    var texture1 : Texture? = null
    var region1 : TextureRegion? = null

    init {
        layers.add(ParallaxLayer(assets.get("parallax_background_layer2.png", Texture::class.java), 0.0015f, ZIndex.PARALLAX_BACKGROUND_LAYER3))
        //layers.add(ParallaxLayer(assets.get("parallax_background_layer1.png", Texture::class.java), 0.05f, ZIndex.PARALLAX_BACKGROUND_LAYER2))
        layers.add(ParallaxLayer(assets.get("parallax_background_layer1.png", Texture::class.java), 0.15f, ZIndex.PARALLAX_BACKGROUND_LAYER1))
    }

    override fun dispose(){
        for (layer in this.layers) {
            layer.dispose()
        }
    }

}