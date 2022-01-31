package net.totobirdcreations.dragonheart.entity.goal.dragon;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.totobirdcreations.dragonheart.entity.DragonEntity;

import java.util.EnumSet;



public class ToSpawn extends Goal {


    public DragonEntity entity;


    public ToSpawn(DragonEntity entity) {

        this.entity = entity;
        this.setControls(EnumSet.of(Goal.Control.MOVE));

    }


    @Override
    public boolean canStart() {

        return entity.getWorld().isNight();

    }


    public boolean shouldContinue() {
        return entity.getNavigation().isFollowingPath();
    }


    public void start() {

        entity.getNavigation().startMovingAlong(entity.getNavigation().findPathTo((new BlockPos(entity.getDataTracker().get(entity.SPAWN_POS))), 1), 0.3d);

    }


}
