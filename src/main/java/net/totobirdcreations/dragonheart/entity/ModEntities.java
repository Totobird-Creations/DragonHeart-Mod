package net.totobirdcreations.dragonheart.entity;


import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonFireEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonIceEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonLightningEntity;


public class ModEntities {

    public static final EntityType<DragonFireEntity> DRAGON_FIRE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_fire"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonFireEntity::new)
                    .dimensions(DragonEntity.DIMENSIONS)
                    .build()
    );

    public static final EntityType<DragonIceEntity> DRAGON_ICE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_ice"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonIceEntity::new)
                    .dimensions(DragonEntity.DIMENSIONS)
                    .build()
    );

    public static final EntityType<DragonLightningEntity> DRAGON_LIGHTNING = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_lightning"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonLightningEntity::new)
                    .dimensions(DragonEntity.DIMENSIONS)
                    .build()
    );


    public static void register() {

        DragonHeart.LOGGER.info("Registering entities.");

        // TODO : Figure out what causes this warning.
        FabricDefaultAttributeRegistry.register( DRAGON_FIRE      , DragonFireEntity.createMobAttributes()      );
        FabricDefaultAttributeRegistry.register( DRAGON_ICE       , DragonIceEntity.createMobAttributes()       );
        FabricDefaultAttributeRegistry.register( DRAGON_LIGHTNING , DragonLightningEntity.createMobAttributes() );

    }


}
