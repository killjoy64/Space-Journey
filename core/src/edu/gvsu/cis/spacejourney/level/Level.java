package edu.gvsu.cis.spacejourney.level;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public abstract class Level implements Disposable {

    protected Stage stage;
    protected World world;

    public void init(Stage stage, World world) {
        this.stage = stage;
        this.world = world;
    }

    public abstract void update(float delta);
}

