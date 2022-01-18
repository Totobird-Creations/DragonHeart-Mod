package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.sword.DragonboneSwordItem;
import net.totobirdcreations.dragonheart.item.sword.DragonboneSwordFireItem;
import net.totobirdcreations.dragonheart.item.sword.DragonboneSwordIceItem;
import net.totobirdcreations.dragonheart.item.sword.DragonboneSwordLightningItem;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;

public class SwordItems {

    public static final Item DRAGONBONE_SWORD = registerItem(
            "dragonbone_sword",
            new DragonboneSwordItem(
                new FabricItemSettings()
                        .group     (ItemGroup.COMBAT)
                        .fireproof ()
            )
    );

    public static final Item DRAGONBONE_SWORD_INFUSABLE = registerItem(
            "dragonbone_sword_infusable",
            new DragonboneSwordItem(
                new FabricItemSettings()
                        .group     (ItemGroup.COMBAT)
                        .fireproof ()
            )
    );

    public static final Item DRAGONBONE_SWORD_FIRE = registerItem(
            "dragonbone_sword_fire",
            new DragonboneSwordFireItem(
                new FabricItemSettings()
                        .group     (ItemGroup.COMBAT)
                        .fireproof ()
            )
    );

    public static final Item DRAGONBONE_SWORD_ICE = registerItem(
            "dragonbone_sword_ice",
            new DragonboneSwordIceItem(
                new FabricItemSettings()
                        .group     (ItemGroup.COMBAT)
                        .fireproof ()
            )
    );

    public static final Item DRAGONBONE_SWORD_LIGHTNING = registerItem(
            "dragonbone_sword_lightning",
            new DragonboneSwordLightningItem(
                new FabricItemSettings()
                        .group     (ItemGroup.COMBAT)
                        .fireproof ()
            )
    );

    public static void register() {}

}
