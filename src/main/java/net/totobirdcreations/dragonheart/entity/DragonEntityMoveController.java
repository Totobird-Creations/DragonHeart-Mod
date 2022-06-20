package net.totobirdcreations.dragonheart.entity;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class DragonEntityMoveController extends MoveControl {


    public final DragonEntity entity;
    public       int          collisionCheckCooldown;


    public DragonEntityMoveController(DragonEntity entity) {
        super(entity);
        this.entity = entity;
    }


    @Override
    public void tick() {
        if (this.state != MoveControl.State.MOVE_TO) {
            return;
        }
        if (this.collisionCheckCooldown-- <= 0) {
            this.collisionCheckCooldown += entity.getRandom().nextInt(5) + 2;
            Vec3d vec3d = new Vec3d(this.targetX - entity.getX(), this.targetY - entity.getY(), this.targetZ - entity.getZ());
            double d = vec3d.length();
            if (this.willCollide(vec3d = vec3d.normalize(), MathHelper.ceil(d))) {
                entity.setVelocity(entity.getVelocity().add(vec3d.multiply(0.1)));
            } else {
                this.state = MoveControl.State.WAIT;
            }
        }
    }


    private boolean willCollide(Vec3d direction, int steps) {
        Box box = entity.getBoundingBox();
        for (int i = 1; i < steps; ++i) {
            if (entity.world.isSpaceEmpty(entity, box = box.offset(direction))) continue;
            return false;
        }
        return true;
    }


}
