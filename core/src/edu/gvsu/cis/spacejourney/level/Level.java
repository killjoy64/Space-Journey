package edu.gvsu.cis.spacejourney.level;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

public abstract class Level implements Disposable {

    protected Stage stage;
    protected World world;

    private Music music;
    private Table hud;

    public void init(Stage stage, World world) {
        this.stage = stage;
        this.world = world;
    }

    public abstract void update(float delta);

    public void setMusic(Music music) {
        this.music = music;
    }

    public void setHud(Table hud) {
        this.hud = hud;
    }

    public Music getMusic() {
        return music;
    }

    public Table getHud() {
        return hud;
    }
}

