package edu.gvsu.cis.spacejourney.entity;

import com.badlogic.gdx.physics.box2d.World;

public interface Collidable {

    void createBody(World world);

}
