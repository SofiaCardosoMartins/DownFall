package com.mygdx.game.controller.entities;

public class PlayerController extends EntityController {

    public PlayerController(World world, PlayerModel playerModel)
    {
        super(world,playerModel);
        float density = 0.5f;
        float friction = 0.4f;
        float restitution = 0.5f;
        int width = 75;
        int height = 75;
    }
}
