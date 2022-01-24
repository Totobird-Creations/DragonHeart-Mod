package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;


public class HiddenItems {


    public static final Item DRAGONBREATH_FIRE = registerItem(
            "dragonbreath_fire",
            new Item(
                    new FabricItemSettings()
                            .group     (null)
                            .fireproof ()
            )
    );

    public static final Item DRAGONBREATH_ICE = registerItem(
            "dragonbreath_ice",
            new Item(
                    new FabricItemSettings()
                            .group     (null)
                            .fireproof ()
            )
    );


    public static void register() {



    }


}
