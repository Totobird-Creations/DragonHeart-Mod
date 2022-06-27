package net.totobirdcreations.dragonheart.entity;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.util.Color;


public class DragonIceEntityRenderer extends DragonEntityRenderer {


    public DragonIceEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragonEntityModel());
    }


    @Override
    public Identifier getTexture(DragonEntity entity) {
        return DragonEntityModel.TEXTURE;
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
