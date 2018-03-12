package edu.gvsu.cis.spacejourney.component.gui

import com.badlogic.ashley.core.Component

abstract class GUIComponent : Component {
    var onClick : Function<Void>? = null
    var onHover : Function<Void>? = null
}