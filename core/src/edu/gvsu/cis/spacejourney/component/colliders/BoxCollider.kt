package edu.gvsu.cis.spacejourney.component.colliders

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

class BoxCollider : Component {
    var width = 50
    var height = 50
    var offset = Vector2(0f, 0f)
}