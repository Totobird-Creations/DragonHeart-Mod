package net.totobirdcreations.dragonheart.item.dragon;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.ItemHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.util.List;


public class DragonBlockItem extends BlockItem implements DragonItem {

    public DragonBlockItem(Block block, Settings settings) {
        super(block, settings);
    }


    public String getNameId() {
        return ((DragonBlock)(this.getBlock())).getNameId();
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {}
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource) {}


    @Override
    public boolean isIn(ItemGroup group) {
        return ItemHelper.isInGroup(this, group);
    }




    @Override
    public Text getName(ItemStack stack) {return this.getNameI(stack);}
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (ItemHelper.isInGroup(this, group)) {
            this.getBlock().appendStacks(group, stacks);
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {this.appendTooltipI(stack, world, tooltips, context);}

}
