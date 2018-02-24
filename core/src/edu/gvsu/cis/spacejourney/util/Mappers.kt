package edu.gvsu.cis.spacejourney.util

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Family
import edu.gvsu.cis.spacejourney.component.*
import edu.gvsu.cis.spacejourney.component.colliders.BoxCollider
import edu.gvsu.cis.spacejourney.component.colliders.CircleCollider


class Mappers {
    companion object {
        val transform = ComponentMapper.getFor(Transform::class.java)
        val velocity = ComponentMapper.getFor(Velocity::class.java)
        val box2D = ComponentMapper.getFor(Box2D::class.java)
        val boxCollider = ComponentMapper.getFor(BoxCollider::class.java)
        val circleCollider = ComponentMapper.getFor(CircleCollider::class.java)
        val staticSprite = ComponentMapper.getFor(StaticSprite::class.java)
    }
}

class Families {
    companion object {
        var players = Family.all(Player::class.java, Transform::class.java, Velocity::class.java).get()
    }
}