package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.totobirdcreations.dragonheart.util.FrozenEffectEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public abstract class FrozenEffectEntityMixin implements FrozenEffectEntityInterface {

    private boolean iced;

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
        return ((LivingEntity)(Object)this).world.isClient() ? dataTracker.get(ICED) : this.iced;
    }

    public void setIced(Boolean value) {
        this.iced = value;
        DataTracker dataTracker = ((LivingEntity)(Object)this).getDataTracker();
        dataTracker.set(ICED, value);
    }

}
