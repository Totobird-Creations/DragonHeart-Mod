package net.totobirdcreations.dragonheart.item.misc;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonEntityColourPicker;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;


public class MiscItems {


    public static final Item DRAGONBONE = registerItem(
            "dragonbone",
            new Item(
                new FabricItemSettings()
                        .group     (ItemGroup.MISC)
                        .maxCount  (16)
                        .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONSTONE = registerItem(
            "dragonstone",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (16)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item BONEHILT = registerItem(
            "bonehilt",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONSCALE = registerItem(
            "dragonscale",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (48)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONHEART_FIRE = registerItem(
            "dragonheart_fire",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (32)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONHEART_ICE = registerItem(
            "dragonheart_ice",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (32)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONHEART_LIGHTNING = registerItem(
            "dragonheart_lightning",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (32)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET = registerItem(
            "dragonbucket",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET_FIRE = registerItem(
            "dragonbucket_fire",
            new Dragonbucket(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET_ICE = registerItem(
            "dragonbucket_ice",
            new Dragonbucket(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET_LIGHTNING = registerItem(
            "dragonbucket_lightning",
            new Dragonbucket(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof ()
            )
    );

    public static final Item DRAGONEGG_FIRE = registerItem(
            "dragonegg_fire",
            new Dragonegg<>(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof (),
                    ModEntities.DRAGONEGG_FIRE,
                    DragonEntity.DragonType.FIRE
            )
    );

    public static final Item DRAGONEGG_ICE = registerItem(
            "dragonegg_ice",
            new Dragonegg<>(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof (),
                    ModEntities.DRAGONEGG_ICE,
                    DragonEntity.DragonType.ICE
            )
    );

    public static final Item DRAGONEGG_LIGHTNING = registerItem(
            "dragonegg_lightning",
            new Dragonegg<>(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof (),
                    ModEntities.DRAGONEGG_LIGHTNING,
                    DragonEntity.DragonType.LIGHTNING
            )
    );
    public static final Item DRAGONEGG_CREATIVE_FIRE = registerItem(
            "dragonegg_creative_fire",
            new DragoneggCreative<>(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof (),
                    ModEntities.DRAGONEGG_FIRE,
                    DragonEntity.DragonType.FIRE,
                    DRAGONEGG_FIRE
            )
    );

    public static final Item DRAGONEGG_CREATIVE_ICE = registerItem(
            "dragonegg_creative_ice",
            new DragoneggCreative<>(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof (),
                    ModEntities.DRAGONEGG_ICE,
                    DragonEntity.DragonType.ICE,
                    DRAGONEGG_ICE
            )
    );

    public static final Item DRAGONEGG_CREATIVE_LIGHTNING = registerItem(
            "dragonegg_creative_lightning",
            new DragoneggCreative<>(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .maxCount  (1)
                            .fireproof (),
                    ModEntities.DRAGONEGG_LIGHTNING,
                    DragonEntity.DragonType.LIGHTNING,
                    DRAGONEGG_LIGHTNING
            )
    );



    public static void register() {}


}
