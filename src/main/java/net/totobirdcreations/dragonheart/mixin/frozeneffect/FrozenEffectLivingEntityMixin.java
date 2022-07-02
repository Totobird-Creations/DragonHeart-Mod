package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.util.effect.FrozenEffectLivingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class FrozenEffectLivingEntityMixin extends Entity implements FrozenEffectLivingEntityInterface {
    @Shadow public abstract float getHeadYaw();

    @Shadow public abstract void setSprinting(boolean sprinting);

    @Shadow public abstract void setBodyYaw(float bodyYaw);

    @Shadow public float prevHeadYaw;

    @Shadow public float prevBodyYaw;

    @Shadow public abstract float getBodyYaw();


    public FrozenEffectLivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private static final TrackedData<Boolean> ICED;

    static {
        ICED = DataTracker.registerData(FrozenEffectLivingEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    public float   frozenHeadYaw = 0.0f;
    public float   frozenYaw     = 0.0f;
    public float   frozenBodyYaw = 0.0f;
    public float   frozenPitch   = 0.0f;


    @Inject(method = "initDataTracker", at = @At("HEAD"))
    public void initDataTracker(CallbackInfo callback) {
        DataTracker dataTracker = this.getDataTracker();
        dataTracker.startTracking(ICED, false);
    }


    public boolean isIced() {
        DataTracker dataTracker = this.getDataTracker();
        return dataTracker.get(ICED);
    }

    public void setIced(boolean value) {
        DataTracker dataTracker = this.getDataTracker();
        dataTracker.set(ICED, value);
    }


    @Inject(method = "writeCustomDataToNbt", at = @At(value = "RETURN"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo callback) {
        DataTracker dataTracker = this.getDataTracker();
        nbt.putBoolean("Iced", dataTracker.get(ICED));
    }


    @Inject(method = "readCustomDataFromNbt", at = @At(value = "RETURN"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callback) {
        DataTracker dataTracker = this.getDataTracker();
        dataTracker.set(ICED, nbt.getBoolean("Iced"));
    }


    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo callback) {
        if (isIced()) {
            setHeadYaw(frozenYaw);
            setYaw(frozenYaw);
            setBodyYaw(frozenYaw);
            prevHeadYaw = frozenHeadYaw;
            prevYaw     = frozenYaw;
            prevBodyYaw = frozenBodyYaw;
            setPitch(frozenPitch);
            prevPitch = frozenPitch;
            setSneaking(false);
            setSprinting(false);
        } else {
            frozenHeadYaw = getHeadYaw();
            frozenYaw     = getYaw();
            frozenBodyYaw = getBodyYaw();
            frozenPitch   = getPitch();
        }
    }


    @Inject(
            method = "isImmobile()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isImmobile(CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(true);
        }
    }


    @Inject(
            method = "tryAttack(Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void tryAttack(Entity target, CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(false);
        }
    }


    @Inject(
            method = "isUsingItem()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isUsingItem(CallbackInfoReturnable<Boolean> callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.setReturnValue(false);
        }
    }


    @Inject(
            method = "tickActiveItemStack()V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void tickActiveItemStack(CallbackInfo callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.cancel();
        }
    }


    @Inject(
            method = "tickItemStackUsage(Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void tickItemStackUsage(ItemStack stack, CallbackInfo callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.cancel();
        }
    }


    @Inject(
            method = "consumeItem()V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void consumeItem(CallbackInfo callback) {
        if (((FrozenEffectLivingEntityInterface)this).isIced()) {
            callback.cancel();
        }
    }


}
