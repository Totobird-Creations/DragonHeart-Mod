package net.totobirdcreations.dragonheart.block.entity.dragon;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
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
import net.totobirdcreations.dragonheart.screenhandler.DragoneggIncubatorScreenHandler;
import net.totobirdcreations.dragonheart.util.helper.InventoryHelper;
import org.jetbrains.annotations.Nullable;


public class DragoneggIncubatorBlockEntity extends DragonBlockEntity implements NamedScreenHandlerFactory, InventoryHelper {

    public static final int POWER_TIME = 20;

    public static final int DELEGATE_POWER = 0;

    public DefaultedList<ItemStack> items  = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public int                      power  = 0;

    public final PropertyDelegate propertyDelegate;


    public DragoneggIncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGONEGG_INCUBATOR, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case DELEGATE_POWER -> DragoneggIncubatorBlockEntity.this.power;
                    default                         -> 0;
                };
            }
            @Override
            public void set(int index, int value) {
                switch (index) {
                    case DELEGATE_POWER -> DragoneggIncubatorBlockEntity.this.power = value;
                }
            }
            @Override
            public int size() {
                return 1;
            }
        };
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + DragonHeart.ID + ".dragonegg_incubator");
    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new DragoneggIncubatorScreenHandler(syncId, inventory, this);
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        this.power  = nbt.getInt("power");
        Inventories.readNbt(nbt, this.items);
        super.readNbt(nbt);
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("power", this.power);
        Inventories.writeNbt(nbt, this.items);
        super.writeNbt(nbt);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragoneggIncubatorBlockEntity entity) {}

}
