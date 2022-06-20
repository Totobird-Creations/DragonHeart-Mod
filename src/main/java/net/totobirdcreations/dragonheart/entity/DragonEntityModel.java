package net.totobirdcreations.dragonheart.entity;

import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import software.bernie.geckolib3.model.AnimatedGeoModel;



public class DragonEntityModel extends AnimatedGeoModel<DragonEntity> {


    @Override
    public Identifier getModelLocation(DragonEntity object) {

        return new Identifier(DragonHeart.MOD_ID, "geo/entity/dragon.geo.json");

    }


    @Override
    public Identifier getTextureLocation(DragonEntity object) {

        return null;

    }


    @Override
    public Identifier getAnimationFileLocation(DragonEntity entity) {

        return new Identifier(DragonHeart.MOD_ID, "animations/entity/dragon.animation.json");

    }


}
