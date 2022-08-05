package net.totobirdcreations.dragonheart.client.entity.dragon;

import net.minecraft.particle.ShriekParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;


// TODO : Do something about the warnings.
public class DragonEntityModel<T extends DragonEntity> extends AnimatedGeoModel<T> {


    @Override
    public Identifier getModelResource(DragonEntity entity) {
        return DragonEntityRenderer.MODEL;
    }
    @Override
    public Identifier getTextureResource(DragonEntity entity) {return DragonEntityRenderer.TEXTURE;}
    @Override
    public Identifier getAnimationResource(DragonEntity entity) {return DragonEntityRenderer.ANIMATION;}


    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        AnimationProcessor       processor = this.getAnimationProcessor();
        DragonEntity.DragonState state     = entity.getState();

        if (
                state != DragonEntity.DragonState.SLEEP &&
                state != DragonEntity.DragonState.ROAR
        ) {
            IBone body = processor.getBone("body0");
            IBone[] bones = {
                    processor.getBone("neck0"),
                    processor.getBone("neck1"),
                    processor.getBone("neck2"),
                    processor.getBone("head")
            };

            EntityModelData extraData = (EntityModelData)(customPredicate.getExtraDataOfType(EntityModelData.class).get(0));
            float sourcePitch = body.getRotationX();
            float sourceYaw = body.getRotationY();
            float targetPitch = extraData.headPitch;
            float targetYaw = extraData.netHeadYaw;
            for (int i = 0; i < bones.length; i++) {
                IBone bone = bones[i];
                bone.setRotationX(
                        sourcePitch + (targetPitch - sourcePitch) * ((float) i / (float) bones.length)
                                * (float) Math.PI / 180.0f
                );
                bone.setRotationY(
                        sourceYaw + (targetYaw - sourceYaw) * ((float) i / (float) bones.length)
                                * (float) Math.PI / 180.0f
                );
            }
        }

        if (state == DragonEntity.DragonState.ROAR) {
            float yaw     = entity.getHeadYaw();
            Vec3d forward = new Vec3d(Math.sin(yaw), 0.0f, Math.cos(yaw)).multiply(4.0f);
            entity.world.addParticle(
                    new ShriekParticleEffect(0),
                    entity.getX() + forward.x, entity.getY() + 0.75f, entity.getZ() + forward.z,
                    0.0f, 0.0f, 0.0f
            );
        }
    }


}
