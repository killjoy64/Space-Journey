package edu.gvsu.cis.spacejourney.entity.movement

import com.badlogic.gdx.math.Vector2

class LinearMovement(val direction : Vector2) : MovementPattern() {

    override fun getMovement(position : Vector2): Vector2 {
        return direction
    }

}