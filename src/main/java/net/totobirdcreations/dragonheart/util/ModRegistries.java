package net.totobirdcreations.dragonheart.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.totobirdcreations.dragonheart.entity.dragon.DragonFireEntity;
import net.totobirdcreations.dragonheart.entity.ModEntities;

public class ModRegistries {

    public static void registerEntityAttributes() {
        // TODO : Do something about this warning.
        FabricDefaultAttributeRegistry.register(ModEntities.DRAGON_FIRE, DragonFireEntity.setAttributes());
    }

    public static void register() {
        registerEntityAttributes();
    }

}
