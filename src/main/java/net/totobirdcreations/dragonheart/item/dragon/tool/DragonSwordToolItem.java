package net.totobirdcreations.dragonheart.item.dragon.tool;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.item.dragon.DragonItem;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;

import javax.annotation.Nullable;
import java.util.List;


public class DragonSwordToolItem extends SwordItem implements DragonItem {

    public final boolean typedDragonItem;


    public DragonSwordToolItem(ToolMaterial toolMaterial, Settings settings, boolean colouredItem) {
        super(toolMaterial, 3, -2.4f, settings);
        this.typedDragonItem = colouredItem;
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        if (! typedDragonItem) {
            ItemStack stack = new ItemStack(this);
            stacks.add(stack);
        } else {
            for (Identifier id : DragonResourceLoader.getIdentifiers()) {
                ItemStack stack = new ItemStack(this);
                DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(id);
                DragonColouredItem.setColour(stack, resource.colourGlow());
                NbtCompound nbt = stack.getOrCreateNbt();
                nbt.putString("dragon", id.toString());
                stacks.add(stack);
            }
        }
    }
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource) {}





    @Override
    public Text getName(ItemStack stack) {return this.getNameI(stack);}
    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {this.appendStacksI(group, stacks);}
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {this.appendTooltipI(stack, world, tooltips, context);}
}
