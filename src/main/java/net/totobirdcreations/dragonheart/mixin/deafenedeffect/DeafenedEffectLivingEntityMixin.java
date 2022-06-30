package net.totobirdcreations.dragonheart.mixin.deafenedeffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.util.effect.DeafenedEffectLivingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public abstract class DeafenedEffectLivingEntityMixin extends Entity implements DeafenedEffectLivingEntityInterface {

    public DeafenedEffectLivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private static final TrackedData<Boolean> DEAFENED;

    static {
        DEAFENED = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }


    @Inject(
            method = "initDataTracker",
            at = @At("HEAD")
    )
    public void initDataTracker(CallbackInfo callback) {
        DataTracker dataTracker = this.getDataTracker();
        dataTracker.startTracking(DEAFENED, false);
    }


    public boolean isDeafened() {
        DataTracker dataTracker = this.getDataTracker();
        return dataTracker.get(DEAFENED);
    }

    public void setDeafened(boolean value) {
        DataTracker dataTracker = this.getDataTracker();
        dataTracker.set(DEAFENED, value);
    }


    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("RETURN")
    )
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo callback) {
        DataTracker dataTracker = this.getDataTracker();
        nbt.putBoolean("Deafened", dataTracker.get(DEAFENED));
    }


    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("RETURN")
    )
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callback) {
        DataTracker dataTracker = this.getDataTracker();
        dataTracker.set(DEAFENED, nbt.getBoolean("Deafened"));
    }


}
