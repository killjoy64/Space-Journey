package edu.gvsu.cis.spacejourney.entity.movement

import com.badlogic.gdx.math.Vector2

// Causes the object to follow a looping pattern
class LoopMovement(val direction: Vector2) : MovementPattern() {

    override fun getMovement(position: Vector2): Vector2 {
        // TODO
        return Vector2(0f, 0f)
    }

}