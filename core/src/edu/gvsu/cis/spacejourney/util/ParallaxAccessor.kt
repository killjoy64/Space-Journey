package edu.gvsu.cis.spacejourney.util

import aurelienribon.tweenengine.TweenAccessor
import edu.gvsu.cis.spacejourney.component.Parallax

class ParallaxAccessor : TweenAccessor<Parallax> {

    override fun getValues(target: Parallax, tweenType: Int, returnValues: FloatArray): Int {
        return when (tweenType) {
            TYPE_PARALLAX -> {
                returnValues[0] = target.speed
                2
            }

            else -> {
                assert(false)
                -1
            }
        }
    }

    override fun setValues(target: Parallax, tweenType: Int, newValues: FloatArray) {
        when (tweenType) {
            TYPE_PARALLAX -> {
                target.speed = newValues[0]
            }
            else -> assert(false)
        }
    }

    companion object {
        const val TYPE_PARALLAX = 6
    }
}