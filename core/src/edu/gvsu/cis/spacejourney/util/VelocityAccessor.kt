package edu.gvsu.cis.spacejourney.util

import aurelienribon.tweenengine.TweenAccessor
import edu.gvsu.cis.spacejourney.component.Velocity

/**
 * Tween accessor that allows interpolation of any parallax component.
 */
class VelocityAccessor : TweenAccessor<Velocity> {

    /**
     * Inherited method that takes an entity's velocity component, and returns
     * the new values that were interpolated.
     * @param target the starting velocity component.
     * @param returnValues An array of values that the interpolation will return.
     * @return an integer value that represents the type of interpolation that was executed.
     */
    override fun getValues(target: Velocity, tweenType: Int, returnValues: FloatArray): Int {
        return when (tweenType) {
            TYPE_ANGULAR -> {
                returnValues[0] = target.angular
                2
            }

            else -> {
                assert(false)
                -1
            }
        }
    }

    /**
     * Function that sets the values inside of a given velocity component.
     * @param target the current parallax component of the entity.
     * @param tweenType the type of tween to execute on the component.
     * @param newValues an array of values to be populated with new values.
     */
    override fun setValues(target: Velocity, tweenType: Int, newValues: FloatArray) {
        when (tweenType) {
            TYPE_ANGULAR -> {
                target.angular = newValues[0]
            }
            else -> assert(false)
        }
    }

    /**
     * Companion object that holds the available interpolation types in this
     * accessor.
     */
    companion object {
        const val TYPE_ANGULAR = 2
    }
}