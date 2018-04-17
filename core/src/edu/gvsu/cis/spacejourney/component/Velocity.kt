package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

/**
 * Component that defines how fast an entity should be moving. A velocity component has two attributes:
 * value (2d vector), and an angular velocity.
 */
class Velocity : Component {
    var value = Vector2()
    var angular = 0.0f
}