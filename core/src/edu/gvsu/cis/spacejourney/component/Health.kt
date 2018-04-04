package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component

class Health : Component {
    var value: Int = 0
    var maxValue: Int = 0
}