package edu.gvsu.cis.spacejourney

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import ktx.actors.*

class Spaceship(val texture: Texture) : Actor() {

    var velocity = Vector2(0f, 0f)

    fun moveUp(amount : Float){
        velocity.y += amount
    }

    fun moveDown(amount : Float){
        velocity.y -= amount
    }

    fun moveLeft(amount : Float){
        velocity.x -= amount
    }

    fun moveRight(amount: Float){
        velocity.x += amount
    }

    override fun act(delta: Float) {
        super.act(delta)

        this.x += velocity.x * delta
        this.y += velocity.y * delta

        applyFriction(2f)
    }

    //private var texture : Texture? = null

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        batch?.draw(texture, x, y, 50f, 50f)
    }

    fun applyFriction(value : Float){

        if (velocity.x > 0f) {
            velocity.x -= value
        }
        else if (velocity.x < 0f) {
            velocity.x += value
        }

        if (velocity.y > 0f){
            velocity.y -= value
        }
        else if (velocity.y < 0f){
            velocity.y += value
        }

    }


}