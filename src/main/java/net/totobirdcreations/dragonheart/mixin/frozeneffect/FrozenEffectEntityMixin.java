package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.util.FrozenEffectEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public abstract class FrozenEffectEntityMixin implements FrozenEffectEntityInterface {

    @Shadow public abstract float getHealth();

    private static final TrackedData<Boolean> ICED;

    static {
        ICED = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }


    @Inject(method = "initDataTracker", at = @At("HEAD"))
    public void initDataTracker(CallbackInfo callback) {
        DataTracker dataTracker = ((LivingEntity)(Object)this).getDataTracker();
        dataTracker.startTracking(ICED, false);
    }


    public boolean isIced() {
        DataTracker dataTracker = ((LivingEntity)(Object)this).getDataTracker();
        return dataTracker.get(ICED);
    }

    public void setIced(Boolean value) {
        DataTracker dataTracker = ((LivingEntity)(Object)this).getDataTracker();
        dataTracker.set(ICED, value);
    }


    @Inject(method = "writeCustomDataToNbt", at = @At(value = "RETURN"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo callback) {
        DataTracker dataTracker = ((LivingEntity)(Object)this).getDataTracker();
        nbt.putBoolean("Iced", dataTracker.get(ICED));
    }


    @Inject(method = "readCustomDataFromNbt", at = @At(value = "RETURN"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callback) {
        DataTracker dataTracker = ((LivingEntity)(Object)this).getDataTracker();
        dataTracker.set(ICED, nbt.getBoolean("Iced"));
    }

}
