package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public abstract class Entity extends Actor implements Collidable, Disposable {

    private Body body;

    private Vector2 position;
    private TextureRegion texture;

    public Entity(TextureRegion textureRegion) {
        this.position = new Vector2(0.0f,0.0f);
        this.texture = textureRegion;
    }

    // This method should be overriden when there are multiple frames per entity.
    public TextureRegion getTextureFrame(float delta) {
        return this.texture;
    }

    // This method should be called when the texture given is an animation.
    public Array<TextureRegion> getAnimations(int start, int end, int y, int width, int height) {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        // If we're using a sprite sheet, load the animation sequence.
        for (int i = start; i < end; i++) {
            frames.add(new TextureRegion(this.texture, i * width, y, width, height));
        }

        return frames;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getPosition() {
        return position;
    }
}
