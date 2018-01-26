package edu.gvsu.cis.spacejourney.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.Array
import edu.gvsu.cis.spacejourney.util.ParallaxBackground
import edu.gvsu.cis.spacejourney.SpaceJourney
import edu.gvsu.cis.spacejourney.entities.Direction
import edu.gvsu.cis.spacejourney.entities.SpaceshipEntity
import edu.gvsu.cis.spacejourney.entities.projectiles.Laser
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.viewport.ScreenViewport


/**
 * Where the magic happens
 */
class LevelScreen(game : SpaceJourney) : BaseScreen(game, "LevelScreen") {

    //private var camera: OrthographicCamera? = null
    private var viewport: ScreenViewport? = null

    private var stage : Stage? = null

    private var background : ParallaxBackground? = null

    private var spaceship: SpaceshipEntity? = null
    private var laser: Laser? = null

    private var activeLasers: Array<Laser>? = null
    private var laserPool: Pool<Laser>? = null

    override fun show() {
        super.show()

        viewport = ScreenViewport()
        viewport?.update(Gdx.graphics.width, Gdx.graphics.height)
        viewport?.unitsPerPixel = Gdx.graphics.density

        stage = Stage(viewport)

        spaceship = SpaceshipEntity(stage)
        laser = Laser(stage)

        spaceship?.setSize(50.0f, 50.0f)
        laser?.setSize(20.0f, 20.0f)

        stage?.addActor(spaceship)

        background = ParallaxBackground()
        stage?.addActor(background)

        activeLasers = Array()

        // Don't know how Kotlin lambdas work....
        laserPool = object : Pool<Laser>() {
            override fun newObject(): Laser {
                return Laser(stage)
            }
        }


    }

    // Be mindful about nullable-types, as resize is called before show
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)

        viewport?.update(width, height, true)

    }

    override fun render(delta: Float) {
        super.render(delta)

        viewport?.apply()

        val moveSpeed = 10.0f

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            spaceship?.move(Direction.UP, moveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            spaceship?.move(Direction.DOWN, moveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            spaceship?.move(Direction.LEFT, moveSpeed)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            spaceship?.move(Direction.RIGHT, moveSpeed)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            val newLaser: Laser = laserPool?.obtain()!!
            newLaser.spawn(spaceship?.x!! + (spaceship?.width!! / 2), spaceship?.y!! + spaceship?.height!!)
            activeLasers?.add(newLaser)
            stage?.addActor(newLaser)
        }

        for ((index) in activeLasers?.withIndex()!!) {
            val activeLaser: Laser = activeLasers!!.get(index)
            if (!activeLaser.isAlive) {
                activeLaser.remove()
                activeLasers?.removeIndex(index)
                laserPool?.free(activeLaser)
            }
        }



        batch?.projectionMatrix = viewport?.camera?.combined

        stage?.act()
        stage?.draw()
    }

    override fun dispose() {
        batch?.dispose()
        spaceship?.dispose()
        laserPool?.clear()
        stage?.dispose()
    }

}