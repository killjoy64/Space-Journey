package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.math.Vector2
import aurelienribon.tweenengine.TweenAccessor
import edu.gvsu.cis.spacejourney.component.StaticSprite
import edu.gvsu.cis.spacejourney.component.Velocity

class VelocityAccessor : TweenAccessor<Velocity> {

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

    override fun setValues(target: Velocity, tweenType: Int, newValues: FloatArray) {
        when (tweenType) {
            TYPE_ANGULAR -> {
                target.angular = newValues[0]
            }
            else -> assert(false)
        }
    }

    companion object {
        const val TYPE_ANGULAR = 2
    }
}