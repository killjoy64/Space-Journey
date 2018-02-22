package edu.gvsu.cis.spacejourney.screens.backgrounds

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Disposable
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.util.ZIndex
import edu.gvsu.cis.spacejourney.util.toMeters
import java.util.*

class ParallaxLayer(
        val texture: Texture? = null,
        val scroll_factor: Float,
        val zindex: Int,
        val repeat: Boolean = true
) {
    var offset: Vector2 = Vector2(0f, 0f)
    var region: TextureRegion? = null

    init {

        this.region = TextureRegion(texture)

        if (repeat) {
            this.region?.setRegion(0, 0, Gdx.graphics.width, Gdx.graphics.height)
            this.texture?.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        } else {
            this.texture?.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge)
        }

        this.texture?.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
    }

    fun dispose() {
        this.texture?.dispose()
    }
}

class ParallaxBackground : Actor(), Disposable {

    var layers: Vector<ParallaxLayer> = Vector()

    init {
        layers.add(ParallaxLayer(SpaceJourney.assetManager.get("parallax_background_layer3.png", Texture::class.java), 0.0020f, ZIndex.PARALLAX_BACKGROUND_LAYER2))

        if (Gdx.app.type == Application.ApplicationType.Desktop) {
            layers.add(ParallaxLayer(SpaceJourney.assetManager.get("parallax_background_layer2.png", Texture::class.java), 0.0015f, ZIndex.PARALLAX_BACKGROUND_LAYER3, false))
        }

        layers.add(ParallaxLayer(SpaceJourney.assetManager.get("parallax_background_layer1.png", Texture::class.java), 0.05f, ZIndex.PARALLAX_BACKGROUND_LAYER2))
        layers.last().offset = Vector2(5.0f, 50.0f)

        layers.add(ParallaxLayer(SpaceJourney.assetManager.get("parallax_background_layer1.png", Texture::class.java), 0.15f, ZIndex.PARALLAX_BACKGROUND_LAYER1))

        this.zIndex = ZIndex.BACKGROUND
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        for (layer in this.layers) {

            if (layer.repeat) {
                layer?.region?.regionWidth = Gdx.graphics.width
                layer?.region?.regionHeight = Gdx.graphics.height
                batch?.draw(layer.region, layer.offset.x, layer.offset.y, stage?.viewport!!.worldWidth, stage?.viewport!!.worldHeight)
            } else {
                batch?.draw(layer.region, layer.offset.x, layer.offset.y, Gdx.graphics.width.toFloat().toMeters(), Gdx.graphics.height.toMeters())
            }
        }
    }

    override fun act(delta: Float) {
        super.act(delta)

        for (layer in this.layers) {
            layer.region?.scroll(0f, -layer.scroll_factor * Gdx.graphics.rawDeltaTime)
        }

    }

    override fun dispose() {
        for (layer in this.layers) {
            layer.dispose()
        }
    }

}