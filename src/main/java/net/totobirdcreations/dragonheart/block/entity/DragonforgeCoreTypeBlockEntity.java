package net.totobirdcreations.dragonheart.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.DragonforgeCoreType;
import net.totobirdcreations.dragonheart.recipe.DragonforgeCoreTypeRecipe;
import net.totobirdcreations.dragonheart.recipe.DragonforgeCoreTypeRecipe.RequiredForgeType;
import net.totobirdcreations.dragonheart.recipe.DragonforgeCoreTypeRecipeType;
import net.totobirdcreations.dragonheart.util.ImplementedInventory;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;



public class DragonforgeCoreTypeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {


    public   static final int DELEGATE_PROGRESS    = 0;
    public   static final int DELEGATE_MAXPROGRESS = 1;

    public DefaultedList<ItemStack> items       = DefaultedList.ofSize(3, ItemStack.EMPTY);
    public int                      progress    = 0;
    public int                      maxProgress = 140;

    public final PropertyDelegate propertyDelegate;


    public DragonforgeCoreTypeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {

        super(type, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case DELEGATE_PROGRESS    : return DragonforgeCoreTypeBlockEntity.this.progress;
                    case DELEGATE_MAXPROGRESS : return DragonforgeCoreTypeBlockEntity.this.maxProgress;
                    default : return 0;
                }
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case DELEGATE_PROGRESS    : DragonforgeCoreTypeBlockEntity.this.progress    = value ; break;
                    case DELEGATE_MAXPROGRESS : DragonforgeCoreTypeBlockEntity.this.maxProgress = value ; break;
                }
            }
            @Override
            public int size() {
                return 2;
            }
        };

    }


    @Override
    public Text getDisplayName() {

        return Text.translatable("container." + DragonHeart.MOD_ID + ".dragonforge_core_base");

    }


    @Override
    public DefaultedList<ItemStack> getItems() {

        return items;

    }


    @Override
    public void readNbt(NbtCompound nbt) {

        progress = nbt.getShort("progress");
        Inventories.readNbt(nbt, items);
        super.readNbt(nbt);

    }


    @Override
    public void writeNbt(NbtCompound nbt) {

        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        nbt.putShort("progress", (short)progress);

    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {

        return null;

    }





    public static void tick(World world, BlockPos pos, BlockState state, DragonforgeCoreTypeBlockEntity entity) {

        if (hasRecipe(entity)) {
            entity.progress++;
            if (entity.progress > entity.maxProgress) {
                craftItem(entity);
                entity.progress = 0;
            }
        } else {
            entity.progress = 0;
        }

    }


    public static boolean hasRecipe(DragonforgeCoreTypeBlockEntity entity) {

        World world = entity.getWorld();
        SimpleInventory inventory = new SimpleInventory(entity.items.size());
        for (int i = 0; i < entity.items.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<DragonforgeCoreTypeRecipe> match = world.getRecipeManager()
                .getFirstMatch(DragonforgeCoreTypeRecipeType.INSTANCE, inventory, world);

        if (
                world.getBlockState(entity.getPos()).get(DragonforgeCoreType.POWERED) &&
                match.isPresent() &&
                entity.isCorrectForgeType(match.get().getRequiredForgeType()) &&
                canInsertAmountIntoOutputSlot(inventory, match.get()) &&
                canInsertItemIntoOutputSlot(inventory, match.get())
        ) {
            entity.maxProgress = match.get().getTimeTicks();
            return true;
        } else {
            return false;
        }

    }


    public boolean isCorrectForgeType(RequiredForgeType requiredForgeType) {

        return requiredForgeType == RequiredForgeType.ANY;

    }


    public static void craftItem(DragonforgeCoreTypeBlockEntity entity) {

        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.items.size());
        for (int i = 0; i < entity.items.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<DragonforgeCoreTypeRecipe> match = world.getRecipeManager()
                .getFirstMatch(DragonforgeCoreTypeRecipeType.INSTANCE, inventory, world);

        if (match.isPresent()) {
            entity.removeStack(0, 1);
            entity.removeStack(1, 1);
            entity.setStack(2, new ItemStack(match.get().getOutput().getItem(), entity.getStack(2).getCount() + match.get().getOutput().getCount()));
        }

    }


    public static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory, DragonforgeCoreTypeRecipe recipe) {

        return inventory.getStack(2).getCount() + recipe.getOutput().getCount() <= inventory.getStack(2).getMaxCount();

    }


    public static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, DragonforgeCoreTypeRecipe recipe) {

        return inventory.getStack(2).getItem() == recipe.getOutput().getItem() || inventory.getStack(2).isEmpty();

    }


}
