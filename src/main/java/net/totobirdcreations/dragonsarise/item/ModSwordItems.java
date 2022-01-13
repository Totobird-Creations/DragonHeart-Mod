package net.totobirdcreations.dragonsarise.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.totobirdcreations.dragonsarise.DragonsArise;
import net.totobirdcreations.dragonsarise.item.sword.DragonboneSwordItem;
import net.totobirdcreations.dragonsarise.item.sword.DragonboneSwordFireItem;
import net.totobirdcreations.dragonsarise.item.sword.DragonboneSwordIceItem;
import net.totobirdcreations.dragonsarise.item.sword.DragonboneSwordLightningItem;

public class ModSwordItems {

    public static final Item DRAGONBONE_SWORD = ModItems.registerItem("dragonbone_sword", new DragonboneSwordItem(
            new FabricItemSettings()
                    .group(ItemGroup.COMBAT)
                    .rarity(Rarity.COMMON)
                    .fireproof()
    ));

    public static final Item DRAGONBONE_SWORD_INFUSABLE = ModItems.registerItem("dragonbone_sword_infusable", new DragonboneSwordItem(
            new FabricItemSettings()
                    .group(ItemGroup.COMBAT)
                    .rarity(Rarity.UNCOMMON)
                    .fireproof()
    ));

    public static final Item DRAGONBONE_SWORD_FIRE = ModItems.registerItem("dragonbone_sword_fire", new DragonboneSwordFireItem(
            new FabricItemSettings()
                    .group(ItemGroup.COMBAT)
                    .rarity(Rarity.RARE)
                    .fireproof()
    ));

    public static final Item DRAGONBONE_SWORD_ICE = ModItems.registerItem("dragonbone_sword_ice", new DragonboneSwordIceItem(
            new FabricItemSettings()
                    .group(ItemGroup.COMBAT)
                    .rarity(Rarity.RARE)
                    .fireproof()
    ));

    public static final Item DRAGONBONE_SWORD_LIGHTNING = ModItems.registerItem("dragonbone_sword_lightning", new DragonboneSwordLightningItem(
            new FabricItemSettings()
                    .group(ItemGroup.COMBAT)
                    .rarity(Rarity.RARE)
                    .fireproof()
    ));

    public static void register() {
        DragonsArise.LOGGER.info("Registering Sword Items.");
    }

}
