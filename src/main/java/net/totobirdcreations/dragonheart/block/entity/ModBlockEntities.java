package net.totobirdcreations.dragonheart.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.DragonforgeBlocks;



public class ModBlockEntities {


    public static final BlockEntityType DRAGONFORGE_CORE_BASE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_base"),
            FabricBlockEntityTypeBuilder.create(
                    DragonforgeCoreBaseBlockEntity::new,
                    DragonforgeBlocks.DRAGONFORGE_CORE_BASE.block
            ).build(null)
    );

    public static final BlockEntityType DRAGONFORGE_CORE_FIRE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_fire"),
            FabricBlockEntityTypeBuilder.create(
                    DragonforgeCoreFireBlockEntity::new,
                    DragonforgeBlocks.DRAGONFORGE_CORE_FIRE.block
            ).build(null)
    );

    public static final BlockEntityType DRAGONFORGE_CORE_ICE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_ice"),
            FabricBlockEntityTypeBuilder.create(
                    DragonforgeCoreIceBlockEntity::new,
                    DragonforgeBlocks.DRAGONFORGE_CORE_ICE.block
            ).build(null)
    );

    public static final BlockEntityType DRAGONFORGE_CORE_LIGHTNING = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.MOD_ID, "dragonforge_core_lightning"),
            FabricBlockEntityTypeBuilder.create(
                    DragonforgeCoreLightningBlockEntity::new,
                    DragonforgeBlocks.DRAGONFORGE_CORE_LIGHTNING.block
            ).build(null)
    );


    public static void register() {

        DragonHeart.LOGGER.info("Registering block entities.");

    }


}
