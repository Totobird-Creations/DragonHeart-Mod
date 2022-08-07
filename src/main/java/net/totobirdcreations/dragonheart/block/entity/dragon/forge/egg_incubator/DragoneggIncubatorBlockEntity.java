package net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.DragonForgeBlockEntity;
import net.totobirdcreations.dragonheart.item.dragon.DragonBreathItem;
import net.totobirdcreations.dragonheart.particle.Particles;
import net.totobirdcreations.dragonheart.screen_handler.DragonEggIncubatorScreenHandler;
import net.totobirdcreations.dragonheart.util.helper.InventoryHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;


public class DragoneggIncubatorBlockEntity extends DragonForgeBlockEntity implements NamedScreenHandlerFactory, InventoryHelper {

    public static int INVENTORY_SIZE = 1;

    public       DefaultedList<ItemStack> items      = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    public final PropertyDelegate         properties;
    public       int                      power      = 0;
    public       int                      maxPower   = 0;


    public DragoneggIncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_EGG_INCUBATOR, pos, state);
        this.properties = new DragoneggIncubatorBlockEntityProperties(this);
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + DragonHeart.ID + ".dragon_egg_incubator");
    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new DragonEggIncubatorScreenHandler(syncId, inventory, this, this.properties);
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        this.power    = nbt.getInt( "power"    );
        this.maxPower = nbt.getInt( "maxPower" );
        Inventories.readNbt(nbt, this.items);
        super.readNbt(nbt);
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt( "power"    , this.power    );
        nbt.putInt( "maxPower" , this.maxPower );
        Inventories.writeNbt(nbt, this.items);
        super.writeNbt(nbt);
    }


    public static void tick(World world, BlockPos pos, BlockState state, DragoneggIncubatorBlockEntity entity) {
        if (entity.power > 0) {
            entity.power -= 1;
            if (entity.power <= 0) {
                world.setBlockState(pos, state.with(Properties.POWERED, false));
            } else {
                Particles.createDragonForgeFlame(world, pos, entity.dragon);
            }
        }
        if (entity.power <= 0) {
            ItemStack stack = entity.getStack(0);
            if (stack.getItem() instanceof DragonBreathItem) {
                Identifier type = NbtHelper.getItemDragonType(stack);
                entity.setDragon(type);
                entity.maxPower = 1200;
                entity.power    = entity.maxPower;
                world.setBlockState(pos, state.with(Properties.POWERED, true));
                entity.removeStack(0, 1);
            }
        }
    }

}
