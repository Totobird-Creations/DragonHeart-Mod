package net.totobirdcreations.dragonheart;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.entity.DragonEntityRenderer;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.item.MiscItems;
import net.totobirdcreations.dragonheart.item.misc.Dragonbucket;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;
import net.totobirdcreations.dragonheart.screen.ModScreens;
import net.totobirdcreations.dragonheart.util.ModRegistries;


@Environment(EnvType.CLIENT)
public class DragonHeartClient implements ClientModInitializer {


    public static final EntityModelLayer DRAGONBREATH_FIRE = new EntityModelLayer(new Identifier(DragonHeart.MOD_ID, "dragonbreath_fire"), "main");
    public static final EntityModelLayer DRAGONBREATH_ICE  = new EntityModelLayer(new Identifier(DragonHeart.MOD_ID, "dragonbreath_ice"), "main");


    @Override
    public void onInitializeClient() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((Dragonscale)stack.getItem()).getColor(stack), MiscItems.DRAGONSCALE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_ICE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_FIRE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> getDragonBucketItemColour(stack, tintIndex), MiscItems.DRAGONBUCKET_LIGHTNING);

        EntityRendererRegistry.register(ModEntities.DRAGON_FIRE, DragonEntityRenderer::new);

        ModScreens.register();
        ModRegistries.register();

    }


    public int getDragonBucketItemColour(ItemStack stack, int tintIndex) {
        Dragonbucket bucket = (Dragonbucket)stack.getItem();
        return tintIndex == 1 ? bucket.getColor(stack) : 16777215;
    }

}
