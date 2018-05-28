package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.entities.BoostModel;

public class FlyBoost extends BoostController {


    public FlyBoost(World world, BoostModel boostModel) {
        super(world, boostModel);
        //createFixture
    }

    @Override
    public void moveRight(PlayerController player) {
        player.getBody().applyForceToCenter(50, 0, true);
    }

    @Override
    public void moveLeft(PlayerController player) {
        player.getBody().applyForceToCenter(-50, 0, true);
    }

    @Override
    public void jump(PlayerController player) {
        player.getBody().applyForceToCenter(0, 200, true);
    }

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
