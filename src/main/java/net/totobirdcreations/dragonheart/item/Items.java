package net.totobirdcreations.dragonheart.item;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;


public class Items {

    public static net.minecraft.item.Item registerItem(String name, net.minecraft.item.Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DragonHeart.ID, name), item);
    }


    public static void register() {

        DragonHeart.LOGGER.debug("Registering items.");
        DragonItems.register();
        ItemGroups.register();

    }


}
