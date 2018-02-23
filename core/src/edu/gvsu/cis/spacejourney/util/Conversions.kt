package edu.gvsu.cis.spacejourney.util

import edu.gvsu.cis.spacejourney.Constants

/** Extension Method
 * Returns the float value converted from Box2D meters to Pixels
 */
fun Float.toPixels(): Float {
    return this * Constants.PX_PER_M
}

/** Extension Method
 * Returns the float value converted from Pixels to Box2D meters
 */
fun Float.toMeters(): Float {
    return this / Constants.PX_PER_M
}

/** Extension Method
 * Returns the float value converted from Box2D meters to Pixels
 */
fun Int.toPixels(): Float {
    return this * Constants.PX_PER_M
}

/** Extension Method
 * Returns the float value converted from Pixels to Box2D meters
 */
fun Int.toMeters(): Float {
    return this / Constants.PX_PER_M
}
