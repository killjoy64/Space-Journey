package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

/**
 * Component that can move an entity on the screen. A transform component has two attributes: a
 * position (2d vector), and rotation.
 */
class Transform : Component {
    var position = Vector2(0f, 0f)
    var rotation = 0.0f
}