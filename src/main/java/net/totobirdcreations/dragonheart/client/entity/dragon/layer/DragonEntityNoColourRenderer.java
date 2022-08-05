package net.totobirdcreations.dragonheart.client.entity.dragon.layer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.client.entity.dragon.DragonEntityRenderer;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;


public class DragonEntityNoColourRenderer<T extends DragonEntity> extends GeoLayerRenderer<T> {

    public DragonEntityNoColourRenderer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }


    public Identifier getTextureResource() {
        return DragonEntityRenderer.TEXTURE_NOCOLOUR;
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        GeoModel       model          = getEntityModel().getModel(DragonEntityRenderer.MODEL);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderLayer.getEyes(this.getTextureResource()));

        this.getRenderer().render(
                model,
                dragon,
                partialTicks,
                RenderLayer.getEntityCutoutNoCull(getTextureResource()),
                matrixStack,
                buffer,
                vertexConsumer,
                packedLight,
                OverlayTexture.DEFAULT_UV,
                1.0f, 1.0f, 1.0f, 1.0f
        );
    }

}
