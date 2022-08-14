package net.totobirdcreations.dragonheart.item.dragon.bucket;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.entity.DragonEggEntity;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonSalt;
import net.totobirdcreations.dragonheart.item.dragon.DragonItemImpl;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class DragonBucketItem extends DragonItemImpl {

    public DragonBucketItem(Settings settings) {
        super(settings);
    }


    public boolean isEmpty() {
        return true;
    }

    @Nullable
    public Entity createEntity(World world) {return null;}


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

    @Override
    public ActionResult useOnEntity(ItemStack usedStack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (this.isEmpty() && entity.isAlive() && ! entity.isRemoved()) {
            Item        item      = null;
            NbtCompound entityNbt = new NbtCompound();
            entity.writeNbt(entityNbt);
            Identifier dragonType = null;
            RGBColour  colour     = null;
            if (entity instanceof DragonEggEntity dragonEggEntity) {
                item       = DragonItems.DRAGON_EGG_DRAGON_BUCKET;
                dragonType = dragonEggEntity.getDragonType();
                colour     = dragonEggEntity.getColour();
            } else if (entity instanceof DragonEntity dragonEntity) {
                if (dragonEntity.isTamedOwner(user)) {
                    item       = DragonItems.DRAGON_DRAGON_BUCKET;
                    dragonType = dragonEntity.getDragonType();
                    colour     = dragonEntity.getColour();
                }
            }
            if (item != null) {
                ItemStack stack = new ItemStack(item);
                stack.getOrCreateNbt().put("storedEntity", entityNbt);
                NbtHelper.setItemDragonType  (stack, dragonType );
                DragonColouredItem.setColour (stack, colour     );
                user.setStackInHand(hand, stack);
                entity.discard();
            }
        }
        return super.useOnEntity(usedStack, user, entity, hand);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (! world.isClient()) {
            if (! this.isEmpty()) {
                Entity entity = this.createEntity(world);
                if (entity != null) {
                    // Calculate position.
                    BlockPos blockPos = context.getBlockPos().offset(context.getSide());
                    Vec3d    pos      = Vec3d.ofBottomCenter(blockPos);
                    // Check can fit.
                    entity.setPosition(pos);
                    if (! entity.collidesWithStateAtPos(blockPos, world.getBlockState(blockPos))) {
                        // Read entity nbt.
                        entity.readNbt(context.getStack().getOrCreateSubNbt("storedEntity"));
                        // Set position and rotation.
                        entity.setPosition(pos);
                        DataHelper.randomiseEntityRotation(entity);
                        // Spawn entity and update player item.
                        context.getWorld().spawnEntity(entity);
                        PlayerEntity player = context.getPlayer();
                        if (player != null) {
                            player.setStackInHand(context.getHand(), new ItemStack(DragonItems.DRAGON_BUCKET));
                        }
                        return ActionResult.CONSUME;
                    } else {
                        entity.discard();
                    }
                }
            }
            return ActionResult.FAIL;
        } else {
            return ActionResult.SUCCESS;
        }
    }

}
