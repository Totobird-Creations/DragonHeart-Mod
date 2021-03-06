package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonSalt;
import net.totobirdcreations.dragonheart.entity.dragon.util.UuidOp;
import net.totobirdcreations.dragonheart.entity.dragonegg.DragoneggEntity;
import net.totobirdcreations.dragonheart.item.util.ColouredItem;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;
import org.jetbrains.annotations.Nullable;

import java.util.List;


// TODO : Make egg able to hatch in inventory.
public class Dragonegg<D extends DragonEntity, T extends DragoneggEntity<D>> extends Item implements ColouredItem {

    public EntityType<T>           entity;
    public DragonEntity.DragonType type;


    public Dragonegg(Settings settings, EntityType<T> entity, DragonEntity.DragonType type) {
        super(settings);
        this.entity = entity;
        this.type   = type;
    }


    public int nbtGetInt(NbtCompound nbt, String key, int fallback) {
        return nbt.contains(key, NbtElement.INT_TYPE)
                ? nbt.getInt(key)
                : fallback;
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
        World       world = context.getWorld();
        T           egg   = entity.create(world);
        BlockPos    pos   = context.getBlockPos().add(context.getSide().getVector());
        NbtCompound nbt   = stack.getOrCreateNbt();
        assert egg != null;
        assert nbt != null;
        egg.setPosition(Vec3d.ofBottomCenter(pos));
        egg.setYaw(new java.util.Random().nextFloat((float)(Math.PI * 2.0f)));

        Random rand = Random.create(DragonSalt.AGE + UuidOp.uuidToInt(egg.getUuid()));
        egg.setColour   (nbtGetInt(nbt, "Colour"    , nbtGetInt(stack.getOrCreateSubNbt("display"), "color", RGBColour.WHITE.asInt()) ));
        egg.setAge      (nbtGetInt(nbt, "Age"       , 0                                                                               ));
        egg.setSpawnAge (nbtGetInt(nbt, "SpawnAge"  , rand.nextBetween(DragoneggEntity.MIN_SPAWN_AGE, DragoneggEntity.MAX_SPAWN_AGE)  ));
        egg.setEyeColour(nbtGetInt(nbt, "EyeColour" , RGBColour.WHITE.asInt()                                                         ));

        world.spawnEntity(egg);

        SoundEvent placeSound = egg.getPlaceSound();
        if (placeSound != null) {
            world.playSound(egg.getX(), egg.getY(), egg.getZ(), placeSound, egg.getSoundCategory(), egg.getSoundVolume(), egg.getSoundPitch(), true);
        }

        return ActionResult.CONSUME;
    }


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            RGBColour[] options = this.type.getDragoneggCreativeColourOptions();
            this.appendStack(stacks, null);
            for (RGBColour option : options) {
                this.appendStack(stacks, option);
            }
        }
    }

    public void appendStack(DefaultedList<ItemStack> stacks, @Nullable RGBColour colour) {
        Item        item  = colour != null ? this.type.getDragoneggItem() : this.type.getDragoneggCreativeItem();
        ItemStack   stack = new ItemStack(item);
        NbtCompound nbt;
        if (colour != null) {
            nbt = stack.getOrCreateSubNbt("display");
            nbt.putInt("color", colour.asInt());
        }
        stacks.add(stack);
    }




    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltips, TooltipContext context) {
        if (this.isCreative(stack)) {
            tooltips.add(Text.translatable("item.dragonheart.dragonegg_creative.tooltip.0"));
            tooltips.add(Text.translatable("item.dragonheart.dragonegg_creative.tooltip.1"));
        }
        super.appendTooltip(stack, world, tooltips, context);
    }

    @Override
    public boolean isFireproof() {return true;}

}
