package net.totobirdcreations.dragonheart.item.misc;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.entity.ModEntities;
import net.totobirdcreations.dragonheart.item.misc.Dragonbucket;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;


public class MiscItems {


    public static final Item DRAGONBONE = registerItem(
            "dragonbone",
            new Item(
                new FabricItemSettings()
                        .group     (ItemGroup.MISC)
                        .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONSTONE = registerItem(
            "dragonstone",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item BONEHILT = registerItem(
            "bonehilt",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONSCALE = registerItem(
            "dragonscale",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONHEART_FIRE = registerItem(
            "dragonheart_fire",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONHEART_ICE = registerItem(
            "dragonheart_ice",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONHEART_LIGHTNING = registerItem(
            "dragonheart_lightning",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );


    @SuppressWarnings("unused")
    public static final Item DRAGONBLOOD_FIRE = registerItem(
            "dragonblood_fire",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBLOOD_ICE = registerItem(
            "dragonblood_ice",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBLOOD_LIGHTNING = registerItem(
            "dragonblood_lightning",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET = registerItem(
            "dragonbucket",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET_FIRE = registerItem(
            "dragonbucket_fire",
            new Dragonbucket(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET_ICE = registerItem(
            "dragonbucket_ice",
            new Dragonbucket(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET_LIGHTNING = registerItem(
            "dragonbucket_lightning",
            new Dragonbucket(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof ()
            )
    );

    public static final Item DRAGONEGG_FIRE = registerItem(
            "dragonegg_fire",
            new Dragonegg(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof (),
                    ModEntities.DRAGONEGG_FIRE
            )
    );

    public static final Item DRAGONEGG_ICE = registerItem(
            "dragonegg_ice",
            new Dragonegg(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof (),
                    ModEntities.DRAGONEGG_ICE
            )
    );

    public static final Item DRAGONEGG_LIGHTNING = registerItem(
            "dragonegg_lightning",
            new Dragonegg(
                    new FabricItemSettings()
                            .group     (ItemGroup.MISC)
                            .fireproof (),
                    ModEntities.DRAGONEGG_LIGHTNING
            )
    );


    public static void register() {}


}
