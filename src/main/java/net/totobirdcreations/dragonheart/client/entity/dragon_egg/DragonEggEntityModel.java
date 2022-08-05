package net.totobirdcreations.dragonheart.client.entity.dragon_egg;

import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.dragon_egg.DragonEggEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;


// TODO : Do something about the warnings.
public class DragonEggEntityModel<T extends DragonEggEntity> extends AnimatedGeoModel<T> {


    @Override
    public Identifier getModelResource(DragonEggEntity entity) {
        return DragonEggEntityRenderer.MODEL;
    }
    @Override
    public Identifier getTextureResource(DragonEggEntity entity) {return DragonEggEntityRenderer.TEXTURE;}
    @Override
    public Identifier getAnimationResource(DragonEggEntity entity) {return DragonEggEntityRenderer.ANIMATION;}

}
