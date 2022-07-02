package net.totobirdcreations.dragonheart.entity.dragon.render;


import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.render.layer.DragonEntityEyesRenderer;
import net.totobirdcreations.dragonheart.entity.dragon.render.layer.DragonEntityNoColourRenderer;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;


public abstract class DragonEntityRenderer<T extends DragonEntity> extends GeoEntityRenderer<T> {

    public static Identifier MODEL            = new Identifier(DragonHeart.MOD_ID, "geo/entity/dragon.geo.json");
    public static Identifier ANIMATION        = new Identifier(DragonHeart.MOD_ID, "animations/entity/dragon.animation.json");

    public static Identifier TEXTURE          = new Identifier(DragonHeart.MOD_ID, "textures/entity/dragon/body.png");
    public static Identifier TEXTURE_EYES     = new Identifier(DragonHeart.MOD_ID, "textures/entity/dragon/eyes.png");
    public static Identifier TEXTURE_NOCOLOUR = new Identifier(DragonHeart.MOD_ID, "textures/entity/dragon/nocolour.png");


    public DragonEntityRenderer(EntityRendererFactory.Context renderManager, DragonEntityModel<T> model) {
        super(renderManager, model);
        this.addLayer(new DragonEntityEyesRenderer<>(this));
        this.addLayer(new DragonEntityNoColourRenderer<>(this));

    }


    @Override
    public Identifier getTexture(DragonEntity entity) {
        return TEXTURE;
    }


    @Override
    public Color getRenderColor(DragonEntity entity, float ticks, MatrixStack stack, VertexConsumerProvider buffer, VertexConsumer builder, int packedLight) {
        return (new RGBColour(entity.getDataTracker().get(DragonEntity.COLOUR))).toColor();
    }

    @Override
    public void render(T entity, float entityYaw, float ticks, MatrixStack stack, VertexConsumerProvider buffer, int packedLight) {
        float scale = entity.getScale();
        stack.scale(scale, scale, scale);
        super.render(entity, entityYaw, ticks, stack, buffer, packedLight);
    }

}
