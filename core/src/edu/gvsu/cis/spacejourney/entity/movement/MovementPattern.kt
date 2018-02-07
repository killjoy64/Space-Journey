package edu.gvsu.cis.spacejourney.entity.movement

import com.badlogic.gdx.math.Vector2

abstract class MovementPattern {

    // Returns a normalized 0..1 x, 0..1 y vector
    abstract fun getMovement(position : Vector2) : Vector2

}