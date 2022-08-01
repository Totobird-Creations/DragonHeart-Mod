package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge_core.DragonForgeCoreBlockEntity;

public class DragonBlockEntities {


    public static final BlockEntityType<DragoneggIncubatorBlockEntity> DRAGONEGG_INCUBATOR = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragonegg_incubator"),
            FabricBlockEntityTypeBuilder.create(
                    DragoneggIncubatorBlockEntity::new,
                    DragonBlocks.DRAGONEGG_INCUBATOR.block()
            ).build(null)
    );


    public static final BlockEntityType<DragonForgeBricksBlockEntity> DRAGON_FORGE_BRICKS = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragon_forge_bricks"),
            FabricBlockEntityTypeBuilder.create(
                    DragonForgeBricksBlockEntity::new,
                    DragonBlocks.DRAGON_FORGE_BRICKS.block()
            ).build(null)
    );


    public static final BlockEntityType<DragonForgeApertureBlockEntity> DRAGON_FORGE_APERTURE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragon_forge_aperture"),
            FabricBlockEntityTypeBuilder.create(
                    DragonForgeApertureBlockEntity::new,
                    DragonBlocks.DRAGON_FORGE_APERTURE.block()
            ).build(null)
    );


    public static final BlockEntityType<DragonForgeHatchBlockEntity> DRAGON_FORGE_HATCH = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragon_forge_hatch"),
            FabricBlockEntityTypeBuilder.create(
                    DragonForgeHatchBlockEntity::new,
                    DragonBlocks.DRAGON_FORGE_HATCH.block()
            ).build(null)
    );


    public static final BlockEntityType<DragonForgeSupportBlockEntity> DRAGON_FORGE_SUPPORT = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragon_forge_support"),
            FabricBlockEntityTypeBuilder.create(
                    DragonForgeSupportBlockEntity::new,
                    DragonBlocks.DRAGON_FORGE_SUPPORT.block()
            ).build(null)
    );


    public static final BlockEntityType<DragonForgeCoreBlockEntity> DRAGON_FORGE_CORE = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(DragonHeart.ID, "dragon_forge_core"),
            FabricBlockEntityTypeBuilder.create(
                    DragonForgeCoreBlockEntity::new,
                    DragonBlocks.DRAGON_FORGE_CORE.block()
            ).build(null)
    );


    public static void register() {}


}
