package edu.gvsu.cis.spacejourney.entity.collectible

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.graphics.Texture
import edu.gvsu.cis.spacejourney.SpaceJourney


class TestCollectible(stage: Stage?) : Collectible(stage) {

    init {

        this.setSize(50f, 50f)
        this.setPosition(1.0f, 0.5f)

        this.addAnimation("idle", SpaceJourney.assetManager.get<Texture>("rotating_pickup.png"), 0, 23, 48, 48)
    }

}