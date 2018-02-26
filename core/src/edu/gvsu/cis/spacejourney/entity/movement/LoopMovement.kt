package edu.gvsu.cis.spacejourney.entity.movement

import com.badlogic.gdx.math.Vector2

/**
 * Simple movement pattern that makes the enemy do a loop.
 */
class LoopMovement(val direction: Vector2) : MovementPattern() {

    /**
     * TODO - Not implemented yet.
     * @param position Not implemented yet.
     */
    override fun getMovement(position: Vector2): Vector2 {
        // TODO
        return Vector2(0f, 0f)
    }
}