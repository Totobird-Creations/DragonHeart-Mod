package net.totobirdcreations.dragonheart.mixin.frozeneffect;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.util.mixin.frozeneffect.FrozenEffectLivingEntityMixinInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(LivingEntityRenderer.class)
public abstract class FrozenEffectRendererMixin extends EntityRenderer<LivingEntity> {
    public FrozenEffectRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    // TODO : Suppress these invalid errors.
    @ModifyVariable(
            method = "getRenderLayer",
            at = @At("STORE"),
            ordinal = 0
    )
    public Identifier overrideIdentifier(Identifier object, LivingEntity entity) {
        if (((FrozenEffectLivingEntityMixinInterface)entity).isIced()) {
            return new Identifier(DragonHeart.ID, "textures/misc/frozen_entity.png");
        } else {
            return this.getTexture(entity);
        }
    }


}
