package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector

/*
 * Depends on `Transform`
 */
class AnimatedSprite : Component {
    var animations : HashMap<String, ArrayList<TextureRegion>> = hashMapOf()
}