package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component

/**
 * Component that defines unique attributes for a player component. This component is unique
 * in that there should only be one in the entire world.
 */
class Player : Component {
    var playerID = 1
    var firerate = 0.125f
    var movespeed = 50.0f
}