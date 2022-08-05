package net.totobirdcreations.dragonheart.entity.dragon.ai;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.server.world.ServerWorld;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;

import java.util.UUID;


public class FindTargetGoal extends Goal {

    public DragonEntity entity;


    public FindTargetGoal(DragonEntity entity) {
        this.entity = entity;
    }


    @Override
    public boolean canStart() {
        return false;
    }


    /*@Override
    public boolean shouldContinue() {
        UUID target = entity.getTargetUuid();
        if (target != null) {
            ((ServerWorld) entity.world).getEntity(target);
            entity.world.getOtherEntities(this.entity);
            return livingEntity != null ? PhantomEntity.this.isTarget(livingEntity, TargetPredicate.DEFAULT) : false;
        }
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
    }*/

}
