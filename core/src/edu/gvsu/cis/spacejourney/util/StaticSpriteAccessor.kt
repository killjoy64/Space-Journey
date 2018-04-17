package edu.gvsu.cis.spacejourney.util

import aurelienribon.tweenengine.TweenAccessor
import com.badlogic.gdx.graphics.Color
import edu.gvsu.cis.spacejourney.component.StaticSprite

/**
 * Tween accessor that allows interpolation of any static sprite component.
 */
class StaticSpriteAccessor : TweenAccessor<StaticSprite> {

    /**
     * Inherited method that takes an entity's parallax component, and returns
     * the new values that were interpolated.
     * @param target the starting static sprite component.
     * @param returnValues An array of values that the interpolation will return.
     * @return an integer value that represents the type of interpolation that was executed.
     */
    override fun getValues(target: StaticSprite, tweenType: Int, returnValues: FloatArray): Int {
        return when (tweenType) {
            TYPE_SCALE -> {
                returnValues[0] = target.scale
                2
            }
            TYPE_ALPHA -> {
                returnValues[0] = target.transparency
                3
            }
            TYPE_COLOR -> {
                returnValues[0] = target.color?.r!!
                returnValues[1] = target.color?.g!!
                returnValues[2] = target.color?.b!!
                returnValues[3] = target.color?.a!!
                4
            }
            else -> {
                assert(false)
                -1
            }
        }
    }

    /**
     * Function that sets the values inside of a given static sprite component.
     * @param target the current parallax component of the entity.
     * @param tweenType the type of tween to execute on the component.
     * @param newValues an array of values to be populated with new values.
     */
    override fun setValues(target: StaticSprite, tweenType: Int, newValues: FloatArray) {
        when (tweenType) {
            TYPE_SCALE -> {
                target.scale = newValues[0]
            }
            TYPE_ALPHA -> {
                target.transparency = newValues[0]
            }
            TYPE_COLOR -> {
                target.color = Color(newValues[0], newValues[1], newValues[2], newValues[3])
            }
            else -> assert(false)
        }
    }

    /**
     * Companion object that holds the available interpolation types in this
     * accessor.
     */
    companion object {
        const val TYPE_SCALE = 1
        const val TYPE_ALPHA = 2
        const val TYPE_COLOR = 3
    }
}