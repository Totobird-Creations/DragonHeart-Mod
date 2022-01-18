package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import net.totobirdcreations.dragonheart.item.group.ModItemGroups;
import net.totobirdcreations.dragonheart.item.misc.Dragonscale;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;

public class DragonforgeItems {

    public static final Item DRAGONFORGE_POWERCELL = registerItem(
            "dragonforge_powercell",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );

    public static final Item DRAGONFORGE_POWERCELL_FIRE = registerItem(
            "dragonforge_powercell_fire",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );

    public static final Item DRAGONFORGE_POWERCELL_ICE = registerItem(
            "dragonforge_powercell_ice",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );

    public static final Item DRAGONFORGE_POWERCELL_LIGHTNING = registerItem(
            "dragonforge_powercell_lightning",
            new Dragonscale(
                    new FabricItemSettings()
                            .group     (ModItemGroups.DRAGONFORGE)
                            .rarity    (Rarity.UNCOMMON)
                            .fireproof ()
            )
    );

    public static void register() {}

}
