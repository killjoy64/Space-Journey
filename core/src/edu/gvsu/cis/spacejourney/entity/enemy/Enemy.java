package edu.gvsu.cis.spacejourney.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.Entity;
import edu.gvsu.cis.spacejourney.entity.Graveyard;

public abstract class Enemy extends Entity {

    private int maxHitPoints;
    private int hitPoints;
    private ShapeRenderer shapeRenderer;

    public Enemy(Stage stage, TextureRegion textureRegion) {
        super(stage, textureRegion);
        this.shapeRenderer = new ShapeRenderer();
    }

    public void takeDamage() {
        if (hitPoints > 1) {
            hitPoints--;
        } else {
            hitPoints = 0;
            Graveyard.bodies.add(getBody());
            Graveyard.actors.add(this);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (maxHitPoints > 2 && hitPoints > 0) {
            batch.end();

            float x = getX() / Constants.PX_PER_M;
            float y = (getY() + getHeight()) / Constants.PX_PER_M;
            float w = getWidth() / Constants.PX_PER_M;
            float hpW = (w / maxHitPoints) * hitPoints;
            float h = 2.5f / Constants.PX_PER_M;

            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
            shapeRenderer.rect(x, y, w, h);
            shapeRenderer.setColor(0.0f, 1.0f, 0.0f, 1.0f);
            shapeRenderer.rect(x, y, hpW, h);
            shapeRenderer.end();

            batch.begin();
        }
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
        this.hitPoints = maxHitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        if (hitPoints > maxHitPoints) {
            this.hitPoints = maxHitPoints;
        } else {
            this.hitPoints = hitPoints;
        }
    }
}
