package net.totobirdcreations.dragonheart.entity;


import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;



public class ModEntities {

    public static final Identifier SPAWN_PACKET = new Identifier(DragonHeart.MOD_ID, "spawn_packed");


    public static final EntityType<DragonbreathFire> DRAGONBREATH_FIRE_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonbreath_fire"),
            FabricEntityTypeBuilder.<DragonbreathFire>create(SpawnGroup.MISC, DragonbreathFire::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<DragonbreathIce> DRAGONBREATH_ICE_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonbreath_ice"),
            FabricEntityTypeBuilder.<DragonbreathIce>create(SpawnGroup.MISC, DragonbreathIce::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );


    public static final EntityType<DragonFireEntity> DRAGON_FIRE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_fire"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DragonFireEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );


    public static void register() {

        DragonHeart.LOGGER.info("Registering entities.");

        FabricDefaultAttributeRegistry.register(DRAGON_FIRE, DragonFireEntity.createMobAttributes());

    }


}
