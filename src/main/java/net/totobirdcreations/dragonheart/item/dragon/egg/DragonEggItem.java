package net.totobirdcreations.dragonheart.item.dragon.egg;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonSalt;
import net.totobirdcreations.dragonheart.entity.dragon.util.UuidOp;
import net.totobirdcreations.dragonheart.entity.dragon_egg.DragonEggEntity;
import net.totobirdcreations.dragonheart.item.dragon.DragonItemImpl;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DragonEggItem extends DragonItemImpl {


    public DragonEggItem(Settings settings) {
        super(settings);
    }


    public boolean isCreative(ItemStack stack) {
        return false;
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        if (this.isCreative(stack)) {
            return super.useOnBlock(context);
        }
        World           world = context.getWorld();
        DragonEggEntity egg   = Entities.DRAGON_EGG.create(world);
        BlockPos        pos   = context.getBlockPos().add(context.getSide().getVector());
        NbtCompound     nbt   = stack.getOrCreateNbt();
        assert egg != null;
        assert nbt != null;
        egg.setPosition(Vec3d.ofBottomCenter(pos));

        Random random = Random.create(DragonSalt.AGE + UuidOp.uuidToInt(egg.getUuid()));
        egg.setDragon   (NbtHelper.getItemDragonType(stack));
        egg.setColour   (NbtHelper.getInt    (nbt, "colour"    , RGBColour.WHITE.asInt()                                                          ));
        egg.setAge      (NbtHelper.getInt    (nbt, "age"       , 0                                                                                ));
        egg.setSpawnAge (NbtHelper.getInt    (nbt, "spawnAge"  , random.nextBetween(DragonEggEntity.MIN_SPAWN_AGE, DragonEggEntity.MAX_SPAWN_AGE) ));
        egg.setEyeColour(NbtHelper.getInt    (nbt, "eyeColour" , RGBColour.WHITE.asInt()                                                          ));
        DataHelper.randomiseEntityRotation(egg);

        world.spawnEntity(egg);

        SoundEvent placeSound = egg.getPlaceSound();
        if (placeSound != null) {
            world.playSound(egg.getX(), egg.getY(), egg.getZ(), placeSound, egg.getSoundCategory(), egg.getSoundVolume(), egg.getSoundPitch(), true);
        }

        return ActionResult.CONSUME;
    }


    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {}
    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks, Identifier identifier, DragonResourceLoader.DragonResource resource) {
        Collection<RGBColour> colours = resource.creativeEggColours();
        this.appendStack(stacks, identifier, null);
        for (RGBColour colour : colours) {
            this.appendStack(stacks, identifier, colour);
        }
    }

    public void appendStack(DefaultedList<ItemStack> stacks, Identifier dragon, @Nullable RGBColour colour) {
        Item      item  = colour != null
                ? DragonItems.DRAGON_EGG
                : DragonItems.DRAGON_EGG_CREATIVE;
        ItemStack stack = new ItemStack(item);
        NbtHelper.setItemDragonType(stack, dragon);
        if (colour != null) {
            DragonColouredItem.setColour(stack, colour.asInt());
        }
        stacks.add(stack);
    }



    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {
        if (this.isCreative(stack)) {
            tooltips.add(Text.translatable("item." + DragonHeart.ID + ".dragonegg_creative.tooltip.0").formatted(Formatting.YELLOW));
            tooltips.add(Text.translatable("item." + DragonHeart.ID + ".dragonegg_creative.tooltip.1").formatted(Formatting.YELLOW));
        }
        super.appendTooltip(stack, world, tooltips, context);
        if (context.isAdvanced()) {
            if (! this.isCreative(stack)) {
                tooltips.add(Text.translatable("text.debug." + DragonHeart.ID + ".dragon.colour",
                        DragonColouredItem.getColour(stack).toString()
                ).formatted(Formatting.GRAY));
            }
        }
    }

    @Override
    public boolean isFireproof() {return true;}

}
