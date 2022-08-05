package net.totobirdcreations.dragonheart.item.dragon.bucket;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.dragon.DragonItemImpl;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class EmptyDragonBucketItem extends DragonItemImpl {

    public EmptyDragonBucketItem(Settings settings) {
        super(settings);
    }


    public boolean isEmpty() {
        return true;
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        ItemStack stack = new ItemStack(this);
        stacks.add(stack);
    }
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource) {}



    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {
        super.appendTooltip(stack, world, tooltips, context);
        if (context.isAdvanced()) {
            tooltips.add(Text.translatable("text.debug." + DragonHeart.ID + ".dragon.colour",
                    DragonColouredItem.getColour(stack).toString()
            ).formatted(Formatting.GRAY));
        }
    }

    @Override
    public boolean isFireproof() {return true;}

}
