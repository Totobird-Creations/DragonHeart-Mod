package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import static net.totobirdcreations.dragonheart.item.Items.registerItem;



public class FoodItems {


    public static final Item DRAGONMEAL = registerItem(
            "dragonmeal",
            new Item(
                    new FabricItemSettings().group(ItemGroup.FOOD)
            )
    );


    public static void register() {}


}
