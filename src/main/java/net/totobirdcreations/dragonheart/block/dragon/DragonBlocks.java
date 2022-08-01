package net.totobirdcreations.dragonheart.block.dragon;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.totobirdcreations.dragonheart.block.util.BlockAndItem;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;
import net.totobirdcreations.dragonheart.item.misc.DragonBlockItem;

import static net.totobirdcreations.dragonheart.block.Blocks.registerBlock;


public class DragonBlocks {

    public static FabricBlockSettings settings = FabricBlockSettings
            .of(Material.STONE)
            .hardness(10.0f)
            .resistance(1200.0f)
            .luminance((state) -> state.get(DragonBlock.POWERED) ? 7 : 0);


    public static final BlockAndItem DRAGONEGG_INCUBATOR = registerBlock(
            "dragonegg_incubator",
            new DragoneggIncubatorBlock(settings),
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


    public static void register() {}

}
