package net.totobirdcreations.dragonheart.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.client.block.DragonGriefedBlockEntityRenderer;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.client.entity.dragon.DragonEntityRenderer;
import net.totobirdcreations.dragonheart.client.entity.dragon_egg.DragonEggEntityRenderer;
import net.totobirdcreations.dragonheart.item.dragon.DragonItem;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.item.dragon.tool.DragonToolItems;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;


public class ClientRenderers {

    public static void register() {
        DragonHeart.LOGGER.debug("Registering client renderers.");

        registerEntities();
        registerItems();
        registerBlocks();
    }


    public static void registerEntities() {
        EntityRendererRegistry.register(Entities.DRAGON     , DragonEntityRenderer::new    );
        EntityRendererRegistry.register(Entities.DRAGON_EGG , DragonEggEntityRenderer::new );
    }


    public static void registerItems() {
        ColorProviderRegistry.ITEM.register(ClientRenderers::getDragonItemColour,
                DragonItems.DRAGON_SCALE,
                DragonItems.DRAGON_EGG,
                DragonItems.DRAGON_BLOOD,
                DragonItems.DRAGON_BREATH,
                DragonItems.DRAGON_CELLS
        );
        ColorProviderRegistry.ITEM.register(ClientRenderers::getDragonBucketItemColour,
                DragonItems.EMPTY_DRAGON_BUCKET,
                DragonItems.DRAGON_BUCKET
        );

        ColorProviderRegistry.ITEM.register(ClientRenderers::getDragonBlockItemColour,
                DragonBlocks.DRAGON_FORGE_BRICKS.item(),
                DragonBlocks.DRAGON_FORGE_APERTURE.item(),
                DragonBlocks.DRAGON_FORGE_HATCH.item(),
                DragonBlocks.DRAGON_FORGE_SUPPORT.item(),
                DragonBlocks.DRAGON_FORGE_CORE.item(),
                DragonBlocks.DRAGON_EGG_INCUBATOR.item(),
                DragonBlocks.PLATED_DRAGON_FORGE_BRICKS.item()
        );

        ColorProviderRegistry.ITEM.register(ClientRenderers::getDragonToolItemColour,
                DragonToolItems.DRAGON_BONE_AXE,
                DragonToolItems.DRAGON_BONE_HOE,
                DragonToolItems.DRAGON_BONE_PICKAXE,
                DragonToolItems.DRAGON_BONE_SHOVEL,
                DragonToolItems.DRAGON_BONE_SWORD,
                DragonToolItems.DRAGON_STEEL_AXE,
                DragonToolItems.DRAGON_STEEL_HOE,
                DragonToolItems.DRAGON_STEEL_PICKAXE,
                DragonToolItems.DRAGON_STEEL_SHOVEL,
                DragonToolItems.DRAGON_STEEL_SWORD
        );
    }


    public static void registerBlocks() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                DragonBlocks.DRAGON_FORGE_BRICKS.block(),
                DragonBlocks.DRAGON_FORGE_APERTURE.block(),
                DragonBlocks.DRAGON_FORGE_HATCH.block(),
                DragonBlocks.DRAGON_FORGE_SUPPORT.block(),
                DragonBlocks.DRAGON_FORGE_CORE.block(),
                DragonBlocks.DRAGON_EGG_INCUBATOR.block(),
                DragonBlocks.PLATED_DRAGON_FORGE_BRICKS.block()
        );

        ColorProviderRegistry.BLOCK.register(ClientRenderers::getDragonBlockColour,
                DragonBlocks.DRAGON_FORGE_BRICKS.block(),
                DragonBlocks.DRAGON_FORGE_APERTURE.block(),
                DragonBlocks.DRAGON_FORGE_HATCH.block(),
                DragonBlocks.DRAGON_FORGE_SUPPORT.block(),
                DragonBlocks.DRAGON_FORGE_CORE.block(),
                DragonBlocks.PLATED_DRAGON_FORGE_BRICKS.block()
        );
        ColorProviderRegistry.BLOCK.register(ClientRenderers::getDragoneggIncubatorBlockColour,
                DragonBlocks.DRAGON_EGG_INCUBATOR.block()
        );

        registerBlockEntities();
    }


    public static void registerBlockEntities() {
        BlockEntityRendererRegistry.register(DragonBlockEntities.DRAGON_GRIEFED, DragonGriefedBlockEntityRenderer::new);
    }



    public static int getDragonItemColour(ItemStack stack, int tintIndex) {
        return tintIndex == 0
            ? ((DragonItem)(stack.getItem())).getColor(stack)
            : RGBColour.WHITE.asInt();
    }
    public static int getDragonBucketItemColour(ItemStack stack, int tintIndex) {
        return switch (tintIndex) {
            case    1 -> DragonResourceLoader.getResource(NbtHelper.getItemDragonType(stack)).colourGlow().asInt();
            case    2 -> ((DragonItem)(stack.getItem())).getColor(stack);
            default   -> RGBColour.WHITE.asInt();
        };
    }


    public static int getDragonBlockItemColour(ItemStack stack, int tintIndex) {
        DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(
                NbtHelper.getItemDragonType(stack)
        );
        return getDragonBlockColour(resource, tintIndex).asInt();
    }


    public static int getDragonToolItemColour(ItemStack stack, int tintIndex) {
        DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(
                NbtHelper.getItemDragonType(stack)
        );
        return (switch (tintIndex) {
            case    1 -> resource.colourBricks();
            case    2 -> resource.colourCracks();
            case    3 -> resource.colourGlow();
            default   -> RGBColour.WHITE;
        }).asInt();
    }



    public static int getDragonBlockColour(BlockState state, @Nullable BlockRenderView view, BlockPos pos, int tintIndex) {
        if (view != null) {
            BlockEntity blockEntity = view.getBlockEntity(pos);
            if (blockEntity instanceof DragonBlockEntity dragonBlockEntity) {
                return getDragonBlockColour(
                        DragonResourceLoader.getResource(dragonBlockEntity.dragon),
                        tintIndex
                ).asInt();
            }
        }
        return RGBColour.WHITE.asInt();
    }

    public static int getDragoneggIncubatorBlockColour(BlockState state, @Nullable BlockRenderView view, BlockPos pos, int tintIndex) {
        if (view != null) {
            BlockEntity blockEntity = view.getBlockEntity(pos);
            if (blockEntity instanceof DragonBlockEntity dragonBlockEntity) {
                if (tintIndex == 3) {
                    return DragonResourceLoader.getResource(dragonBlockEntity.dragon).colourGlow().asInt();
                } else {
                    return getDragonBlockColour(
                            DragonResourceLoader.getResource(NbtHelper.EMPTY_TYPE),
                            tintIndex
                    ).asInt();
                }
            }
        }
        return RGBColour.WHITE.asInt();
    }


    public static RGBColour getDragonBlockColour(DragonResourceLoader.DragonResource resource, int tintIndex) {
        return switch (tintIndex) {
            case    0 -> resource. colourBricks();
            case    1 -> resource. colourCracks();
            case    3 -> resource. colourGlow();
            default   -> RGBColour.WHITE;
        };
    }

}
