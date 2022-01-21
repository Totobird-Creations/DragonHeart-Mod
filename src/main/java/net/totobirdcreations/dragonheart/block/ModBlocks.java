package net.totobirdcreations.dragonheart.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.ModBlockEntities;
import net.totobirdcreations.dragonheart.block.util.ItemBlock;



public class ModBlocks {


    public static final ItemBlock BURNED = registerBlock(
            "burned",
            new Block(FabricBlockSettings.copy(Blocks.SAND)),
            ItemGroup.BUILDING_BLOCKS
    );

    public static final ItemBlock FROZEN = registerBlock(
            "frozen",
            new Block(FabricBlockSettings.copy(Blocks.PACKED_ICE)),
            ItemGroup.BUILDING_BLOCKS
    );

    public static final ItemBlock ELETCRIFIED = registerBlock(
            "electrified",
            new Block(FabricBlockSettings.copy(Blocks.STONE)),
            ItemGroup.BUILDING_BLOCKS
    );


    public static ItemBlock registerBlock(String name, Block block, ItemGroup group) {

        Block blockBlock = Registry.register(
                Registry.BLOCK,
                new Identifier(DragonHeart.MOD_ID, name),
                block
        );
        BlockItem blockItem = Registry.register(
                Registry.ITEM,
                new Identifier(DragonHeart.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group))
        );
        return new ItemBlock(blockBlock, blockItem);

    }


    public static Block registerBlockWithoutItem(String name, Block block) {

        return Registry.register(
                Registry.BLOCK,
                new Identifier(DragonHeart.MOD_ID, name),
                block
        );

    }


    public static void register() {

        DragonHeart.LOGGER.info("Registering blocks.");
        DragonforgeBlocks .register();
        ModBlockEntities  .register();

    }


}
