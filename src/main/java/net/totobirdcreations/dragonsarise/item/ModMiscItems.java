package net.totobirdcreations.dragonsarise.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.totobirdcreations.dragonsarise.DragonsArise;

public class ModMiscItems {

    public static final Item DRAGONBONE = ModItems.registerItem("dragonbone", new Item(
            new FabricItemSettings()
                    .group(ItemGroup.MISC)
                    .rarity(Rarity.UNCOMMON)
                    .fireproof()
    ));

    public static void register() {
        DragonsArise.LOGGER.info("Registering Misc Items.");
    }

}
