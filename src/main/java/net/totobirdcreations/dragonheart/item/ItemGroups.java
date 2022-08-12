package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;


public class ItemGroups {


    public static final net.minecraft.item.ItemGroup DRAGON = registerItemGroup(
            "dragon",
            new ItemStack(DragonItems.DRAGON_EGG_CREATIVE)
    );


    public static net.minecraft.item.ItemGroup registerItemGroup(String name, ItemStack icon) {
        return FabricItemGroupBuilder.build(
                new Identifier(DragonHeart.ID, name),
                () -> icon
        );
    }


    public static void register() {}


}
