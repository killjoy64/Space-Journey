package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Builder class that builds the body
 * from the ground up, including
 * fixtures, sensors, etc.
 */
public class BodyBuilder {

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Object userData;

    public BodyBuilder() {
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();

        // By default, we will leave this.
        this.bodyDef.type = BodyType.DynamicBody;
        this.fixtureDef.isSensor = true;

        this.userData = null;
    }

    public BodyBuilder setBodyType(BodyType bodyType) {
        this.bodyDef.type = bodyType;
        return this;
    }

    public BodyBuilder setBodyPosition(Vector2 position) {
        this.bodyDef.position.set(position);
        return this;
    }

    public BodyBuilder setBodyShape(Shape shape) {
        this.fixtureDef.shape = shape;
        return this;
    }

    public BodyBuilder setIsSensor(boolean sensor) {
        this.fixtureDef.isSensor = sensor;
        return this;
    }

    public BodyBuilder setUserData(Object o) {
        this.userData = o;
        return this;
    }

    public Body build(World world) {
        Body body = world.createBody(this.bodyDef);
        body.createFixture(this.fixtureDef);
        body.setUserData(this.userData);
        return body;
    }

}
