package net.totobirdcreations.dragonsarise.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonsarise.DragonsArise;
import net.totobirdcreations.dragonsarise.block.entity.ModBlockEntities;

public class ModBlocks {

    public static final Block BURNED = registerBlock("burned",
            new Block(FabricBlockSettings.copy(Blocks.SAND)),
            ItemGroup.BUILDING_BLOCKS
    );

    public static final Block FROZEN = registerBlock("frozen",
            new Block(FabricBlockSettings.copy(Blocks.PACKED_ICE)),
            ItemGroup.BUILDING_BLOCKS
    );

    public static final Block ELETCRIFIED = registerBlock("electrified",
            new Block(FabricBlockSettings.copy(Blocks.STONE)),
            ItemGroup.BUILDING_BLOCKS
    );

    public static final Block DRAGONFORGE_LIGHTNING = registerBlock("dragonforge_lightning",
            new DragonforgeLightningBlock(FabricBlockSettings.copy(Blocks.ANVIL)),
            ItemGroup.DECORATIONS
    );

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(DragonsArise.MOD_ID, name),
            new BlockItem(block, new FabricItemSettings().group(group))
        );
    }

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(DragonsArise.MOD_ID, name), block);
    }

    public static void register() {
        DragonsArise.LOGGER.info("Registering blocks.");
        ModBlockEntities.register();
    }

}
