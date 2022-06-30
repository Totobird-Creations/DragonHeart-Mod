package net.totobirdcreations.dragonheart.entity.dragon.render;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonIceEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonLightningEntity;
import software.bernie.geckolib3.core.util.Color;


public class DragonLightningEntityRenderer extends DragonEntityRenderer<DragonLightningEntity> {

    public DragonLightningEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragonEntityModel());
    }

}
