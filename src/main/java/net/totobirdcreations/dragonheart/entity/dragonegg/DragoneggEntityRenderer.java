package net.totobirdcreations.dragonheart.entity.dragonegg;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonEntityModel;
import net.totobirdcreations.dragonheart.entity.dragon.render.layer.DragonEntityEyesRenderer;
import net.totobirdcreations.dragonheart.entity.dragon.render.layer.DragonEntityNoColourRenderer;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class DragoneggEntityRenderer<T extends DragoneggEntity> extends GeoEntityRenderer<T> {

    public static Identifier MODEL            = new Identifier(DragonHeart.MOD_ID, "geo/entity/dragonegg.geo.json");
    public static Identifier ANIMATION        = new Identifier(DragonHeart.MOD_ID, "animations/entity/dragonegg.animation.json");

    public static Identifier TEXTURE          = new Identifier(DragonHeart.MOD_ID, "textures/entity/dragonegg.png");


    public DragoneggEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragoneggEntityModel<>());
    }


    @Override
    public Identifier getTexture(DragoneggEntity entity) {
        return TEXTURE;
    }


    @Override
    public Color getRenderColor(DragoneggEntity entity, float ticks, MatrixStack stack, VertexConsumerProvider buffer, VertexConsumer builder, int packedLight) {
        return (new RGBColour(entity.getDataTracker().get(DragoneggEntity.COLOUR))).toColor();
    }

}
