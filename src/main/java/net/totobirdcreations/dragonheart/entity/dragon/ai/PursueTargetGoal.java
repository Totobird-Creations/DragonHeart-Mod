package net.totobirdcreations.dragonheart.entity.dragon.ai;

import net.minecraft.entity.ai.goal.Goal;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;


public class PursueTargetGoal extends Goal {

    public DragonEntity entity;


    public PursueTargetGoal(DragonEntity entity) {
        this.entity = entity;
    }


    @Override
    public boolean canStart() {
        return false;
    }


    @Override
    public void start() {

    }


    @Override
    public void stop() {

    }


    @Override
    public void tick() {

    }


    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

}
