package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.entities.BoostModel;
import com.mygdx.game.model.entities.EntityModel;

import java.util.ArrayList;
import java.util.List;

public class NoCollisionsBoost extends BoostController {


    public NoCollisionsBoost(World world, BoostModel boostModel) {
        super(world, boostModel);
    }

    @Override
    public void jump(PlayerController player) {
        player.getBody().applyForceToCenter(0, 800, true);
    }

    @Override
    public void collisionHandler(PlayerController player) {
        Array<Fixture> fixtureList = player.body.getFixtureList();
        short maskBits = fixtureList.get(0).getFilterData().maskBits;
        maskBits &= ~(EntityController.OBSTACLE_BITS);
        for(Fixture fixture: fixtureList){
            Filter filter = fixture.getFilterData();
            filter.maskBits = maskBits;
            fixture.setFilterData(filter);
        }
    }
}
