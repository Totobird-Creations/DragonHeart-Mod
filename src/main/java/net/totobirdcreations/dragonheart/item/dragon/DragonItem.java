package net.totobirdcreations.dragonheart.item.dragon;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.ItemHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;


public interface DragonItem extends DragonColouredItem {


    default Text getNameI(ItemStack stack) {
        return Text.translatable("dragon." + DragonHeart.ID + "." + this.getNameId(),
                DragonResourceLoader.getResource(
                        NbtHelper.getItemStackDragonType(stack)
                ).getName()
        );
    }
    default String getNameId() {
        return Registry.ITEM.getId((Item)this).getPath();
    };


    default void appendStacksI(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (ItemHelper.isInGroup((Item) this, group)) {
            Set<Identifier> identifiers = DragonResourceLoader.getIdentifiers();
            this.appendStacks(stacks);
            for (Identifier identifier : identifiers) {
                this.appendStacks(stacks, identifier, DragonResourceLoader.getResource(identifier));
            }
        }
    }
    void appendStacks(DefaultedList<ItemStack> stacks);
    void appendStacks(DefaultedList<ItemStack> stacks, Identifier id, DragonResourceLoader.DragonResource resource);


    default void appendTooltipI(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {
        if (context.isAdvanced()) {
            tooltips.add(Text.translatable("text.debug." + DragonHeart.ID + ".dragon.type",
                    NbtHelper.getItemStackDragonType(stack)
            ).formatted(Formatting.GRAY));
        }
    }


}
