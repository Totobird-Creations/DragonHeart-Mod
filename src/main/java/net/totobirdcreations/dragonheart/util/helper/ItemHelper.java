package net.totobirdcreations.dragonheart.util.helper;

import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.totobirdcreations.dragonheart.item.dragon.DragonItem;
import net.totobirdcreations.dragonheart.item.ItemGroups;

import javax.annotation.Nullable;


public class ItemHelper {

    @Nullable
    public static net.minecraft.item.ItemGroup getItemGroup(Item item) {
        if (item instanceof SwordItem) {
            return net.minecraft.item.ItemGroup.COMBAT;
        } else if (item instanceof ToolItem) {
            return net.minecraft.item.ItemGroup.TOOLS;
        } else if (item instanceof DragonItem) {
            return ItemGroups.DRAGON;
        } else {
            return item.getGroup();
        }
    }

    public static boolean isInGroup(Item item, net.minecraft.item.ItemGroup group) {
        net.minecraft.item.ItemGroup itemGroup = getItemGroup(item);
        return itemGroup != null && (itemGroup == net.minecraft.item.ItemGroup.SEARCH || itemGroup == group);
    }

}
