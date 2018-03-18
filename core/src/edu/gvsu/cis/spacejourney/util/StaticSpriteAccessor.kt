package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.math.Vector2
import aurelienribon.tweenengine.TweenAccessor
import edu.gvsu.cis.spacejourney.component.StaticSprite

class StaticSpriteAccessor : TweenAccessor<StaticSprite> {

    override fun getValues(target: StaticSprite, tweenType: Int, returnValues: FloatArray): Int {
        return when (tweenType) {
            TYPE_SCALE -> {
                returnValues[0] = target.scale
                2
            }

            else -> {
                assert(false)
                -1
            }
        }
    }

    override fun setValues(target: StaticSprite, tweenType: Int, newValues: FloatArray) {
        when (tweenType) {
            TYPE_SCALE -> {
                target.scale = newValues[0]
            }
            else -> assert(false)
        }
    }

    companion object {
        const val TYPE_SCALE = 1
    }
}