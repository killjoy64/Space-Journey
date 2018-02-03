package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.SpaceJourney;

public class PlayerSpaceship extends Entity {

    private final float moveSpeed = 1.5f;

    private EntityDirection direction;

    public PlayerSpaceship(Stage stage) {
        super(stage, new TextureRegion(SpaceJourney.Companion.getAssetManager().get("spaceship2.png", Texture.class)));
        direction = EntityDirection.IDLE;
    }

    public void move(EntityDirection direction) {
        this.direction = direction;

        if (canMove(direction)) {
            getBody().setLinearVelocity(moveSpeed * direction.getX(), moveSpeed * direction.getY());
        }
    }

    public boolean canMove(EntityDirection direction) {
        int screenW = (int) (getStage().getViewport().getWorldWidth() * Constants.PX_PER_M);
        int screenH = (int) (getStage().getViewport().getWorldHeight() * Constants.PX_PER_M);
        int x = (int) getX();
        int y = (int) getY();
        int w = (int) getWidth();
        int h = (int) getHeight();
        switch (direction) {
            case UP:
                if (y + h <= screenH) {
                    return true;
                }
                break;
            case DOWN:
                if (y > 0) {
                    return true;
                }
                break;
            case LEFT:
                if (x > 0) {
                    return true;
                }
                break;
            case RIGHT:
                if (x + w < screenW) {
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!canMove(direction)) {
            getBody().setLinearVelocity(0.0f, 0.0f);
        }

    }

    @Override
    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() + ((getWidth() / 2) / Constants.PX_PER_M),
                getY() + ((getHeight() / 2) / Constants.PX_PER_M));

        Body body = world.createBody(bodyDef);

        PolygonShape square = new PolygonShape();
        square.setAsBox((getWidth() / 2) / Constants.PX_PER_M, (getHeight() / 2) / Constants.PX_PER_M);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = square;
        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);

        setBody(body);

        square.dispose();
    }

    @Override
    public void dispose() {

    }
}
