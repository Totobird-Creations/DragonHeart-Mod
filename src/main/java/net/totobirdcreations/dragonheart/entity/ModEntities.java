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


    public static final EntityType<DragonFireEntity> DRAGON_FIRE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragon_fire"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DragonFireEntity::new)
                    .dimensions(EntityDimensions.fixed(5.0f, 2.0f))
                    .build()
    );


    public static void register() {

        DragonHeart.LOGGER.info("Registering entities.");

        FabricDefaultAttributeRegistry.register(DRAGON_FIRE, DragonFireEntity.createMobAttributes());

    }


}
