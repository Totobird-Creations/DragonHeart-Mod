package net.totobirdcreations.dragonheart.entity;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.DragonHeartClient;



public class DragonFireEntityRenderer extends MobEntityRenderer<DragonFireEntity, DragonFireEntityModel> {


    public DragonFireEntityRenderer(EntityRendererFactory.Context context) {

        super(context, new DragonFireEntityModel(context.getPart(DragonHeartClient.DRAGON_FIRE)), 0.5f);

    }


    @Override
    public Identifier getTexture(DragonFireEntity entity) {

        return new Identifier(DragonHeart.MOD_ID, "textures/entity/dragon/fire.png");

    }


}
