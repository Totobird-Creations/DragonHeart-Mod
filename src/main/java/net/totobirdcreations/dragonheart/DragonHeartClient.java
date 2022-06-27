package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.item.ItemStack;
import net.totobirdcreations.dragonheart.entity.*;
import net.totobirdcreations.dragonheart.item.MiscItems;
import net.totobirdcreations.dragonheart.item.misc.Dragonbucket;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;
import net.totobirdcreations.dragonheart.screen.ModScreens;
import net.totobirdcreations.dragonheart.util.ModRegistries;


@Environment(EnvType.CLIENT)
public class DragonHeartClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonscale)stack.getItem()).getColor(stack), MiscItems.DRAGONSCALE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_ICE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_FIRE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_LIGHTNING);

        EntityRendererRegistry.register(ModEntities.DRAGON_FIRE      , DragonFireEntityRenderer::new      );
        EntityRendererRegistry.register(ModEntities.DRAGON_ICE       , DragonIceEntityRenderer::new       );
        EntityRendererRegistry.register(ModEntities.DRAGON_LIGHTNING , DragonLightningEntityRenderer::new );

        ModScreens.register();
        ModRegistries.register();

    }


    public int getDragonBucketItemColour(ItemStack stack, int tintIndex) {
        Dragonbucket bucket = (Dragonbucket)stack.getItem();
        return tintIndex == 1 ? bucket.getColor(stack) : 16777215;
    }

}
