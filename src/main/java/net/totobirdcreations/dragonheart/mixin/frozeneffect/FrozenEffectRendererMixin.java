package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.util.FrozenEffectEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(LivingEntityRenderer.class)
public class FrozenEffectRendererMixin {


    @ModifyVariable(method = "getRenderLayer", at = @At("STORE"), ordinal = 0)
    public Identifier overrideIdentifier(Identifier object, LivingEntity entity) {
        if (((FrozenEffectEntityInterface)entity).isIced()) {
            return new Identifier("minecraft", "textures/block/packed_ice.png");
        } else {
            return ((LivingEntityRenderer)(Object)this).getTexture(entity);
        }
    }


}
