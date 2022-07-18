package net.totobirdcreations.dragonheart.item.group;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.DragonforgeBlocks;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;



public class ModItemGroups {


    public static final ItemGroup DRAGONFORGE = registerItemGroup(
            "dragonforge",
            new ItemStack(Items.AIR)
    );


    public static ItemGroup registerItemGroup(String name, ItemStack icon) {
        return FabricItemGroupBuilder.build(
                new Identifier(DragonHeart.MOD_ID, name),
                () -> icon
        );
    }


    public static void register() {
        DragonHeart.LOGGER.info("Registering item groups.");
    }


}
