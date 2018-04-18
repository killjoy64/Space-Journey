

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.HeadlessApplication
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector2
import edu.gvsu.cis.spacejourney.component.Transform
import edu.gvsu.cis.spacejourney.component.Velocity
import edu.gvsu.cis.spacejourney.system.VelocitySystem
import ktx.ashley.add
import ktx.ashley.entity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock


class EntityComponentTest : ApplicationListener {

    override fun render() {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(p0: Int, p1: Int) {
    }

    override fun create() {
    }

    override fun dispose() {
    }

    @Test
    fun testVelocitySystem() {

        val conf = HeadlessApplicationConfiguration()

        HeadlessApplication(this, conf)

        // These lines are VERY important. They simulate a GL20 (2D) environment
        // without actually using GL20. Our tests cannot be completed without these.
        Gdx.gl = mock<GL20>(GL20::class.java)
        Gdx.gl20 = mock<GL20>(GL20::class.java)

        val engine = Engine()

        engine.addSystem(VelocitySystem())

        engine.add {
            entity {
                with<Velocity> {
                    value = Vector2(1.0f, 2.0f)
                }
                with<Transform> {
                    position = Vector2(0.0f, 0.0f)
                }
            }
        }

        engine.update(1.0f)

        val tm = ComponentMapper.getFor(Transform::class.java)
        val vm = ComponentMapper.getFor(Velocity::class.java)

        val entity = engine.getEntitiesFor(Family.all(Velocity::class.java, Transform::class.java).get()).first()

        assertEquals(tm.get(entity).position.x, 1.0f, 0.1f)
        assertEquals(tm.get(entity).position.y, 2.0f, 0.1f)

    }


}