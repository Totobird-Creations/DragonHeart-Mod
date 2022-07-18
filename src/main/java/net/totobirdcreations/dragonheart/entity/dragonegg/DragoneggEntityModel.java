package net.totobirdcreations.dragonheart.entity.dragonegg;

import net.minecraft.particle.ShriekParticleEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonEntityRenderer;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;


// TODO : Do something about the warnings.
public class DragoneggEntityModel<T extends DragoneggEntity> extends AnimatedGeoModel<T> {


    @Override
    public Identifier getModelResource(DragoneggEntity entity) {
        return DragoneggEntityRenderer.MODEL;
    }
    @Override
    public Identifier getTextureResource(DragoneggEntity entity) {return DragoneggEntityRenderer.TEXTURE;}
    @Override
    public Identifier getAnimationResource(DragoneggEntity entity) {return DragoneggEntityRenderer.ANIMATION;}

}
