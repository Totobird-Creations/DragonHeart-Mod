package net.totobirdcreations.dragonheart.entity.dragon.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;

import java.util.EnumSet;


public class DragonEntityPursueGoal extends Goal {

    public DragonEntity entity;
    public Path path;


    public DragonEntityPursueGoal(DragonEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }


    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }


    @Override
    public boolean canStart() {
        if (this.entity.getState() == DragonEntity.DragonState.PURSUE) {
            return true;
        } else if (this.entity.getState() == DragonEntity.DragonState.NEST) {
            LivingEntity target = this.entity.getTarget();
            if (this.entity.isValidTarget(target)) {
                this.path = this.entity.getNavigation().findPathTo(target, 0);
                if (this.path != null) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean shouldContinue() {
        LivingEntity target = entity.getTarget();
        if (this.entity.isValidTarget(target)) {
            if (this.entity.isInWalkTargetRange(target.getBlockPos())) {
                return true;
            }
        }
        return false;
    }

}
