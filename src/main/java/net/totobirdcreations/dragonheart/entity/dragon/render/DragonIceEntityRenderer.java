package net.totobirdcreations.dragonheart.entity.dragon.render;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.totobirdcreations.dragonheart.entity.dragon.DragonIceEntity;


public class DragonIceEntityRenderer extends DragonEntityRenderer<DragonIceEntity> {

    public DragonIceEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragonEntityModel<>());
    }

}
