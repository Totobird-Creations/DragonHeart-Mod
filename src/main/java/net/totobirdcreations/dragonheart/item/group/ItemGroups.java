package net.totobirdcreations.dragonheart.item.group;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;


public class ItemGroups {


    public static final ItemGroup DRAGON = registerItemGroup(
            "dragon",
            new ItemStack(DragonItems.DRAGON_EGG_CREATIVE)
    );


    public static ItemGroup registerItemGroup(String name, ItemStack icon) {
        return FabricItemGroupBuilder.build(
                new Identifier(DragonHeart.ID, name),
                () -> icon
        );
    }


    public static void register() {}


}
