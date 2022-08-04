package net.totobirdcreations.dragonheart.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon_egg.DragonEggEntity;


public class Entities {

    public static final EntityType<DragonEntity> DRAGON = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragon"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, DragonEntity::new)
                    .dimensions(DragonEntity.MAX_DIMENSIONS)
                    .build()
    );


    public static final EntityType<DragonEggEntity> DRAGON_EGG = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragon_egg"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, DragonEggEntity::new)
                    .dimensions(DragonEggEntity.DIMENSIONS)
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
        FabricDefaultAttributeRegistry.register( DRAGON     , DragonEntity    .createMobAttributes());
        FabricDefaultAttributeRegistry.register( DRAGON_EGG , DragonEggEntity.createMobAttributes());
    }


    public static PaintingVariant registerPainting(String name, PaintingVariant variant) {
        return Registry.register(Registry.PAINTING_VARIANT, new Identifier(DragonHeart.ID, name), variant);
    }


}
