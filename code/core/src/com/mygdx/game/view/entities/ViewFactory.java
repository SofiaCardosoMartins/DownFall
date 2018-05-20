package com.mygdx.game.view.entities;

import com.mygdx.game.DownFall;
import com.mygdx.game.model.entities.EntityModel;

import java.util.HashMap;
import java.util.Map;

public class ViewFactory {

    private static Map<EntityModel.ModelType, EntityView> cache =
            new HashMap<EntityModel.ModelType, EntityView>();

    public static EntityView makeView(DownFall game, EntityModel model) {
        if (!cache.containsKey(model.getType())) {
            if (model.getType() == EntityModel.ModelType.PLAYER)
                cache.put(model.getType(), new PlayerView(game));
            if (model.getType() == EntityModel.ModelType.OBSTACLE)
                cache.put(model.getType(), new ObstacleView(game));
            if (model.getType() == EntityModel.ModelType.BOOST)
                cache.put(model.getType(), new BoostView(game));
            if (model.getType() == EntityModel.ModelType.PLATFORM)
                cache.put(model.getType(), new PlatformView(game));
        }
        return cache.get(model.getType());
    }

    public static float getWidth(DownFall game, EntityModel model)
    {
        return ViewFactory.makeView(game, model).getSprite().getWidth();
    }

    public static float getHeigth(DownFall game, EntityModel model)
    {
        return ViewFactory.makeView(game, model).getSprite().getHeight();
    }
}
