package net.totobirdcreations.dragonheart.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonFireEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonIceEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonLightningEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggFireEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggIceEntity;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggLightningEntity;


public class ModEntities {

    public static final EntityType<DragonFireEntity> DRAGON_FIRE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_fire"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonFireEntity::new)
                    .dimensions(DragonEntity.MAX_DIMENSIONS)
                    .build()
    );

    public static final EntityType<DragonIceEntity> DRAGON_ICE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_ice"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonIceEntity::new)
                    .dimensions(DragonEntity.MAX_DIMENSIONS)
                    .build()
    );

    public static final EntityType<DragonLightningEntity> DRAGON_LIGHTNING = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_lightning"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonLightningEntity::new)
                    .dimensions(DragonEntity.MAX_DIMENSIONS)
                    .build()
    );


    public static final EntityType<DragoneggFireEntity> DRAGONEGG_FIRE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonegg_fire"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragoneggFireEntity::new)
                    .dimensions(DragoneggEntity.DIMENSIONS)
                    .build()
    );

    public static final EntityType<DragoneggIceEntity> DRAGONEGG_ICE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonegg_ice"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragoneggIceEntity::new)
                    .dimensions(DragoneggEntity.DIMENSIONS)
                    .build()
    );

    public static final EntityType<DragoneggLightningEntity> DRAGONEGG_LIGHTNING = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonegg_lightning"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragoneggLightningEntity::new)
                    .dimensions(DragoneggEntity.DIMENSIONS)
                    .build()
    );


    @SuppressWarnings("unused")
    public static final PaintingVariant PAINTING_DRAGON_FIRE = registerPainting(
            "dragon/fire",
            new PaintingVariant(16, 32)
    );

    @SuppressWarnings("unused")
    public static final PaintingVariant PAINTING_DRAGON_ICE = registerPainting(
            "dragon/ice",
            new PaintingVariant(16, 32)
    );

    @SuppressWarnings("unused")
    public static final PaintingVariant PAINTING_DRAGON_LIGHTNING = registerPainting(
            "dragon/lightning",
            new PaintingVariant(16, 32)
    );


    @SuppressWarnings("all")
    public static void register() {
        DragonHeart.LOGGER.info("Registering entities.");
        FabricDefaultAttributeRegistry.register( DRAGON_FIRE         , DragonFireEntity         .createMobAttributes());
        FabricDefaultAttributeRegistry.register( DRAGON_ICE          , DragonIceEntity          .createMobAttributes());
        FabricDefaultAttributeRegistry.register( DRAGON_LIGHTNING    , DragonLightningEntity    .createMobAttributes());
        FabricDefaultAttributeRegistry.register( DRAGONEGG_FIRE      , DragoneggFireEntity      .createMobAttributes());
        FabricDefaultAttributeRegistry.register( DRAGONEGG_ICE       , DragoneggIceEntity       .createMobAttributes());
        FabricDefaultAttributeRegistry.register( DRAGONEGG_LIGHTNING , DragoneggLightningEntity .createMobAttributes());
    }


    public static PaintingVariant registerPainting(String name, PaintingVariant variant) {
        return Registry.register(Registry.PAINTING_VARIANT, new Identifier(DragonHeart.MOD_ID, name), variant);
    }


}
