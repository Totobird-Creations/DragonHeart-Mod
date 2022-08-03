package net.totobirdcreations.dragonheart.util.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.totobirdcreations.dragonheart.item.dragon.DragonItem;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;

import javax.annotation.Nullable;


public class ItemHelper {

    @Nullable
    public static ItemGroup getItemGroup(Item item) {
        if (item instanceof SwordItem) {
            return ItemGroup.COMBAT;
        } else if (item instanceof ToolItem) {
            return ItemGroup.TOOLS;
        } else if (item instanceof DragonItem) {
            return ItemGroups.DRAGON;
        } else {
            return item.getGroup();
        }
    }

    public static boolean isInGroup(Item item, ItemGroup group) {
        ItemGroup itemGroup = getItemGroup(item);
        return itemGroup != null && (itemGroup == ItemGroup.SEARCH || itemGroup == group);
    }

}
