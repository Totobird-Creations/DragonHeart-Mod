package net.totobirdcreations.dragonheart.entity;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class DragonFireEntityRenderer extends GeoEntityRenderer<DragonEntity> {


    public DragonFireEntityRenderer(EntityRendererFactory.Context renderManager) {

        super(renderManager, new DragonFireEntityModel());

    }


    @Override
    public Identifier getTexture(DragonEntity entity) {

        return DragonFireEntityModel.TEXTURE;

    }



    @Override
    public Color getRenderColor(DragonEntity entity, float ticks, MatrixStack stackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn) {
        int colour = entity.getDataTracker().get(entity.COLOUR);
        return Color.ofRGB(
                ((colour >> 16) & 0xff) / 255.0f,
                ((colour >>  8) & 0xff) / 255.0f,
                ((colour      ) & 0xff) / 255.0f
        );
    }


}
