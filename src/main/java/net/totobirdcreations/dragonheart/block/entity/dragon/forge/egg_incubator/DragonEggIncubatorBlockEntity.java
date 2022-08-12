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
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.item.dragon.DragonBreathItem;
import net.totobirdcreations.dragonheart.particle_effect.ParticleEffects;
import net.totobirdcreations.dragonheart.screen_handler.DragonEggIncubatorScreenHandler;
import net.totobirdcreations.dragonheart.util.helper.InventoryHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;


public class DragonEggIncubatorBlockEntity extends DragonForgeBlockEntity implements NamedScreenHandlerFactory, InventoryHelper {

    public static int INVENTORY_SIZE = 1;

    public       DefaultedList<ItemStack> items      = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    public final PropertyDelegate         properties;
    public       int                      time    = 0;
    public       int                      maxTime = 0;


    public DragonEggIncubatorBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_EGG_INCUBATOR, pos, state);
        this.properties = new DragonEggIncubatorBlockEntityProperties(this);
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
        this.time = nbt.getInt( "power"    );
        this.maxTime = nbt.getInt( "maxPower" );
        Inventories.readNbt(nbt, this.items);
        super.readNbt(nbt);
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt( "power"    , this.time);
        nbt.putInt( "maxPower" , this.maxTime);
        Inventories.writeNbt(nbt, this.items);
        super.writeNbt(nbt);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            if (state.get(Properties.POWERED)) {
                ParticleEffects.createDragonForgeFlame(world, pos, this.power);
            }

        } else {
            if (! this.type.equals(NbtHelper.EMPTY_TYPE)) {
                this.setType(NbtHelper.EMPTY_TYPE);
            }
            if (this.time > 0) {
                this.time -= 1;
                if (this.time <= 0) {
                    world.setBlockState(pos, state.with(Properties.POWERED, false));
                }
            }
            if (this.time <= 0) {
                ItemStack stack = this.getStack(0);
                if (stack.getItem() instanceof DragonBreathItem) {
                    Identifier type = NbtHelper.getItemDragonType(stack);
                    this.setPower(type);
                    this.maxTime = 1200;
                    this.time    = this.maxTime;
                    world.setBlockState(pos, state.with(Properties.POWERED, true));
                    this.removeStack(0, 1);
                }
            }
        }
    }


    @Nullable
    public Relation<DragonForgeCoreBlockEntity> getCoreRelation() {
        return null;
    }

}
