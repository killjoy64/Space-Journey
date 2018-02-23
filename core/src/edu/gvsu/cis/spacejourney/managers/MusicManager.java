package edu.gvsu.cis.spacejourney.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * Singleton class that stores and manages
 * universal music.
 */
public class MusicManager implements Disposable {

    private static MusicManager instance;

    private Music currentMusic;

    /**
     * Gets the current game data singleton being used.
     * @return {@link edu.gvsu.cis.spacejourney.managers.MusicManager} singleton.
     */
    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    /**
     * Gets the current music that is being played in the game.
     * @return a {@link com.badlogic.gdx.audio.Music} object.
     */
    public Music getMusic() {
        return currentMusic;
    }

    /**
     * Sets the current in-game music, stops the previous track, and
     * starts looping the music.
     * @param music a {@link com.badlogic.gdx.audio.Music} music object.
     */
    public void setMusic(Music music) {
        this.stop();
        this.currentMusic = music;
        this.currentMusic.play();
        this.currentMusic.setVolume(0.3f);
        this.currentMusic.setLooping(true);
    }

    /**
     * Stops the current music that is being played.
     */
    public void stop() {
        if (this.currentMusic != null) {
            this.currentMusic.stop();
        }
    }

    /**
     * From {@link com.badlogic.gdx.utils.Disposable}. It just disposes
     * the music object.
     */
    @Override
    public void dispose() {
        this.currentMusic.dispose();
    }

}
