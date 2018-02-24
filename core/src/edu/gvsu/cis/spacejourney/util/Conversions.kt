package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.Constants
import ktx.math.div
import ktx.math.times

/// Extension Method
/// Returns the float value converted from Box2D meters to Pixels
fun Float.toPixels() : Float {
    return this * Constants.PX_PER_M
}

/// Extension Method
/// Returns the float value converted from Pixels to Box2D meters
fun Float.toMeters() : Float {
    return this / Constants.PX_PER_M
}


/// Extension Method
/// Returns the float value converted from Box2D meters to Pixels
fun Int.toPixels() : Float {
    return this * Constants.PX_PER_M
}

/// Extension Method
/// Returns the float value converted from Pixels to Box2D meters
fun Int.toMeters() : Float {
    return this / Constants.PX_PER_M
}

fun Vector2.toPixels(): Vector2 {
    return this * Constants.PX_PER_M
}

fun Vector2.toMeters(): Vector2 {
    return this / Constants.PX_PER_M
}
