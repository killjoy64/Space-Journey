package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import edu.gvsu.cis.spacejourney.Constants;

public abstract class Entity extends Actor implements Collidable, Disposable {

    private Body body;
    private Stage stage;
    private TextureRegion texture;
    private World world;

    public Entity(Stage stage, TextureRegion textureRegion) {
        this.stage = stage;
        this.texture = textureRegion;
        this.setPosition(0.0f, 0.0f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (body != null) {
            setPosition((body.getPosition().x * Constants.PX_PER_M) - (getWidth() / 2),
                    (body.getPosition().y * Constants.PX_PER_M) - (getHeight() / 2));
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(this.texture, getX() / Constants.PX_PER_M, getY() / Constants.PX_PER_M, getWidth() / Constants.PX_PER_M, getHeight() / Constants.PX_PER_M);
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

    public boolean outOfBounds() {
        int screenW = (int) (this.stage.getViewport().getWorldWidth() * Constants.PX_PER_M);
        int screenH = (int) (this.stage.getViewport().getWorldHeight() * Constants.PX_PER_M);
        int x = (int) getX();
        int y = (int) getY();
        int w = (int) getWidth();
        int h = (int) getHeight();

        return (x + w) > screenW || x < 0 || (y + h) > screenH || y < 0;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public TextureRegion getTexture() {
        return texture;
    }

}
