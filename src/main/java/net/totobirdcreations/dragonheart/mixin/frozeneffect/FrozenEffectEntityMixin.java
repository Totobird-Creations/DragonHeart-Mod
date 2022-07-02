package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.totobirdcreations.dragonheart.util.effect.FrozenEffectLivingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Entity.class)
public abstract class FrozenEffectEntityMixin {

    public boolean frozenSneak = false;



    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    public void tick(CallbackInfo callback) {
        // TODO : Do something about this warning.
        if (((Entity)(Object)this) instanceof LivingEntity) {
            if (! ((FrozenEffectLivingEntityInterface) this).isIced()) {
                Entity entity = ((Entity)(Object)this);
                frozenSneak   = entity.isSneaking();
            }
        }
    }

    // TODO : Fix this.
    /*@Inject(
            method = "isSneaking()Z",
            at = @At("HEAD"),
            cancellable = true
    )
    public void isSneaking(CallbackInfoReturnable<Boolean> callback) {
        if (((Object)this) instanceof LivingEntity) {
            if (! ((FrozenEffectLivingEntityInterface) this).isIced()) {
                callback.setReturnValue(frozenSneak);
            }
        }
    }*/


}
