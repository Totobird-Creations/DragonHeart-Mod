package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public abstract class DragonItem extends Item implements DragonColouredItem {


    public DragonItem(Settings settings) {
        super(settings);
    }


    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable("dragon." + DragonHeart.ID + "." + this.getNameId(),
                DragonResourceLoader.getResource(
                        NbtHelper.getItemStackDragonType(stack)
                ).getName()
        );
    }
    public abstract String getNameId();


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            Set<Identifier> identifiers = DragonResourceLoader.getIdentifiers();
            for (Identifier identifier : identifiers) {
                this.appendStacks(stacks, identifier, DragonResourceLoader.getResource(identifier));
            }
        }
    }
    public abstract void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource);


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {
        super.appendTooltip(stack, world, tooltips, context);
        if (context.isAdvanced()) {
            tooltips.add(Text.translatable("text.debug." + DragonHeart.ID + ".dragon.type",
                    NbtHelper.getItemStackDragonType(stack)
            ).formatted(Formatting.GRAY));
        }
    }


    @Override
    public boolean isIn(ItemGroup group) {
        return (group == ItemGroup.SEARCH || group == ItemGroups.DRAGON);
    }


}
