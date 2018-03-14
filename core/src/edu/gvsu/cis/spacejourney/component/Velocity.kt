package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class Velocity : Component {
    var value = Vector2()
    var angular = 0.0f
}