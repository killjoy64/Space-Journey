package edu.gvsu.cis.spacejourney.util

import aurelienribon.tweenengine.TweenAccessor
import edu.gvsu.cis.spacejourney.component.Transform

/**
 * Tween accessor that allows interpolation of any transform component.
 */
class TransformAccessor : TweenAccessor<Transform> {

    /**
     * Inherited method that takes an entity's transform component, and returns
     * the new values that were interpolated.
     * @param target the starting transform component.
     * @param returnValues An array of values that the interpolation will return.
     * @return an integer value that represents the type of interpolation that was executed.
     */
    override fun getValues(target: Transform, tweenType: Int, returnValues: FloatArray): Int {
        return when (tweenType) {
            TYPE_POSITION -> {
                returnValues[0] = target.position.x
                returnValues[1] = target.position.y
                2
            }

            else -> {
                assert(false)
                -1
            }
        }
    }

    /**
     * Function that sets the values inside of a given transform component.
     * @param target the current parallax component of the entity.
     * @param tweenType the type of tween to execute on the component.
     * @param newValues an array of values to be populated with new values.
     */
    override fun setValues(target: Transform, tweenType: Int, newValues: FloatArray) {
        when (tweenType) {
            TYPE_POSITION -> {
                target.position.x = newValues[0]
                target.position.y = newValues[1]
            }
            else -> assert(false)
        }
    }

    /**
     * Companion object that holds the available interpolation types in this
     * accessor.
     */
    companion object {
        const val TYPE_POSITION = 5
    }
}