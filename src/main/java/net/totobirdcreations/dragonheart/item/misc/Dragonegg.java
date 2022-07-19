package net.totobirdcreations.dragonheart.item.misc;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvent;
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


public class Dragonegg<D extends DragonEntity, T extends DragoneggEntity<D>> extends Item implements ColouredItem {

    public EntityType<T>           entity;
    public DragonEntity.DragonType type;


    public Dragonegg(Settings settings, EntityType<T> entity, DragonEntity.DragonType type) {
        super(settings);
        this.entity = entity;
        this.type   = type;
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World       world = context.getWorld();
        T           egg   = entity.create(world);
        BlockPos    pos   = context.getBlockPos().add(context.getSide().getVector());
        ItemStack   stack = context.getStack();
        NbtCompound nbt   = stack.getOrCreateNbt();
        assert egg != null;
        assert nbt != null;
        egg.setPosition(Vec3d.ofBottomCenter(pos));
        egg.setYaw(new java.util.Random().nextFloat((float)(Math.PI * 2.0f)));

        Random rand = Random.create(DragonSalt.AGE + UuidOp.uuidToInt(egg.getUuid()));
        egg.setColour   (nbtGetInt(nbt, "Colour"    , nbtGetInt(stack.getOrCreateSubNbt("display"), "color", 16777215)               ));
        egg.setAge      (nbtGetInt(nbt, "Age"       , 0                                                                              ));
        egg.setSpawnAge (nbtGetInt(nbt, "SpawnAge"  , rand.nextBetween(DragoneggEntity.MIN_SPAWN_AGE, DragoneggEntity.MAX_SPAWN_AGE) ));
        egg.setEyeColour(nbtGetInt(nbt, "EyeColour" , 16777215                                                                       ));

        world.spawnEntity(egg);

        SoundEvent placeSound = egg.getPlaceSound();
        if (placeSound != null) {
            world.playSound(egg.getX(), egg.getY(), egg.getZ(), placeSound, egg.getSoundCategory(), egg.getSoundVolume(), egg.getSoundPitch(), true);
        }

        return ActionResult.CONSUME;
    }


    public int nbtGetInt(NbtCompound nbt, String key, int fallback) {
        if (nbt.contains(key, NbtElement.INT_TYPE)) {
            return nbt.getInt(key);
        } else {
            return fallback;
        }
    }


    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            Item        item    = this.type.getDragoneggItem();
            RGBColour[] options = this.type.getBaseColourOptions();
            if (this.type != DragonEntity.DragonType.ICE) {
                stacks.add(new ItemStack(item));
            }
            for (RGBColour option : options) {
                ItemStack   stack = new ItemStack(item);
                NbtCompound nbt   = stack.getOrCreateSubNbt("display");
                nbt.putInt("color", option.asInt());
                stacks.add(stack);
            }
        }
    }

}
