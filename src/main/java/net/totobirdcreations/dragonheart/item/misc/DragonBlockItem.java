package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;


public class DragonBlockItem extends BlockItem {

    public DragonBlockItem(Block block, Settings settings) {
        super(block, settings);
    }


    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable("dragon." + DragonHeart.ID + "." + this.getNameId(),
                DragonResourceLoader.getResource(
                        NbtHelper.getItemStackDragonType(stack)
                ).getName()
        );
    }
    public String getNameId() {
        return ((DragonBlock)(getBlock())).getNameId();
    }


    @Override
    public boolean isIn(ItemGroup group) {
        return (group == ItemGroup.SEARCH || group == ItemGroups.DRAGON);
    }

}
