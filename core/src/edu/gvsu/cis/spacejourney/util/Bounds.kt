package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * Utility class that checks the (x, y) coordinates of two given rectangles, and
 * performs operations on them. This class is beneficial for basic physics and
 * collision detection.
 */
class Bounds {

    /**
     * Class companion object that allows the isOutOfBounds function to be statically
     * referenced.
     */
    companion object {

        /**
         * Static function that returns whether or not a given (x, y) coordinate is out
         * of bounds on the screen.
         * @param position The current position of the entity.
         * @param size The size of the entity.
         * @param padding How much padding there should be in the entity. A larger padding will
         * allow the entity to appear more off-screen.
         * @return true if the entity is out of bounds, and false if the entity is not.
         */
        fun isOutOfBounds(position: Vector2, size: Vector2, padding: Float): Boolean {

            val bounds = Rectangle(
                    padding,
                    padding,
                    size.x - padding * 2f,
                    size.y - padding * 2f
            )

            if (position.x < bounds.x) {
                return true
            }
            if (position.y < bounds.y) {
                return true
            }
            if (position.x > bounds.x + bounds.width) {
                return true
            }
            if (position.y > bounds.y + bounds.height) {
                return true
            }

            return false
        }
    }
}