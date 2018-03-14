package edu.gvsu.cis.spacejourney.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class Bounds {
    companion object {
        fun isOutOfBounds(position : Vector2, size : Vector2, padding : Float) : Boolean {

            val bounds = Rectangle(
                    padding,
                    padding,
                    size.x - padding * 2f,
                    size.y - padding * 2f
            )

            if (position.x < bounds.x){
                return true
            }
            if (position.y < bounds.y){
                return true
            }
            if (position.x > bounds.x + bounds.width){
                return true
            }
            if (position.y > bounds.y + bounds.height){
                return true
            }

            return false
        }
    }
}