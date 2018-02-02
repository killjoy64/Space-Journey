package edu.gvsu.cis.spacejourney.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import edu.gvsu.cis.spacejourney.util.ZIndex;

public abstract class Entity extends Actor implements Collidable {

    Stage stage;
    World world;

    private Texture spriteSheet;
    private TextureRegion currentSprite;

    private float width;
    private float height;
    private Vector2 velocity;

    public Entity(Stage stage, World world, TextureRegion region) {
        this.stage = stage;
        this.world = world;
        this.currentSprite = region;
        this.spriteSheet = null;
        this.velocity = new Vector2(0.0f, 0.0f);
        this.width = 10.0f;
        this.height = 10.0f;
        this.setZIndex(ZIndex.ENTITY);
    }

    public Entity(Stage stage, World world, Texture spriteSheet) {
        this.stage = stage;
        this.world = world;
        this.spriteSheet = spriteSheet;
        this.velocity = new Vector2(0.0f, 0.0f);
        this.width = 10.0f;
        this.height = 10.0f;
    }

    public abstract TextureRegion getFrame(float delta);

    @Override
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.currentSprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float oldX = this.getX();
        float oldY = this.getY();

        this.setX(oldX + (this.velocity.x * delta));
        this.setY(oldY + (this.velocity.y * delta));

        TextureRegion newRegion = getFrame(delta);

        if (newRegion != null) {
            this.currentSprite.setRegion(getFrame(delta));
        }
    }

    public TextureRegion getTextureRegion() {
        return currentSprite;
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

    public boolean outOfBounds() {
        int screenW = (int) this.stage.getViewport().getWorldWidth();
        int screenH = (int) this.stage.getViewport().getWorldHeight();
        int x = (int) getX();
        int y = (int) getY();

        return x > screenW || x < 0 || y > screenH || y < 0;
    }

    public boolean outOfBoundsX() {
        int screenW = (int) this.stage.getViewport().getWorldWidth();
        int x = (int) getX();

        return x > screenW || x < 0;
    }

    public boolean outOfBoundsY() {
        int screenH = (int) this.stage.getViewport().getWorldHeight();
        int y = (int) getY();

        return y > screenH || y < 0;
    }

    public void setVelocity(float x, float y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    public void setVelocityX(float x) {
        this.velocity.x = x;
    }

    public void setVelocityY(float y) {
        this.velocity.y = y;
    }

    public void dispose() {
        if (this.spriteSheet != null) {
            this.spriteSheet.dispose();
        }
    }

}
