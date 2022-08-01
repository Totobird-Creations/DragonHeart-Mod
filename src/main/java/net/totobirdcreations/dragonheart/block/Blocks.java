package net.totobirdcreations.dragonheart.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.BlockEntities;
import net.totobirdcreations.dragonheart.block.util.BlockAndItem;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class Blocks {


    @SuppressWarnings("unused")
    public static final BlockAndItem BURNED = registerBlock(
            "burned",
            new Block(FabricBlockSettings.copy(net.minecraft.block.Blocks.SAND)),
            ItemGroup.BUILDING_BLOCKS
    );

    @SuppressWarnings("unused")
    public static final BlockAndItem FROZEN = registerBlock(
            "frozen",
            new Block(FabricBlockSettings.copy(net.minecraft.block.Blocks.PACKED_ICE)),
            ItemGroup.BUILDING_BLOCKS
    );

    @SuppressWarnings("unused")
    public static final BlockAndItem ELETCRIFIED = registerBlock(
            "electrified",
            new Block(FabricBlockSettings.copy(net.minecraft.block.Blocks.STONE)),
            ItemGroup.BUILDING_BLOCKS
    );

    @SuppressWarnings("unused")
    public static final BlockAndItem DRAGONBONEBLOCK = registerBlock(
            "dragonboneblock",
            new PillarBlock(FabricBlockSettings.copy(net.minecraft.block.Blocks.BONE_BLOCK)),
            ItemGroups.DRAGON
    );



    public static BlockAndItem registerBlock(String name, Block block, ItemGroup group) {
        return registerBlockWithSettings(name, block, group, new FabricItemSettings());
    }
    public static BlockAndItem registerBlock(String name, Block block, ItemGroup group, Class<? extends BlockItem> blockItemClass) {
        return registerBlockWithSettings(name, block, group, new FabricItemSettings(), blockItemClass);
    }


    public static BlockAndItem registerBlockWithSettings(String name, Block block, ItemGroup group, FabricItemSettings settings) {
        return registerBlockWithSettings(name, block, group, settings, BlockItem.class);
    }
    public static BlockAndItem registerBlockWithSettings(String name, Block block, ItemGroup group, FabricItemSettings settings, Class<? extends BlockItem> blockItemClass) {
        Block blockBlock = Registry.register(
                Registry.BLOCK,
                new Identifier(DragonHeart.ID, name),
                block
        );
        try {
            BlockItem blockItem = Registry.register(
                    Registry.ITEM,
                    new Identifier(DragonHeart.ID, name),
                    blockItemClass.getConstructor(Block.class, Item.Settings.class).newInstance(blockBlock, settings)
            );
            return new BlockAndItem(blockBlock, blockItem);
        } catch (Exception e) {
            DragonHeart.LOGGER.error("Failed to register block item `" + DragonHeart.ID + ":" + name + "`. " + e);
        }
        return new BlockAndItem(blockBlock, Items.STONE);
    }


    public static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(
                Registry.BLOCK,
                new Identifier(DragonHeart.ID, name),
                block
        );
    }


    public static void register() {
        DragonHeart.LOGGER.info("Registering blocks.");
        DragonBlocks.register();
        BlockEntities.register();
    }


}
