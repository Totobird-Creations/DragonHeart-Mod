package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;



public class FoodItems {


    @SuppressWarnings("unused")
    public static final Item DRAGONMEAT_FIRE = registerItem(
            "dragonmeat_fire",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.FOOD)
                            .fireproof ()
                            .food(FoodItemComponents.DRAGONMEAT_FIRE)
            )
    );


    @SuppressWarnings("unused")
    public static final Item DRAGONMEAT_ICE = registerItem(
            "dragonmeat_ice",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.FOOD)
                            .fireproof ()
                            .food(FoodItemComponents.DRAGONMEAT_ICE)
            )
    );


    @SuppressWarnings("unused")
    public static final Item DRAGONMEAT_LIGHTNING = registerItem(
            "dragonmeat_lightning",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroup.FOOD)
                            .fireproof ()
                            .food(FoodItemComponents.DRAGONMEAT_LIGHTNING)
            )
    );


    public static final Item DRAGONMEAL = registerItem(
            "dragonmeal",
            new Item(
                    new FabricItemSettings().group(ItemGroup.FOOD)
            )
    );


    public static void register() {}


}
