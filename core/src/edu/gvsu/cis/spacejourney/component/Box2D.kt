package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body

class Box2D : Component {
    var body : Body? = null
}