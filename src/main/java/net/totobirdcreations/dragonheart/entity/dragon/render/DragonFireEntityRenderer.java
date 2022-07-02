package net.totobirdcreations.dragonheart.entity.dragon.render;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.totobirdcreations.dragonheart.entity.dragon.DragonFireEntity;


public class DragonFireEntityRenderer extends DragonEntityRenderer<DragonFireEntity> {

    public DragonFireEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragonEntityModel<>());
    }

}
