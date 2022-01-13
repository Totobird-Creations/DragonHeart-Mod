package net.totobirdcreations.dragonsarise.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totobirdcreations.dragonsarise.DragonsArise;
import net.totobirdcreations.dragonsarise.block.ModBlocks;

public class ModItems {

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(DragonsArise.MOD_ID, name), item);
    }

    public static void register() {
        DragonsArise.LOGGER.info("Registering Items.");
        ModSwordItems.register();
        ModMiscItems.register();
    }

}
