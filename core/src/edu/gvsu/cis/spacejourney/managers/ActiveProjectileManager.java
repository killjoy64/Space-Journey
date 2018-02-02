package edu.gvsu.cis.spacejourney.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import edu.gvsu.cis.spacejourney.entities.projectiles.Laser;

public class ActiveProjectileManager implements Disposable {

    private static ActiveProjectileManager instance;

    private Stage stage;
    private World world;
    private AssetManager assets;

    private Array<Laser> activeProjectiles;
    private Pool<Laser> projectilePool;

    public static ActiveProjectileManager getInstance() {
        if (instance == null) instance = new ActiveProjectileManager();
        return instance;
    }

    public void init() {
        this.activeProjectiles = new Array<Laser>();
        this.projectilePool = new Pool<Laser>() {
            @Override
            protected Laser newObject() {
                return new Laser(stage, world, assets);
            }
        };
    }

    public void spawnLaser(float x, float y) {
        Laser newLaser = this.projectilePool.obtain();
        newLaser.spawn(x, y);
        System.out.println(newLaser.getX() + " | " + newLaser.getY());
        activeProjectiles.add(newLaser);
        stage.addActor(newLaser);
    }

    public void poll() {
        for (int i = 0; i < activeProjectiles.size; i++) {
            Laser p = activeProjectiles.get(i);
            if (!p.isAlive()) {
                p.remove();
                activeProjectiles.removeIndex(i);
                projectilePool.free(p);
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setAssetManager(AssetManager assets) {
        this.assets = assets;
    }

    @Override
    public void dispose() {
        this.projectilePool.clear();
    }
}
