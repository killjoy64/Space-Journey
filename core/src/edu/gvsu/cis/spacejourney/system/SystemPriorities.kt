package edu.gvsu.cis.spacejourney.system


/***
 * All of our systems have an assigned priority by the engine.
 * Systems with a lower number have a higher priority to the system and are prioritized first
 * This helper class lets us assign and view all the priorities in one place
 */
class SystemPriorities {

    companion object {
        // User input is the most important
        val PlayerControllerSystem = 0

        // Not dropping frames is the second most important
        val RenderingSystem = 1

        // General updates are less important
        val ProjectileSystem = 2
        val CollisionSystem = 2
        val VelocitySystem = 2
    }

}