package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

/**
 * Depends on `Transform` component. A static sprite component defines attributes to be
 * able to effectively render an entity and texture on to the screen.
 */
class StaticSprite : Component {

    // The texture to draw
    var texture: Texture? = null

    // If size is null the renderer uses the pixel size of the image
    var size: Vector2? = null

    // Is the sprite repeating across it's size?
    var repeating = false

    // This is an integer to make sure we respect the Pixels so we get Pixel-Perfect-Rendering
    var scale: Float = 1.0f

    // The ZIndex to draw the sprite at
    var zindex = 1

    var color: Color? = null

    var transparency: Float = 1.0f
}