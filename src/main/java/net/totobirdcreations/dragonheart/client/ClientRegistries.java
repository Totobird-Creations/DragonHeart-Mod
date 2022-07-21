package net.totobirdcreations.dragonheart.client;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.DragonFireEntity;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonIceEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonLightningEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggFireEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggIceEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggLightningEntity;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;


public class ClientRegistries {

    @SuppressWarnings("all")
    public static void registerEntityAttributes() {
        DragonHeart.LOGGER.info("Registering entity attributes.");
        FabricDefaultAttributeRegistry.register(ModEntities.DRAGON_FIRE         , DragonFireEntity         .setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DRAGON_ICE          , DragonIceEntity          .setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DRAGON_LIGHTNING    , DragonLightningEntity    .setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DRAGONEGG_FIRE      , DragoneggFireEntity      .setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DRAGONEGG_ICE       , DragoneggIceEntity       .setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DRAGONEGG_LIGHTNING , DragoneggLightningEntity .setAttributes());
    }

    public static void registerCustomTrades() {
        DragonHeart.LOGGER.info("Registering villager trades.");
        VillagerProfession[] professions = {
                VillagerProfession.TOOLSMITH,
                VillagerProfession.WEAPONSMITH
        };
        for (VillagerProfession profession : professions) {
            TradeOfferHelper.registerVillagerOffers(
                    profession, 4,
                    factories -> factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 32),
                            new ItemStack(MiscItems.BONEHILT, 1),
                            1, 3, 0.08f
                    ))
            );
        }
    }

    public static void registerFuels() {
        DragonHeart.LOGGER.info("Registering fuels.");
        FuelRegistry.INSTANCE.add(MiscItems.DRAGONSTONE, 72000); // 1h
    }

    public static void register() {
        registerEntityAttributes();
        registerCustomTrades();
        registerFuels();
    }

}
