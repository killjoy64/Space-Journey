package edu.gvsu.cis.spacejourney.level.choreography.events;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.gvsu.cis.spacejourney.Constants;
import edu.gvsu.cis.spacejourney.entity.Entity;
import edu.gvsu.cis.spacejourney.level.choreography.ChoreographEvent;

import java.util.Random;

public class EntitySpawnEvent extends ChoreographEvent {

    private Entity entity;

    public EntitySpawnEvent(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void onEvent(Stage stage, World world) {
        Random random = new Random();

        float r = random.nextFloat();
        float x = (r * stage.getViewport().getWorldWidth() - (50f / Constants.PX_PER_M));
        float y = stage.getViewport().getWorldHeight();

        entity.setPosition(x, y);
        entity.createBody(world);
        stage.addActor(entity);
    }

    public Entity getEntity() {
        return entity;
    }
}
