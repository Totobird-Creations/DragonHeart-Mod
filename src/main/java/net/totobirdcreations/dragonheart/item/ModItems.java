package net.totobirdcreations.dragonheart.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.axe.AxeItems;
import net.totobirdcreations.dragonheart.item.hoe.HoeItems;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;
import net.totobirdcreations.dragonheart.item.pickaxe.PickaxeItems;
import net.totobirdcreations.dragonheart.item.shovel.ShovelItems;
import net.totobirdcreations.dragonheart.item.sword.SwordItems;


public class ModItems {

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DragonHeart.MOD_ID, name), item);
    }


    public static void register() {

        DragonHeart.LOGGER.info("Registering items.");
        MiscItems.register();
        DragonforgeItems .register();
        FoodItems        .register();

        SwordItems.register();
        PickaxeItems.register();
        AxeItems.register();
        ShovelItems.register();
        HoeItems.register();
        ArmourItems      .register();

    }


}
