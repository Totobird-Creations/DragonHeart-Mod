package net.totobirdcreations.dragonheart.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonFireEntityRenderer;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonIceEntityRenderer;
import net.totobirdcreations.dragonheart.entity.dragon.render.DragonLightningEntityRenderer;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggEntityRenderer;
import net.totobirdcreations.dragonheart.item.misc.Dragonbucket;
import net.totobirdcreations.dragonheart.item.misc.Dragonegg;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;


public class ClientRenderers {

    public static void register() {
        registerEntities();
        registerItems();
    }


    public static void registerEntities() {
        EntityRendererRegistry.register(ModEntities.DRAGON_FIRE      , DragonFireEntityRenderer::new      );
        EntityRendererRegistry.register(ModEntities.DRAGON_ICE       , DragonIceEntityRenderer::new       );
        EntityRendererRegistry.register(ModEntities.DRAGON_LIGHTNING , DragonLightningEntityRenderer::new );

        EntityRendererRegistry.register(ModEntities.DRAGONEGG_FIRE      , DragoneggEntityRenderer::new );
        EntityRendererRegistry.register(ModEntities.DRAGONEGG_ICE       , DragoneggEntityRenderer::new );
        EntityRendererRegistry.register(ModEntities.DRAGONEGG_LIGHTNING , DragoneggEntityRenderer::new );
    }

    public static void registerItems() {
        ColorProviderRegistry.ITEM.register( ClientRenderers::getDragonscaleItemColour, MiscItems.DRAGONSCALE);

        ColorProviderRegistry.ITEM.register( ClientRenderers::getDragonbucketItemColour, MiscItems.DRAGONBUCKET_FIRE      );
        ColorProviderRegistry.ITEM.register( ClientRenderers::getDragonbucketItemColour, MiscItems.DRAGONBUCKET_ICE       );
        ColorProviderRegistry.ITEM.register( ClientRenderers::getDragonbucketItemColour, MiscItems.DRAGONBUCKET_LIGHTNING );

        ColorProviderRegistry.ITEM.register( ClientRenderers::getDragoneggItemColour, MiscItems.DRAGONEGG_FIRE      );
        ColorProviderRegistry.ITEM.register( ClientRenderers::getDragoneggItemColour, MiscItems.DRAGONEGG_ICE       );
        ColorProviderRegistry.ITEM.register( ClientRenderers::getDragoneggItemColour, MiscItems.DRAGONEGG_LIGHTNING );
    }



    public static int getDragonscaleItemColour(ItemStack stack, int tintIndex) {
        Dragonscale scale = (Dragonscale)stack.getItem();
        return scale.getColor(stack);
    }


    public static int getDragonbucketItemColour(ItemStack stack, int tintIndex) {
        Dragonbucket bucket = (Dragonbucket)stack.getItem();
        return tintIndex == 1 ? bucket.getColor(stack) : RGBColour.WHITE.asInt();
    }


    public static int getDragoneggItemColour(ItemStack stack, int tintIndex) {
        Dragonegg egg = (Dragonegg)(stack.getItem());
        return egg.getColor(stack);
    }

}
