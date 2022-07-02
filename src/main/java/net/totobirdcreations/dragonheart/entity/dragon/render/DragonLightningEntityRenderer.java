package net.totobirdcreations.dragonheart.entity.dragon.render;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.totobirdcreations.dragonheart.entity.dragon.DragonLightningEntity;


public class DragonLightningEntityRenderer extends DragonEntityRenderer<DragonLightningEntity> {

    public DragonLightningEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragonEntityModel<>());
    }

}
