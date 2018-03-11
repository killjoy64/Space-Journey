package edu.gvsu.cis.spacejourney.level.one

import com.badlogic.ashley.core.Engine
import edu.gvsu.cis.spacejourney.level.Level
import edu.gvsu.cis.spacejourney.level.choreography.EnemySpawnEvent
import edu.gvsu.cis.spacejourney.level.choreography.LevelChoreographer
import edu.gvsu.cis.spacejourney.screens.backgrounds.ParallaxBackground

//private DefaultOverlay defaultHud;

class LevelOne : Level() {

    private val background: ParallaxBackground? = null
    //private PlayerSpaceship player;
    //private PlayerInputListener inputListener;
    //private Collectible testCollectible;

    private var choreographer: LevelChoreographer? = null

    override fun init(engine: Engine) {
        super.init(engine)

        /*background = new ParallaxBackground();
    background.setZIndex(ZIndex.BACKGROUND);
    stage.addActor(background);

    player = new PlayerSpaceship(stage);
    player.setPosition(1.5f, 0.0f);
    player.setWidth(50.0f);
    player.setHeight(50.0f);
    player.createBody(world);
    stage.addActor(player);
    setPlayer(player);

    defaultHud = new DefaultOverlay();
    setHud(defaultHud);

    inputListener = new PlayerInputListener(player);
    Gdx.input.setInputProcessor(inputListener);

    testCollectible = new TestCollectible(stage);
    testCollectible.createBody(world);
    stage.addActor(testCollectible);*/

        choreographer = LevelChoreographer(engine)

        for (i in 0..399) {
            choreographer!!.schedule(1.0f + i * 0.2f, EnemySpawnEvent())
        }
    }

    override fun update(delta: Float) {
        super.update(delta)
        //inputListener.poll(delta);
        choreographer!!.update(delta)
        //defaultHud.poll();
    }

    override fun dispose() {
        //player.dispose();
        //testCollectible.dispose();
        music!!.stop()
        music.dispose()
    }
}//setMusic(SpaceJourney.Companion.getAssetManager().get(
//    "Space Background Music.mp3", Music.class));
