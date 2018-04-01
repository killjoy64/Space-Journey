package edu.gvsu.cis.spacejourney.util

import aurelienribon.tweenengine.TweenAccessor
import edu.gvsu.cis.spacejourney.component.Transform

class TransformAccessor : TweenAccessor<Transform> {

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

    override fun setValues(target: Transform, tweenType: Int, newValues: FloatArray) {
        when (tweenType) {
            TYPE_POSITION -> {
                target.position.x = newValues[0]
                target.position.y = newValues[1]
            }
            else -> assert(false)
        }
    }

    companion object {
        const val TYPE_POSITION = 5
    }
}