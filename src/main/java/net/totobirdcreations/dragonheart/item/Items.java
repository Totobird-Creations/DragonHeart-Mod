package net.totobirdcreations.dragonheart.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;


public class Items {

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DragonHeart.ID, name), item);
    }


    public static void register() {

        DragonHeart.LOGGER.debug("Registering items.");
        DragonItems.register();
        ItemGroups.register();

    }


}
