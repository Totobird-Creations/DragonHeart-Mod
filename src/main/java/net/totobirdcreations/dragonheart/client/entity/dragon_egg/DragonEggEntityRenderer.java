package net.totobirdcreations.dragonheart.client.entity.dragon_egg;


import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon_egg.DragonEggEntity;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public class DragonEggEntityRenderer<T extends DragonEggEntity> extends GeoEntityRenderer<T> {

    public static Identifier MODEL            = new Identifier(DragonHeart.ID, "geo/entity/dragon_egg.geo.json");
    public static Identifier ANIMATION        = new Identifier(DragonHeart.ID, "animations/entity/dragon_egg.animation.json");

    public static Identifier TEXTURE          = new Identifier(DragonHeart.ID, "textures/entity/dragon_egg.png");


    public DragonEggEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DragonEggEntityModel<>());
    }


    @Override
    public Identifier getTexture(DragonEggEntity entity) {
        return TEXTURE;
    }


    @Override
    public Color getRenderColor(DragonEggEntity entity, float ticks, MatrixStack stack, VertexConsumerProvider buffer, VertexConsumer builder, int packedLight) {
        return (new RGBColour(entity.getDataTracker().get(DragonEggEntity.COLOUR))).toColor();
    }

}
