package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.DragonFireEntityRenderer;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.item.MiscItems;
import net.totobirdcreations.dragonheart.item.misc.Dragonbucket;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;
import net.totobirdcreations.dragonheart.screen.ModScreens;
import net.totobirdcreations.dragonheart.util.ModRegistries;

import java.util.UUID;


@Environment(EnvType.CLIENT)
public class DragonHeartClient implements ClientModInitializer {


    public static final EntityModelLayer DRAGONBREATH_FIRE = new EntityModelLayer(new Identifier(DragonHeart.MOD_ID, "dragonbreath_fire"), "main");
    public static final EntityModelLayer DRAGONBREATH_ICE  = new EntityModelLayer(new Identifier(DragonHeart.MOD_ID, "dragonbreath_ice"), "main");

    @Override
    public void onInitializeClient() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonscale)stack.getItem()).getColor(stack), MiscItems.DRAGONSCALE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonbucket)stack.getItem()).getColor(stack), MiscItems.DRAGONBUCKET_ICE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonbucket)stack.getItem()).getColor(stack), MiscItems.DRAGONBUCKET_FIRE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonbucket)stack.getItem()).getColor(stack), MiscItems.DRAGONBUCKET_LIGHTNING);

        EntityRendererRegistry.register(
                ModEntities.DRAGONBREATH_FIRE_TYPE,
                (context) -> {return new FlyingItemEntityRenderer(context);}
        );
        EntityRendererRegistry.register(
                ModEntities.DRAGONBREATH_ICE_TYPE,
                (context) -> {return new FlyingItemEntityRenderer(context);}
        );

        EntityRendererRegistry.register(ModEntities.DRAGON_FIRE, DragonFireEntityRenderer::new);

        ModScreens.register();
        ModRegistries.register();

    }

}
