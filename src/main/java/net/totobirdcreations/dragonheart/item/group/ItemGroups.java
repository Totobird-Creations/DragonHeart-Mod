package net.totobirdcreations.dragonheart.item.group;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;


public class ItemGroups {


    public static final ItemGroup DRAGON = registerItemGroup(
            "dragon",
            new ItemStack(MiscItems.DRAGONEGG_CREATIVE)
    );


    public static ItemGroup registerItemGroup(String name, ItemStack icon) {
        return FabricItemGroupBuilder.build(
                new Identifier(DragonHeart.ID, name),
                () -> icon
        );
    }


    public static void register() {
        DragonHeart.LOGGER.info("Registering item groups.");
    }


}
