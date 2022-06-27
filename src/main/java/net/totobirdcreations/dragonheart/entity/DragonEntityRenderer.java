package net.totobirdcreations.dragonheart.entity;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.util.RGBColour;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public abstract class DragonEntityRenderer extends GeoEntityRenderer<DragonEntity> {


    public DragonEntityRenderer(EntityRendererFactory.Context renderManager, DragonEntityModel model) {
        super(renderManager, model);
    }


    @Override
    public Identifier getTexture(DragonEntity entity) {
        return DragonEntityModel.TEXTURE;
    }


    @Override
    public Color getRenderColor(DragonEntity entity, float ticks, MatrixStack stackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn) {
        return (new RGBColour(entity.getDataTracker().get(entity.COLOUR))).toColor();
    }


}
