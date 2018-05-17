package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.PlayerModel;

public class PlayerController extends EntityController {

    public PlayerController(World world, PlayerModel playerModel)
    {
        super(world,playerModel, BodyDef.BodyType.DynamicBody);
        float density = 0.5f;
        float friction = 0.4f;
        float restitution = 0.5f;
        int width = 128;
        int height = 128;

        createFixture(body, new float[]{
                0.19875001907348633f*128,(1-0.8587499260902405f)*128,0.1912500560283661f*128,(1-0.12125003337860107f)*128,0.8512500524520874f*128,(1-0.11375004053115845f)*128,0.8287498950958252f*128,(1-0.9312499761581421f)*128
        }, width, height, density, friction, restitution, PLAYER_BITS, PLATFORM_BITS);


    }


}
