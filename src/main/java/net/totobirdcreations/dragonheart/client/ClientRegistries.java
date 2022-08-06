package net.totobirdcreations.dragonheart.client;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon_egg.DragonEggEntity;


public class ClientRegistries {

    @SuppressWarnings("all")
    public static void registerEntityAttributes() {
        DragonHeart.LOGGER.info("Registering entity attributes.");
        FabricDefaultAttributeRegistry.register( Entities.DRAGON     , DragonEntity    .setAttributes());
        FabricDefaultAttributeRegistry.register( Entities.DRAGON_EGG , DragonEggEntity.setAttributes());
    }


    public static void register() {
        registerEntityAttributes();
    }

}