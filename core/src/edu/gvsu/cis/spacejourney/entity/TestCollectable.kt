package edu.gvsu.cis.spacejourney.entity

import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import edu.gvsu.cis.spacejourney.SpaceJourney
import com.badlogic.gdx.math.Rectangle.tmp
import com.badlogic.gdx.graphics.g2d.TextureRegion
import edu.gvsu.cis.spacejourney.Constants
import edu.gvsu.cis.spacejourney.util.ZIndex
import ktx.collections.GdxArray
import ktx.log.debug


class TestCollectable(stage : Stage?) : AnimatedEntity(stage) {

    override fun createBody(world: World?) {}

    init {

        this.setSize(50f, 50f)
        this.setPosition(50f, 50f)

        this.addAnimation("idle", SpaceJourney.assetManager.get<Texture>("rotating_pickup.png"), 0, 23, 48, 48)

        //this.animate("idle")
        //this.setIdleAnimation("idle")


    }

}