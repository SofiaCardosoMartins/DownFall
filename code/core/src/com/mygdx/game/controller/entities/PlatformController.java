package com.mygdx.game.controller.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.entities.PlatformModel;

public class PlatformController extends EntityController {

    public PlatformController(World world, PlatformModel platformModel) {
        super(world, platformModel, BodyDef.BodyType.KinematicBody);
        float density = 0.5f;
        float friction = 0.4f;
        float restitution = 0.5f;
        int width = 200;
        int height = 200;

        createFixture(body, new float[]{
                0.08125001192092896f * 200, (1 - 0.5487500429153442f + 0.0037500858306884766f) * 200, 0.9262499213218689f * 200, (1 - 0.5487500429153442f + 0.0037500858306884766f) * 200, 0.9312499761581421f * 200, (1 - 0.3712500035762787f + 0.0037500858306884766f) * 200, 0.07875001430511475f * 200, (1 - 0.36625003814697266f + 0.0037500858306884766f) * 200
        }, width, height, density, friction, restitution, PLATFORM_BITS, PLAYER_BITS);
    }
}
