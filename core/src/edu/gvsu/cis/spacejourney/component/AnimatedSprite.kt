package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * Depends on `Transform` component. Defines a component that may have animations.
 */
class AnimatedSprite : Component {
    var animations: HashMap<String, ArrayList<TextureRegion>> = hashMapOf()
}