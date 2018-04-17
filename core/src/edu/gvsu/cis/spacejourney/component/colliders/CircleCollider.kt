package edu.gvsu.cis.spacejourney.component.colliders

import com.badlogic.ashley.core.Component

/**
 * Depends on `Transform` component. Defines a circle collision detection around an entity.
 */
class CircleCollider : Component {
    val radius = 1.0f
}