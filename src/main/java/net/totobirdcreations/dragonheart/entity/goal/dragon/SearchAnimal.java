package net.totobirdcreations.dragonheart.entity.goal.dragon;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.totobirdcreations.dragonheart.entity.DragonEntity;

import java.util.EnumSet;



public class SearchAnimal extends Goal {


    public DragonEntity entity;


    public SearchAnimal(DragonEntity entity) {

        this.entity = entity;
        this.setControls(EnumSet.of(Goal.Control.MOVE));

    }


    @Override
    public boolean canStart() {

        return entity.getWorld().isDay();

    }


    public boolean shouldContinue() {
        return entity.getNavigation().isFollowingPath();
    }


    public void start() {

        Vec3d random = NoPenaltyTargeting.find(entity, 8, 8);
        if (random != null) {
            entity.getNavigation().startMovingAlong(entity.getNavigation().findPathTo((new BlockPos(random)), 1), 0.3d);
        }

    }


}
