package net.totobirdcreations.dragonheart.entity.dragon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;


public class DragonPartEntity extends Entity {
    public final DragonEntity     owner;
    public final EntityDimensions partDimensions;
    public final float            damageMult;

    public DragonPartEntity(DragonEntity owner, EntityDimensions partDimensions, float damageMult) {
        super(owner.getType(), owner.world);
        this.owner          = owner;
        this.partDimensions = partDimensions;
        this.damageMult     = damageMult;
        this.calculateDimensions();
    }

    @Override
    public void initDataTracker() {}

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {}

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {}

    @Override
    public boolean collides() {return true;}

    @Override
    public boolean damage(DamageSource source, float amount) {
        return ! this.isInvulnerableTo(source) && this.owner.damage(source, amount * this.damageMult);
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    @Override
    public Packet<?> createSpawnPacket() {throw new UnsupportedOperationException();}

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return this.partDimensions.scaled(owner.getModelScale());
    }

    @Override
    public boolean shouldSave() {
        return false;
    }

}
