package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component

/**
 * Component that gives an entity a health bar. This component defines a health value, and
 * health max value.
 */
class Health : Component {
    var value: Int = 0
    var maxValue: Int = 0
}