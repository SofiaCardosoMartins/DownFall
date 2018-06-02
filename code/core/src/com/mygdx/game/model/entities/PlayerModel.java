package com.mygdx.game.model.entities;


import com.mygdx.game.controller.GameController;

import static com.mygdx.game.controller.GameController.WORLD_HEIGHT;

public class PlayerModel extends EntityModel {

    int num;
    double remainingTime;
    boolean boostPresent;

    public PlayerModel(float x, float y, float rotation, int num)
    {
        super(x,y,rotation);
        this.num = num;
        this.remainingTime = 0;
        this.boostPresent = false;
    }

    @Override
    public ModelType getType() {
        switch (num)
        {
            case 1:
                return ModelType.PLAYER1;
            case 2:
                return ModelType.PLAYER2;
        }
        return null;
    }

    public int getNum() {
        return num;
    }

    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public boolean isBoostPresent() {
        return boostPresent;
    }

    public void setBoostPresent(boolean boostPresent) {
        this.boostPresent = boostPresent;
    }

    public void setPosition(float x, float y)
    {
        super.setPosition(x,y);
        if(y == WORLD_HEIGHT)
            GameController.getInstance().endGame(false);
    }
}
