package net.totobirdcreations.dragonheart.block.dragon;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.totobirdcreations.dragonheart.block.dragon.forge.*;
import net.totobirdcreations.dragonheart.block.util.BlockAndItem;
import net.totobirdcreations.dragonheart.item.dragon.DragonBlockItem;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;

import static net.totobirdcreations.dragonheart.block.Blocks.registerBlock;
import static net.totobirdcreations.dragonheart.block.Blocks.registerBlockWithoutItem;


public class DragonBlocks {

    public static FabricBlockSettings settings = FabricBlockSettings
            .of(Material.STONE)
            .hardness(10.0f)
            .resistance(Blocks.OBSIDIAN.getBlastResistance())
            .luminance(DragonBlock::getLuminance);


    public static final BlockAndItem DRAGON_EGG_INCUBATOR = registerBlock(
            "dragon_egg_incubator",
            new DragonEggIncubatorBlock(settings),
            ItemGroups.DRAGON,
            DragonBlockItem.class
    );


    public static final BlockAndItem DRAGON_FORGE_BRICKS = registerBlock(
            "dragon_forge_bricks",
            new DragonForgeBricksBlock(settings),
            ItemGroups.DRAGON,
            DragonBlockItem.class
    );

    public static final BlockAndItem DRAGON_FORGE_APERTURE = registerBlock(
            "dragon_forge_aperture",
            new DragonForgeApertureBlock(settings),
            ItemGroups.DRAGON,
            DragonBlockItem.class
    );

    public static final BlockAndItem DRAGON_FORGE_HATCH = registerBlock(
            "dragon_forge_hatch",
            new DragonForgeHatchBlock(settings),
            ItemGroups.DRAGON,
            DragonBlockItem.class
    );

    public static final BlockAndItem DRAGON_FORGE_SUPPORT = registerBlock(
            "dragon_forge_support",
            new DragonForgeSupportBlock(settings),
            ItemGroups.DRAGON,
            DragonBlockItem.class
    );

    public static final BlockAndItem DRAGON_FORGE_CORE = registerBlock(
            "dragon_forge_core",
            new DragonForgeCoreBlock(settings),
            ItemGroups.DRAGON,
            DragonBlockItem.class
    );

    public static final BlockAndItem PLATED_DRAGON_FORGE_BRICKS = registerBlock(
            "plated_dragon_forge_bricks",
            new PlatedDragonForgeBricksBlock(settings),
            ItemGroups.DRAGON,
            DragonBlockItem.class
    );

    public static final Block DRAGON_GRIEFED = registerBlockWithoutItem(
            "dragon_griefed",
            new DragonGriefedBlock(FabricBlockSettings.copy(Blocks.SAND)
                    .requiresTool()
            )
    );


    public static final BlockAndItem DRAGON_BONE_BLOCK = registerBlock(
            "dragon_bone_block",
            new PillarBlock(FabricBlockSettings.copy(Blocks.BONE_BLOCK)),
            ItemGroups.DRAGON
    );


    public static void register() {}

}
