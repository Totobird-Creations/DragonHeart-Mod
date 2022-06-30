package net.totobirdcreations.dragonheart.entity.dragon.render;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonFireEntity;
import software.bernie.geckolib3.core.util.Color;


public class DragonFireEntityRenderer extends DragonEntityRenderer<DragonFireEntity> {

    public DragonFireEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragonEntityModel());
    }

}
