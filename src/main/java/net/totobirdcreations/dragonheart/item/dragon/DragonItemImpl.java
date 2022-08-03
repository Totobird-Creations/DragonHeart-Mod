package net.totobirdcreations.dragonheart.item.dragon;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


public abstract class DragonItemImpl extends Item implements DragonItem {

    public DragonItemImpl(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {return this.getNameI(stack);}
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {this.appendStacksI(group, stacks);}
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {this.appendTooltipI(stack, world, tooltips, context);}

}
