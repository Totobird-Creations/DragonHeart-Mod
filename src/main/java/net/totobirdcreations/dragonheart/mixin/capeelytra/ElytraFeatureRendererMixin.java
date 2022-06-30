package net.totobirdcreations.dragonheart.mixin.capeelytra;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.item.ArmourItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;


@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    private static Identifier                TEXTURE_ELYTRA             = new Identifier("textures/entity/elytra.png");
    private static Identifier                TEXTURE_ELYTRA_DRAGONSCALE = new Identifier("textures/entity/player/elytra/dragonscale.png");
    private static HashMap<Item, Identifier> RENDER_ALLOWED             = new HashMap();
    static {
        RENDER_ALLOWED.put( Items.ELYTRA                   , TEXTURE_ELYTRA_DRAGONSCALE );
        RENDER_ALLOWED.put( ArmourItems.ELYTRA_DRAGONSCALE , TEXTURE_ELYTRA             );
    }

    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }


    /*@Redirect(
            method = "<init>(Lnet/minecraft/client/render/entity/feature/FeatureRendererContext;Lnet/minecraft/client/render/entity/model/EntityModelLoader;)V",
            at = @At(
                    value = "NEW",
                    target = "Lnet/minecraft/client/render/entity/model/ElytraEntityModel;(Lnet/minecraft/client/model/ModelPart;)V"
            )
    )
    public void init() {}


    public boolean renderAllowed(ItemStack stack) {
        return RENDER_ALLOWED.keySet().contains(stack);
    }


    public Identifier renderTexture(T entity, ItemStack item) {
        if (entity instanceof AbstractClientPlayerEntity clientEntity) {
            if (clientEntity.canRenderElytraTexture() && clientEntity.getElytraTexture() != null) {
                return clientEntity.getElytraTexture();
            } else if (clientEntity.canRenderCapeTexture() && clientEntity.getCapeTexture() != null && clientEntity.isPartVisible(PlayerModelPart.CAPE)) {
                return clientEntity.getCapeTexture();
            }
        }
        return RENDER_ALLOWED.get(item);
    }


    public void renderFeature(MatrixStack stack, VertexConsumerProvider provider, int i, T entity, float f, float g, float j, float k, float l, ItemStack item, Identifier texture) {
        stack.push();
        stack.translate(0.0, 0.0, 0.125);
        this.getContextModel().copyStateTo(model);
        model.setAngles(entity, f, g, j, k, l);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(provider, RenderLayer.getArmorCutoutNoCull(texture), false, item.hasGlint());
        model.render(stack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        stack.pop();
    }


    @Inject(
            method = "render",
            at = @At("HEAD")
    )
    public void render(MatrixStack stack, VertexConsumerProvider provider, int i, T entity, float f, float g, float h, float j, float k, float l) {
        ItemStack item = entity.getEquippedStack(EquipmentSlot.CHEST);
        if (this.renderAllowed(item)) {
            Identifier texture = this.renderTexture(entity, item);
            renderFeature(stack, provider, i, entity, f, g, j, k, l, item, texture);
        }
    }*/

}
