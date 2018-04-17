package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component

/**
 * Component that defines a parallax entity. A parallax component will have an offset,
 * and a speed.
 */
class Parallax : Component {
    var offset = 0.0f
    var speed = 0.0f
}