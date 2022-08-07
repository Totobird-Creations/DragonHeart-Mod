package net.totobirdcreations.dragonheart.client.entity.dragon.layer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.client.entity.dragon.DragonEntityRenderer;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;


public class DragonEntityEyesRenderer<T extends DragonEntity> extends GeoLayerRenderer<T> {

    public DragonEntityEyesRenderer(IGeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }


    public Identifier getTextureResource() {
        return DragonEntityRenderer.TEXTURE_EYES;
    }


    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider buffer, int packedLight, T dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (dragon.areEyesOpen() && dragon.blinkTicks <= 0) {
            GeoModel       model          = getEntityModel().getModel(DragonEntityRenderer.MODEL);
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderLayer.getEyes(this.getTextureResource()));
            RGBColour      colour         = new RGBColour(dragon.getDataTracker().get(DragonEntity.EYE_COLOUR));

            RenderLayer[] renderLayers = {
                    RenderLayer.getEntityTranslucent(getTextureResource()),
                    RenderLayer.getEyes(getTextureResource())
            };
            for (RenderLayer renderLayer : renderLayers) {
                this.getRenderer().render(
                        model,
                        dragon,
                        partialTicks,
                        renderLayer,
                        matrixStack,
                        buffer,
                        vertexConsumer,
                        15728640,
                        OverlayTexture.DEFAULT_UV,
                        colour.r,
                        colour.g,
                        colour.b,
                        1.0f
                );
            }
        }
    }

}
