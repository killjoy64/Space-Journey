package edu.gvsu.cis.spacejourney.component

import com.badlogic.ashley.core.Component

/**
 * Depends on `Transform` and `BoxCollider` components. A projectile component has a damage
 * attribute.
 */
class Projectile : Component {
    var damage = 1
}