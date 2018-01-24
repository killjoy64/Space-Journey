package edu.gvsu.cis.spacejourney.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public abstract class Entity extends Actor {

    private Stage stage;
    private Texture spriteSheet;
    private TextureRegion currentSprite;

    private Vector2 velocity;

    public Entity(Stage stage, TextureRegion region) {
        this.stage = stage;
        this.currentSprite = region;
        this.spriteSheet = null;
        this.velocity = new Vector2(0.0f, 0.0f);
    }

    public Entity(Stage stage, Texture spriteSheet) {
        this.stage = stage;
        this.spriteSheet = spriteSheet;
        this.velocity = new Vector2(0.0f, 0.0f);
    }

    public abstract TextureRegion getFrame(float delta);

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.currentSprite, this.getX(), this.getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.setX(this.velocity.x * delta);
        this.setY(this.velocity.y * delta);

        TextureRegion newRegion = getFrame(delta);

        if (newRegion != null) {
            this.currentSprite.setRegion(getFrame(delta));
        }
    }

    public void setVelocity(float x, float y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public Array<TextureRegion> getAnimations(int start, int end, int y, int width, int height) {
        Array<TextureRegion> frames = new Array<TextureRegion>();

        // If we're using a single sprite entity, just return that sprite.
        if (this.spriteSheet == null) {
            frames.add(this.currentSprite);
            return frames;
        }

        // If we're using a sprite sheet, load the animation sequence.
        for (int i = start; i < end; i++) {
            frames.add(new TextureRegion(this.spriteSheet, i * width, y, width, height));
        }

        return frames;
    }

    public void dispose() {
        if (this.spriteSheet != null) {
            this.spriteSheet.dispose();
        }
    }

}
