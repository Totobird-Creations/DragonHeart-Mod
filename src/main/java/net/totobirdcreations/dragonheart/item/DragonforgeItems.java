package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import net.totobirdcreations.dragonheart.item.group.ModItemGroups;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;



public class DragonforgeItems {


    @SuppressWarnings("unused")
    public static final Item DRAGONFORGE_POWERCELL = registerItem(
            "powercell",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );

    public static final Item POWERCELL_FIRE = registerItem(
            "powercell_fire",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );

    public static final Item POWERCELL_ICE = registerItem(
            "powercell_ice",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );

    public static final Item POWERCELL_LIGHTNING = registerItem(
            "powercell_lightning",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );


    public static void register() {}


}
