package net.totobirdcreations.dragonheart.entity;

import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;


public class DragonEntityModel extends AnimatedGeoModel<DragonEntity> {


    @Override
    public Identifier getModelResource(DragonEntity object) {
        return new Identifier(DragonHeart.MOD_ID, "geo/entity/dragon.geo.json");
    }


    @Override
    public Identifier getTextureResource(DragonEntity object) {
        return new Identifier(DragonHeart.MOD_ID, "textures/entity/dragon/fire.png");
    }


    @Override
    public Identifier getAnimationResource(DragonEntity entity) {
        return new Identifier(DragonHeart.MOD_ID, "animations/entity/dragon.animation.json");
    }

    @Override
    public void setLivingAnimations(DragonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        AnimationProcessor processor = this.getAnimationProcessor();
        IBone   body = processor.getBone("body0");
        IBone[] bones = {
                processor.getBone("neck0"),
                processor.getBone("neck1"),
                processor.getBone("neck2"),
                processor.getBone("head")
        };

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        float sourcePitch = body.getRotationX();
        float sourceYaw   = body.getRotationY();
        float targetPitch = extraData.headPitch;
        float targetYaw   = extraData.netHeadYaw;
        for (int i=0;i< bones.length ;i++) {
            IBone bone = bones[i];
            bone.setRotationX(
                    sourcePitch + (targetPitch - sourcePitch) * ((float)i / (float)bones.length)
                        * (float) Math.PI / 180.0f
            );
            bone.setRotationY(
                    sourceYaw + (targetYaw - sourceYaw) * ((float)i / (float)bones.length)
                            * (float) Math.PI / 180.0f
            );
        }
    }


}
