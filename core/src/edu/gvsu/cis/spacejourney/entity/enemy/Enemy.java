package edu.gvsu.cis.spacejourney.entity.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.entity.Entity;

public abstract class Enemy extends Entity {

    public Enemy(Stage stage, TextureRegion textureRegion) {
        super(stage, textureRegion);
    }
}
