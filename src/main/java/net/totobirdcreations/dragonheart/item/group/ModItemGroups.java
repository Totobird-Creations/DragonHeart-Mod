package net.totobirdcreations.dragonheart.item.group;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;



public class ModItemGroups {


    public static final Item DRAGONFORGE_CATEGORY = registerItem(
            "category_dragonforge",
            new Item(new FabricItemSettings().group(null))
    );

    public static final ItemGroup DRAGONFORGE = registerItemGroup(
            "dragonforge",
            DRAGONFORGE_CATEGORY
    );


    public static ItemGroup registerItemGroup(String name, Item item) {

        return FabricItemGroupBuilder.build(
                new Identifier(DragonHeart.MOD_ID, name),
                () -> new ItemStack(item)
        );

    }


    public static void register() {

        DragonHeart.LOGGER.info("Registering item groups.");

    }


}
