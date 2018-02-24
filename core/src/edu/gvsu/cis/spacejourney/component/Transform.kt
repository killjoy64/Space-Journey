package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2


// ALWAYS IN PIXELS AND DEGREES!
class Transform : Component {
    var position = Vector2(0f, 0f)
    var rotation = 0.0f;
}