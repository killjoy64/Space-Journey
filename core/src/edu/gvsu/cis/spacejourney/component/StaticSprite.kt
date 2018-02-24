package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture

class StaticSprite : Component {
    var texture : Texture? = null
    var zindex = 1.0;
}