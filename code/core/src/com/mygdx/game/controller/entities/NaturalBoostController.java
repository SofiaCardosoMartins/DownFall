package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.entities.BoostModel;

/**
 * A class to represent the default boost of a player
 */
public class NaturalBoostController extends BoostController {
    /**
     * Constructor without arguments of the NaturalBoostController class
     * @see BoostController#BoostController()
     */
    public NaturalBoostController(){
        super();
    }

    /**
     * Constructor with arguments of the NaturalBoostController class. Sets the body's fixtures
     * @param world A Box2D world
     * @param boostModel Object of class BoostModel to be set as the body's user data
     * @see BoostController#BoostController(World, BoostModel)
     */
    public NaturalBoostController(World world, BoostModel boostModel) {
        super(world, boostModel);
    }

    /**
     * Moves the specified player jump
     * @param player The player in which the jump is applied
     */
    @Override
    public void jump(PlayerController player) {
        if (!(player.getBody().getLinearVelocity().y > 4))
            player.getBody().applyForceToCenter(0, UP_FORCE, true);
    }

    /**
     * Sets the mask bits of the specified player according to the type of boost
     * @param player The player in which the collisions are applied
     */
    @Override
    public void collisionHandler(PlayerController player) {
        Array<Fixture> fixtureList = player.body.getFixtureList();
        short maskBits = fixtureList.get(0).getFilterData().maskBits;
        maskBits |= (EntityController.OBSTACLE_BITS);
        for(Fixture fixture: fixtureList){
            Filter filter = fixture.getFilterData();
            filter.maskBits = maskBits;
            fixture.setFilterData(filter);
        }
    }
}
