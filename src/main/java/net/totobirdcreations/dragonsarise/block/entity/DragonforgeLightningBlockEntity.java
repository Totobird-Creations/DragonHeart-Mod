package net.totobirdcreations.dragonsarise.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonsarise.DragonsArise;
import net.totobirdcreations.dragonsarise.item.ModMiscItems;
import net.totobirdcreations.dragonsarise.item.ModSwordItems;
import net.totobirdcreations.dragonsarise.item.inventory.ImplementedInventory;
import net.totobirdcreations.dragonsarise.screen.DragonforgeLightningScreenHandler;
import org.jetbrains.annotations.Nullable;

public class DragonforgeLightningBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private static BlockPos blockPos;

    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(3, ItemStack.EMPTY);

    public DragonforgeLightningBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRAGONFORGE_LIGHTNING_BLOCK_ENTITY, pos, state);
        blockPos = pos;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("displayname." + DragonsArise.MOD_ID + ".dragonforge_lightning");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        DragonforgeLightningScreenHandler screenHandler = new DragonforgeLightningScreenHandler(syncId, inv, this);
        screenHandler.setBlockPos(blockPos);
        return screenHandler;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
         return inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
        super.writeNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, DragonforgeLightningBlockEntity entity) {
        if (hasRecipe(entity) && world.isThundering() && hasNotReachedStackLimit(entity)) {
            craftItem(entity);
            if (! world.isClient()) {
                EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world, null, null, null, pos,
                        SpawnReason.TRIGGERED, true, true
                );
            }
        }
    }

    private static boolean hasRecipe(DragonforgeLightningBlockEntity entity) {
        boolean hasItemInSlot0 = entity.getStack(0).getItem() == ModMiscItems.DRAGONBONE;
        boolean hasItemInSlot1 = entity.getStack(1).getItem() == ModSwordItems.DRAGONBONE_SWORD;
        return (hasItemInSlot0 && hasItemInSlot1);
    }

    private static boolean hasNotReachedStackLimit(DragonforgeLightningBlockEntity entity) {
        return entity.getStack(2).getCount() < entity.getStack(2).getMaxCount();
    }

    public static void craftItem(DragonforgeLightningBlockEntity entity) {
        entity.removeStack(0, 1);
        entity.removeStack(1, 1);
        entity.setStack(2, new ItemStack(ModMiscItems.DRAGONBONE, entity.getStack(2).getCount() + 1));
    }

}
