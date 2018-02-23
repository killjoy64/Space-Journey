package edu.gvsu.cis.spacejourney.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class MusicManager implements Disposable {

    private static MusicManager instance;

    private Music currentMusic;

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public Music getMusic() {
        return currentMusic;
    }

    public void setMusic(Music music) {
        this.stop();
        this.currentMusic = music;
        this.currentMusic.play();
        this.currentMusic.setLooping(true);
    }

    public void stop() {
        if (this.currentMusic != null) {
            this.currentMusic.stop();
        }
    }

    @Override
    public void dispose() {
        this.currentMusic.dispose();
    }

}
