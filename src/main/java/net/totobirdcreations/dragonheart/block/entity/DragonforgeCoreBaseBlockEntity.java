package net.totobirdcreations.dragonheart.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.*;
import net.totobirdcreations.dragonheart.item.DragonforgeItems;
import net.totobirdcreations.dragonheart.screen.DragonforgeCoreBaseScreenHandler;
import net.totobirdcreations.dragonheart.util.ImplementedInventory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;



public class DragonforgeCoreBaseBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {


    public static final int DELEGATE_PROGRESS        = 0;
    public static final int DELEGATE_MAXPROGRESS     = 1;
    public static final int DELEGATE_CONVERSIONMODE  = 2;

    public static final int CONVERSIONMODE_NONE      = 0;
    public static final int CONVERSIONMODE_FIRE      = 1;
    public static final int CONVERSIONMODE_ICE       = 2;
    public static final int CONVERSIONMODE_LIGHTNING = 3;

    enum ConversionType {
        BRICKS,
        APERTURE,
        HATCH,
        SUPPORT,
        CORE,
        WINDOW
    }


    public  DefaultedList<ItemStack> items               = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public  int                      progress            = 0;
    public  int                      maxProgress         = 200;
    public  int                      conversionMode      = CONVERSIONMODE_NONE;
    public  boolean                  completedConversion = false;
    public  boolean                  isDischarging       = false;

    public final PropertyDelegate propertyDelegate;


    public DragonforgeCoreBaseBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRAGONFORGE_CORE_BASE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case    DELEGATE_PROGRESS       -> DragonforgeCoreBaseBlockEntity.this.progress;
                    case    DELEGATE_MAXPROGRESS    -> DragonforgeCoreBaseBlockEntity.this.maxProgress;
                    case    DELEGATE_CONVERSIONMODE -> DragonforgeCoreBaseBlockEntity.this.conversionMode;
                    default                         -> 0;
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case DELEGATE_PROGRESS       -> DragonforgeCoreBaseBlockEntity.this.progress       = value;
                    case DELEGATE_MAXPROGRESS    -> DragonforgeCoreBaseBlockEntity.this.maxProgress    = value;
                    case DELEGATE_CONVERSIONMODE -> DragonforgeCoreBaseBlockEntity.this.conversionMode = value;
                }
            }
            @Override
            public int size() {
                return 3;
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


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new DragonforgeCoreBaseScreenHandler(syncId, inv, this, this.propertyDelegate);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        progress = nbt.getShort("progress");
        completedConversion = nbt.getBoolean("completedConversion");
        conversionMode = nbt.getShort("conversionMode");
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putShort("conversionMode", (short)conversionMode);
        nbt.putBoolean("completedConversion", completedConversion);
        nbt.putShort("progress", (short)progress);
        Inventories.writeNbt(nbt, items);
        super.writeNbt(nbt);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragonforgeCoreBaseBlockEntity entity) {
        int newConversionMode = entity.getTargetConversionMode();
        entity.isDischarging = ((entity.progress <= 0 && newConversionMode == CONVERSIONMODE_NONE) || (entity.progress > 0 && newConversionMode != entity.conversionMode) || (! state.get(DragonforgeCore.POWERED)) || (entity.completedConversion));
        if (entity.progress <= 0) {
            entity.conversionMode = newConversionMode;
        }
        if (entity.isDischarging) {
            entity.progress -= 10;
        } else {
            entity.progress += 1;
        }
        if (entity.progress <= 0) {
            entity.progress            = 0;
            entity.completedConversion = false;
        } else if (entity.progress > entity.maxProgress) {
            entity.progress            = entity.maxProgress;
            entity.completedConversion = true;
            entity.convert(world, pos, entity);
        }
    }


    public void convert(World world, BlockPos pos, DragonforgeCoreBaseBlockEntity entity) {

        int currentConversionMode = getTargetConversionMode();
        entity.removeStack(0, 1);
        boolean shouldConvertSelf = true;

        BlockPos[] blockPositions = {
                pos.add(-1, -1, -1),
                pos.add(-1, -1, 0),
                pos.add(-1, -1, 1),
                pos.add(-1, 0, -1),
                pos.add(-1, 0, 0),
                pos.add(-1, 0, 1),
                pos.add(-1, 1, -1),
                pos.add(-1, 1, 0),
                pos.add(-1, 1, 1),
                pos.add(0, -1, -1),
                pos.add(0, -1, 0),
                pos.add(0, -1, 1),
                pos.add(0, 0, -1),
                pos.add(0, 0, 1),
                pos.add(0, 1, -1),
                pos.add(0, 1, 0),
                pos.add(0, 1, 1),
                pos.add(1, -1, -1),
                pos.add(1, -1, 0),
                pos.add(1, -1, 1),
                pos.add(1, 0, -1),
                pos.add(1, 0, 0),
                pos.add(1, 0, 1),
                pos.add(1, 1, -1),
                pos.add(1, 1, 0),
                pos.add(1, 1, 1)
        };
        ArrayList<BlockPos> blockPositionOptions = new ArrayList<>();
        for (BlockPos blockPosition : blockPositions) {
            BlockState blockState = world.getBlockState(blockPosition);
            if (blockState.isOf(DragonforgeBlocks.DRAGONFORGE_BRICKS_BASE.block) || blockState.isOf(DragonforgeBlocks.DRAGONFORGE_APERTURE_BASE.block) || blockState.isOf(DragonforgeBlocks.DRAGONFORGE_HATCH_BASE.block) || blockState.isOf(DragonforgeBlocks.DRAGONFORGE_SUPPORT_BASE.block) || blockState.isOf(DragonforgeBlocks.DRAGONFORGE_WINDOW_BASE)) {
                shouldConvertSelf = false;
                blockPositionOptions.add(blockPosition);
            }
        }
        BlockPos blockPosition = pos;
        if (! shouldConvertSelf) {
            int index = (int) (Math.random() * blockPositionOptions.size());
            blockPosition = blockPositionOptions.get(index);
        }
        ConversionType conversionType = ConversionType.BRICKS;
        BlockState     blockState     = world.getBlockState(blockPosition);
        if (blockState.isOf(DragonforgeBlocks.DRAGONFORGE_APERTURE_BASE.block)) {
            conversionType = ConversionType.APERTURE;
        } else if (blockState.isOf(DragonforgeBlocks.DRAGONFORGE_HATCH_BASE.block)) {
            conversionType = ConversionType.HATCH;
        } else if (blockState.isOf(DragonforgeBlocks.DRAGONFORGE_SUPPORT_BASE.block)) {
            conversionType = ConversionType.SUPPORT;
        } else if (blockState.isOf(DragonforgeBlocks.DRAGONFORGE_CORE_BASE.block)) {
            conversionType = ConversionType.CORE;
        } else if (blockState.isOf(DragonforgeBlocks.DRAGONFORGE_WINDOW_BASE)) {
            conversionType = ConversionType.WINDOW;
        }
        BlockState newBlockState = DragonforgeBlocks.DRAGONFORGE_BRICKS_FIRE.block.getDefaultState();
        if (conversionType == ConversionType.BRICKS) {
            //if      (currentConversionMode == CONVERSIONMODE_FIRE)       {newBlockState = DragonforgeBlocks.DRAGONFORGE_BRICKS_FIRE      .block.getDefaultState();}
            if      (currentConversionMode == CONVERSIONMODE_ICE)        {newBlockState = DragonforgeBlocks.DRAGONFORGE_BRICKS_ICE       .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_LIGHTNING)  {newBlockState = DragonforgeBlocks.DRAGONFORGE_BRICKS_LIGHTNING .block.getDefaultState();}
        } else if (conversionType == ConversionType.APERTURE) {
            if      (currentConversionMode == CONVERSIONMODE_FIRE)       {newBlockState = DragonforgeBlocks.DRAGONFORGE_APERTURE_FIRE      .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_ICE)        {newBlockState = DragonforgeBlocks.DRAGONFORGE_APERTURE_ICE       .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_LIGHTNING)  {newBlockState = DragonforgeBlocks.DRAGONFORGE_APERTURE_LIGHTNING .block.getDefaultState();}
        } else if (conversionType == ConversionType.HATCH) {
            if      (currentConversionMode == CONVERSIONMODE_FIRE)       {newBlockState = DragonforgeBlocks.DRAGONFORGE_HATCH_FIRE      .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_ICE)        {newBlockState = DragonforgeBlocks.DRAGONFORGE_HATCH_ICE       .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_LIGHTNING)  {newBlockState = DragonforgeBlocks.DRAGONFORGE_HATCH_LIGHTNING .block.getDefaultState();}
        } else if (conversionType == ConversionType.SUPPORT) {
            if      (currentConversionMode == CONVERSIONMODE_FIRE)       {newBlockState = DragonforgeBlocks.DRAGONFORGE_SUPPORT_FIRE      .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_ICE)        {newBlockState = DragonforgeBlocks.DRAGONFORGE_SUPPORT_ICE       .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_LIGHTNING)  {newBlockState = DragonforgeBlocks.DRAGONFORGE_SUPPORT_LIGHTNING .block.getDefaultState();}
        } else if (conversionType == ConversionType.CORE) {
            if      (currentConversionMode == CONVERSIONMODE_FIRE)       {newBlockState = DragonforgeBlocks.DRAGONFORGE_CORE_FIRE      .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_ICE)        {newBlockState = DragonforgeBlocks.DRAGONFORGE_CORE_ICE       .block.getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_LIGHTNING)  {newBlockState = DragonforgeBlocks.DRAGONFORGE_CORE_LIGHTNING .block.getDefaultState();}
        } else { // conversionType == ConversionType.WINDOW
            if      (currentConversionMode == CONVERSIONMODE_FIRE)       {newBlockState = DragonforgeBlocks.DRAGONFORGE_WINDOW_FIRE      .getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_ICE)        {newBlockState = DragonforgeBlocks.DRAGONFORGE_WINDOW_ICE       .getDefaultState();}
            else if (currentConversionMode == CONVERSIONMODE_LIGHTNING)  {newBlockState = DragonforgeBlocks.DRAGONFORGE_WINDOW_LIGHTNING .getDefaultState();}
        }
        BlockEntity blockEntity    = world.getBlockEntity(blockPosition);
        Clearable.clear(blockEntity);
        world.setBlockState(blockPosition, newBlockState);
        newBlockState = world.getBlockState(blockPosition);
        if (conversionType == ConversionType.BRICKS) {
            ((DragonforgeBrick)newBlockState.getBlock()).update(world, blockPosition, newBlockState);
        } else if (conversionType == ConversionType.APERTURE) {
            ((DragonforgeAperture)newBlockState.getBlock()).update(world, blockPosition);
        } else if (conversionType == ConversionType.HATCH) {
            ((DragonforgeHatch)newBlockState.getBlock()).update(world, blockPosition, newBlockState);
        } else if (conversionType == ConversionType.CORE) {
            ((DragonforgeCore)newBlockState.getBlock()).update(world, blockPosition);
        } else if (conversionType == ConversionType.WINDOW) {
            ((DragonforgeWindow)newBlockState.getBlock()).update(world, blockPosition);
        }

    }


    public int getTargetConversionMode() {
        if (getStack(0).isOf(DragonforgeItems.POWERCELL_FIRE)) {
            return CONVERSIONMODE_FIRE;
        } else if (getStack(0).isOf(DragonforgeItems.POWERCELL_ICE)) {
            return CONVERSIONMODE_ICE;
        } else if (getStack(0).isOf(DragonforgeItems.POWERCELL_LIGHTNING)) {
            return CONVERSIONMODE_LIGHTNING;
        } else {
            return CONVERSIONMODE_NONE;
        }
    }


}
