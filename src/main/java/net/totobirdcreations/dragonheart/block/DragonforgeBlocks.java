package net.totobirdcreations.dragonheart.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.util.*;
import net.totobirdcreations.dragonheart.item.group.ModItemGroups;

import static net.totobirdcreations.dragonheart.block.ModBlocks.registerBlock;
import static net.totobirdcreations.dragonheart.block.ModBlocks.registerBlockWithoutItem;



public class DragonforgeBlocks {


    public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) {

        return false;

    }


    public static final ItemBlock DRAGONFORGE_BRICKS_BASE = registerBlock(
            "dragonforge_bricks_base",
            new DragonforgeBrick(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_APERTURE_BASE = registerBlock(
            "dragonforge_aperture_base",
            new DragonforgeAperture(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_HATCH_BASE = registerBlock(
            "dragonforge_hatch_base",
            new DragonforgeHatch(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_SUPPORT_BASE = registerBlock(
            "dragonforge_support_base",
            new DragonforgeStructureBlock(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_CORE_BASE = registerBlock(
            "dragonforge_core_base",
            new DragonforgeCoreBase(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).luminance(DragonforgeCore::getLuminance)),
            ModItemGroups.DRAGONFORGE
    );

    public static final Block DRAGONFORGE_WINDOW_BASE = registerBlockWithoutItem(
            "dragonforge_window_base",
            new DragonforgeWindow(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).nonOpaque().solidBlock(DragonforgeBlocks::never).blockVision(DragonforgeBlocks::never))
    );


    public static final ItemBlock DRAGONFORGE_BRICKS_FIRE = registerBlock(
            "dragonforge_bricks_fire",
            new DragonforgeBrick(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_APERTURE_FIRE = registerBlock(
            "dragonforge_aperture_fire",
            new DragonforgeAperture(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_HATCH_FIRE = registerBlock(
            "dragonforge_hatch_fire",
            new DragonforgeHatch(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_SUPPORT_FIRE = registerBlock(
            "dragonforge_support_fire",
            new DragonforgeStructureBlock(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_CORE_FIRE = registerBlock(
            "dragonforge_core_fire",
            new DragonforgeCoreType(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).luminance(DragonforgeCore::getLuminance)),
            ModItemGroups.DRAGONFORGE
    );

    public static final Block DRAGONFORGE_WINDOW_FIRE = registerBlockWithoutItem(
            "dragonforge_window_fire",
            new DragonforgeWindow(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).nonOpaque().solidBlock(DragonforgeBlocks::never).blockVision(DragonforgeBlocks::never))
    );


    public static final ItemBlock DRAGONFORGE_BRICKS_ICE = registerBlock(
            "dragonforge_bricks_ice",
            new DragonforgeBrick(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_APERTURE_ICE = registerBlock(
            "dragonforge_aperture_ice",
            new DragonforgeAperture(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_HATCH_ICE = registerBlock(
            "dragonforge_hatch_ice",
            new DragonforgeHatch(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_SUPPORT_ICE = registerBlock(
            "dragonforge_support_ice",
            new DragonforgeStructureBlock(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_CORE_ICE = registerBlock(
            "dragonforge_core_ice",
            new DragonforgeCoreType(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).luminance(DragonforgeCore::getLuminance)),
            ModItemGroups.DRAGONFORGE
    );

    public static final Block DRAGONFORGE_WINDOW_ICE = registerBlockWithoutItem(
            "dragonforge_window_ice",
            new DragonforgeWindow(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).nonOpaque().solidBlock(DragonforgeBlocks::never).blockVision(DragonforgeBlocks::never))
    );


    public static final ItemBlock DRAGONFORGE_BRICKS_LIGHTNING = registerBlock(
            "dragonforge_bricks_lightning",
            new DragonforgeBrick(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_APERTURE_LIGHTNING = registerBlock(
            "dragonforge_aperture_lightning",
            new DragonforgeAperture(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_HATCH_LIGHTNING = registerBlock(
            "dragonforge_hatch_lightning",
            new DragonforgeHatch(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_SUPPORT_LIGHTNING = registerBlock(
            "dragonforge_support_lightning",
            new DragonforgeStructureBlock(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f)),
            ModItemGroups.DRAGONFORGE
    );

    public static final ItemBlock DRAGONFORGE_CORE_LIGHTNING = registerBlock(
            "dragonforge_core_lightning",
            new DragonforgeCoreType(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).luminance(DragonforgeCore::getLuminance)),
            ModItemGroups.DRAGONFORGE
    );

    public static final Block DRAGONFORGE_WINDOW_LIGHTNING = registerBlockWithoutItem(
            "dragonforge_window_lightning",
            new DragonforgeWindow(FabricBlockSettings.of(Material.STONE).hardness(10.0f).resistance(1200.0f).nonOpaque().solidBlock(DragonforgeBlocks::never).blockVision(DragonforgeBlocks::never))
    );


    public static void register() {

        DragonHeart.LOGGER.info("Test");
        Block[] base_cores = {
                DRAGONFORGE_CORE_BASE.block
        };
        Block[] base_bricks = {
                DRAGONFORGE_BRICKS_BASE.block,
                DRAGONFORGE_BRICKS_FIRE.block,
                DRAGONFORGE_BRICKS_ICE.block,
                DRAGONFORGE_BRICKS_LIGHTNING.block
        };
        Block[] base_hatches = {
                DRAGONFORGE_HATCH_BASE.block,
                DRAGONFORGE_HATCH_FIRE.block,
                DRAGONFORGE_HATCH_ICE.block,
                DRAGONFORGE_HATCH_LIGHTNING.block
        };
        Block[] base_apertures = {
                DRAGONFORGE_APERTURE_BASE.block,
                DRAGONFORGE_APERTURE_FIRE.block,
                DRAGONFORGE_APERTURE_ICE.block,
                DRAGONFORGE_APERTURE_LIGHTNING.block
        };
        Block[] base_windows = {
                DRAGONFORGE_WINDOW_BASE,
                DRAGONFORGE_WINDOW_FIRE,
                DRAGONFORGE_WINDOW_ICE,
                DRAGONFORGE_WINDOW_LIGHTNING
        };
        Block[] base_supports = {
                DRAGONFORGE_SUPPORT_BASE.block,
                DRAGONFORGE_SUPPORT_FIRE.block,
                DRAGONFORGE_SUPPORT_ICE.block,
                DRAGONFORGE_SUPPORT_LIGHTNING.block
        };
        ((DragonforgeCore)DRAGONFORGE_CORE_BASE.block).setDependencyBlocks(
                base_bricks,
                base_hatches,
                base_apertures,
                base_windows,
                base_supports
        );
        ( ( DragonforgeAperture       ) DRAGONFORGE_APERTURE_BASE .block ).setDependencyBlocks( base_cores                           );
        ( ( DragonforgePowerable      ) DRAGONFORGE_HATCH_BASE    .block ).setDependencyBlocks( base_cores                           );
        ( ( DragonforgeBrick          ) DRAGONFORGE_BRICKS_BASE   .block ).setDependencyBlocks( DRAGONFORGE_WINDOW_BASE , base_cores );
        ( ( DragonforgeWindow         ) DRAGONFORGE_WINDOW_BASE          ).setDependencyBlocks( DRAGONFORGE_BRICKS_BASE , base_cores );
        ( ( DragonforgeStructureBlock ) DRAGONFORGE_SUPPORT_BASE  .block ).setDependencyBlocks( base_cores                           );


        Block[] fire_cores = {
                DRAGONFORGE_CORE_BASE.block,
                DRAGONFORGE_CORE_FIRE.block
        };
        Block[] fire_bricks = {
                DRAGONFORGE_BRICKS_FIRE.block
        };
        Block[] fire_hatches = {
                DRAGONFORGE_HATCH_FIRE.block
        };
        Block[] fire_apertures = {
                DRAGONFORGE_APERTURE_FIRE.block
        };
        Block[] fire_windows = {
                DRAGONFORGE_WINDOW_FIRE
        };
        Block[] fire_supports = {
                DRAGONFORGE_SUPPORT_FIRE.block
        };
        ((DragonforgeCore)DRAGONFORGE_CORE_FIRE.block).setDependencyBlocks(
                fire_bricks,
                fire_hatches,
                fire_apertures,
                fire_windows,
                fire_supports
        );
        ( ( DragonforgeAperture       ) DRAGONFORGE_APERTURE_FIRE .block ).setDependencyBlocks( fire_cores                           );
        ( ( DragonforgePowerable      ) DRAGONFORGE_HATCH_FIRE    .block ).setDependencyBlocks( fire_cores                           );
        ( ( DragonforgeBrick          ) DRAGONFORGE_BRICKS_FIRE   .block ).setDependencyBlocks( DRAGONFORGE_WINDOW_FIRE , fire_cores );
        ( ( DragonforgeWindow         ) DRAGONFORGE_WINDOW_FIRE          ).setDependencyBlocks( DRAGONFORGE_BRICKS_FIRE , fire_cores );
        ( ( DragonforgeStructureBlock ) DRAGONFORGE_SUPPORT_FIRE  .block ).setDependencyBlocks( fire_cores                           );


        Block[] ice_cores = {
                DRAGONFORGE_CORE_BASE.block,
                DRAGONFORGE_CORE_ICE.block
        };
        Block[] ice_bricks = {
                DRAGONFORGE_BRICKS_ICE.block
        };
        Block[] ice_hatches = {
                DRAGONFORGE_HATCH_ICE.block
        };
        Block[] ice_apertures = {
                DRAGONFORGE_APERTURE_ICE.block
        };
        Block[] ice_windows = {
                DRAGONFORGE_WINDOW_ICE
        };
        Block[] ice_supports = {
                DRAGONFORGE_SUPPORT_ICE.block
        };
        ((DragonforgeCore)DRAGONFORGE_CORE_ICE.block).setDependencyBlocks(
                ice_bricks,
                ice_hatches,
                ice_apertures,
                ice_windows,
                ice_supports
        );
        ( ( DragonforgeAperture       ) DRAGONFORGE_APERTURE_ICE .block ).setDependencyBlocks( ice_cores                          );
        ( ( DragonforgePowerable      ) DRAGONFORGE_HATCH_ICE    .block ).setDependencyBlocks( ice_cores                          );
        ( ( DragonforgeBrick          ) DRAGONFORGE_BRICKS_ICE   .block ).setDependencyBlocks( DRAGONFORGE_WINDOW_ICE , ice_cores );
        ( ( DragonforgeWindow         ) DRAGONFORGE_WINDOW_ICE          ).setDependencyBlocks( DRAGONFORGE_BRICKS_ICE , ice_cores );
        ( ( DragonforgeStructureBlock ) DRAGONFORGE_SUPPORT_ICE  .block ).setDependencyBlocks( ice_cores                          );


        Block[] lightning_cores = {
                DRAGONFORGE_CORE_BASE.block,
                DRAGONFORGE_CORE_LIGHTNING.block
        };
        Block[] lightning_bricks = {
                DRAGONFORGE_BRICKS_LIGHTNING.block
        };
        Block[] lightning_hatches = {
                DRAGONFORGE_HATCH_LIGHTNING.block
        };
        Block[] lightning_apertures = {
                DRAGONFORGE_APERTURE_LIGHTNING.block
        };
        Block[] lightning_windows = {
                DRAGONFORGE_WINDOW_LIGHTNING
        };
        Block[] lightning_supports = {
                DRAGONFORGE_SUPPORT_LIGHTNING.block
        };
        ((DragonforgeCore)DRAGONFORGE_CORE_LIGHTNING.block).setDependencyBlocks(
                lightning_bricks,
                lightning_hatches,
                lightning_apertures,
                lightning_windows,
                lightning_supports
        );
        ( ( DragonforgeAperture       ) DRAGONFORGE_APERTURE_LIGHTNING .block ).setDependencyBlocks( lightning_cores                                );
        ( ( DragonforgePowerable      ) DRAGONFORGE_HATCH_LIGHTNING    .block ).setDependencyBlocks( lightning_cores                                );
        ( ( DragonforgeBrick          ) DRAGONFORGE_BRICKS_LIGHTNING   .block ).setDependencyBlocks( DRAGONFORGE_WINDOW_LIGHTNING , lightning_cores );
        ( ( DragonforgeWindow         ) DRAGONFORGE_WINDOW_LIGHTNING          ).setDependencyBlocks( DRAGONFORGE_BRICKS_LIGHTNING , lightning_cores );
        ( ( DragonforgeStructureBlock ) DRAGONFORGE_SUPPORT_LIGHTNING  .block ).setDependencyBlocks( lightning_cores                                );

    }


}
