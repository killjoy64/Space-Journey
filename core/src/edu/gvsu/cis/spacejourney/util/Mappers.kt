package edu.gvsu.cis.spacejourney.util

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Family
import edu.gvsu.cis.spacejourney.component.*
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.component.colliders.CircleCollider

/**
 * Utility class that holds a map for components. This converts an ashley
 * component to it's original class.
 */
class Mappers {

    /**
     * Companion object that holds the component maps for all possible components
     * that are made throughout the game.
     */
    companion object {
        val transform = ComponentMapper.getFor(Transform::class.java)
        val velocity = ComponentMapper.getFor(Velocity::class.java)
        val boxCollider = ComponentMapper.getFor(BoxCollider::class.java)
        val circleCollider = ComponentMapper.getFor(CircleCollider::class.java)
        val staticSprite = ComponentMapper.getFor(StaticSprite::class.java)
        val player = ComponentMapper.getFor(Player::class.java)
        val projectile = ComponentMapper.getFor(Projectile::class.java)
        val enemy = ComponentMapper.getFor(Enemy::class.java)
        val parallax = ComponentMapper.getFor(Parallax::class.java)
        val health = ComponentMapper.getFor(Health::class.java)
    }
}

/**
 * Utility class that is used to classify multiple ashley entities.
 */
class Families {

    /**
     * Companion object that holds the families for all possible entity types
     * throughout the game.
     */
    companion object {
        var players = Family.all(Player::class.java, Transform::class.java, Velocity::class.java).get()
    }
}