package net.totobirdcreations.dragonheart.item.misc;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;

import static net.totobirdcreations.dragonheart.item.Items.registerItem;


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
            new DragonscaleItem(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (48)
                            .fireproof ()
            )
    );

    @SuppressWarnings("unused")
    public static final Item DRAGONBUCKET = registerItem(
            "dragonbucket",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (1)
                            .fireproof ()
            )
    );

    public static final Item DRAGONEGG = registerItem(
            "dragonegg",
            new DragoneggItem(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (1)
                            .fireproof ()
            )
    );
    public static final Item DRAGONEGG_CREATIVE = registerItem(
            "dragonegg_creative",
            new DragoneggCreativeItem(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (1)
                            .fireproof ()
            )
    );


    @SuppressWarnings("unused")
    public static final Item DRAGONBLOOD = registerItem(
            "dragonblood",
            new DragonbloodItem(
                    new FabricItemSettings()
                            .group(ItemGroups.DRAGON)
            )
    );


    public static final Item DRAGONBREATH = registerItem(
            "dragonbreath",
            new DragonbreathItem(
                    new FabricItemSettings()
                            .group(ItemGroups.DRAGON)
            )
    );



    public static void register() {}


}
