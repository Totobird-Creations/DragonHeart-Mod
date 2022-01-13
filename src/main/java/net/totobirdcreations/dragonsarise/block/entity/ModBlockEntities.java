package net.totobirdcreations.dragonsarise.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonsarise.DragonsArise;
import net.totobirdcreations.dragonsarise.block.ModBlocks;

public class ModBlockEntities {

    public static BlockEntityType<DragonforgeLightningBlockEntity> DRAGONFORGE_LIGHTNING_BLOCK_ENTITY =
            Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(DragonsArise.MOD_ID, "dragonforge_lightning"),
            FabricBlockEntityTypeBuilder.create(
                    DragonforgeLightningBlockEntity::new,
                    ModBlocks.DRAGONFORGE_LIGHTNING
            ).build(null)
    );

    public static void register() {
        DragonsArise.LOGGER.info("Registering block entities.");
    }


}
