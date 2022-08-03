package net.totobirdcreations.dragonheart.block.entity.dragon.forge.core;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlock;
import net.totobirdcreations.dragonheart.block.dragon.forge.DragonForgeBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.*;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.*;
import net.totobirdcreations.dragonheart.recipe.DragonForgeCoreRecipe;
import net.totobirdcreations.dragonheart.recipe.Recipes;
import net.totobirdcreations.dragonheart.screenhandler.DragonForgeCoreScreenHandler;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;
import net.totobirdcreations.dragonheart.util.helper.InventoryHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class DragonForgeCoreBlockEntity extends DragonForgeBlockEntity implements NamedScreenHandlerFactory, InventoryHelper {

    public static final Relation<DragonForgeApertureBlockEntity> APERTURE_SIDE = new Relation<>(
            DragonBlockEntities.DRAGON_FORGE_APERTURE,
            new Vec3i(-1, 0, 0),
            new Vec3i(1, 0, 0),
            new Vec3i(0, 0, -1),
            new Vec3i(0, 0, 1)
    );
    public static final Relation<DragonForgeHatchBlockEntity> HATCH_SIDE = new Relation<>(
            DragonBlockEntities.DRAGON_FORGE_HATCH,
            new Vec3i(-1, 0, 0),
            new Vec3i(1, 0, 0),
            new Vec3i(0, 0, -1),
            new Vec3i(0, 0, 1)
    );
    public static final Relation<DragonForgeSupportBlockEntity> SUPPORT_CORNER = new DragonBlockEntity.Relation<>(
            DragonBlockEntities.DRAGON_FORGE_SUPPORT,
            new Vec3i(-1, -1, -1),
            new Vec3i(-1, -1, 1),
            new Vec3i(1, -1, -1),
            new Vec3i(1, -1, 1),
            new Vec3i(-1, 1, -1),
            new Vec3i(-1, 1, 1),
            new Vec3i(1, 1, -1),
            new Vec3i(1, 1, 1)
    );
    public static final DragonBlockEntity.Relation<DragonForgeBricksBlockEntity> BRICKS = new DragonBlockEntity.Relation<>(
            DragonBlockEntities.DRAGON_FORGE_BRICKS,
            new Vec3i(-1, -1, 0),
            new Vec3i(1, -1, 0),
            new Vec3i(0, -1, -1),
            new Vec3i(0, -1, 1),
            new Vec3i(0, -1, 0),
            new Vec3i(-1, 1, 0),
            new Vec3i(1, 1, 0),
            new Vec3i(0, 1, -1),
            new Vec3i(0, 1, 1),
            new Vec3i(0, 1, 0),
            new Vec3i(-1, 0, 0),
            new Vec3i(-1, 0, -1),
            new Vec3i(0, 0, -1),
            new Vec3i(1, 0, -1),
            new Vec3i(1, 0, 0),
            new Vec3i(1, 0, 1),
            new Vec3i(0, 0, 1),
            new Vec3i(-1, 0, 1)
    );

    public static int INVENTORY_SIZE = 4;


    public       DefaultedList<ItemStack> inventory   = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    public final PropertyDelegate         properties;
    public       int                      progress    = 0;
    public       int                      maxProgress = 0;


    public DragonForgeCoreBlockEntity(BlockPos pos, BlockState state) {
        super(DragonBlockEntities.DRAGON_FORGE_CORE, pos, state);
        properties = new DragonForgeCoreBlockEntityProperties(this);

    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + DragonHeart.ID + ".dragon_forge_core");
    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new DragonForgeCoreScreenHandler(syncId, inventory, this, this.properties);
    }


    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.progress    = nbt.getInt( "progress"    );
        this.maxProgress = nbt.getInt( "maxProgress" );
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt( "progress"    , this. progress    );
        nbt.putInt( "maxProgress" , this. maxProgress );
    }



    public static void tick(World world, BlockPos pos, BlockState state, DragonForgeCoreBlockEntity entity) {
        tickState      (world, pos, state                    , entity);
        tickRecipe     (world,      world.getBlockState(pos) , entity);
    }


    public static void tickState(World world, BlockPos pos, BlockState state, DragonForgeCoreBlockEntity entity) {
        boolean powered = false;

        for (DragonForgeApertureBlockEntity relation : entity.getRelation(APERTURE_SIDE)) {
            if (world.getBlockState(relation.getPos()).get(Properties.POWERED)) {
                powered = true;
                break;
            }
        }
        if (powered) {
            if (entity.getRelation(BRICKS).size() != 16) {
                powered = false;
            } else if (entity.getRelation(SUPPORT_CORNER).size() != 8) {
                powered = false;
            } else if (entity.getRelation(HATCH_SIDE).size() != 1) {
                powered = false;
            }
        }

        world.setBlockState(pos, state
                .with(Properties.POWERED, powered)
        );
    }


    public static void tickRecipe(World world, BlockState state, DragonForgeCoreBlockEntity entity) {
        SimpleInventory       inventory = generateInventory(entity);
        DragonForgeCoreRecipe recipe    = getRecipe(world, state, entity, inventory);
        if (recipe != null) {
            entity.maxProgress  = recipe.timeTicks;
            entity.progress    += 1;
            if (entity.progress > entity.maxProgress) {
                craftItem(entity, inventory, recipe);
            }
        } else {
            entity.progress = 0;
        }
    }


    public static SimpleInventory generateInventory(DragonForgeCoreBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }
        return inventory;
    }


    @Nullable
    public static DragonForgeCoreRecipe getRecipe(World world, BlockState state, DragonForgeCoreBlockEntity entity, SimpleInventory inventory) {
        if (! state.contains(Properties.POWERED) || ! state.get(Properties.POWERED)) {
            return null;
        }
        if (inventory.getStack(3).getItem() != Items.FIRE_CHARGE) {
            return null;
        }
        List<DragonForgeCoreRecipe> recipes = world.getRecipeManager().getAllMatches(Recipes.DRAGON_FORGE_CORE.type(), inventory, world);
        DragonForgeCoreRecipe       result  = null;
        for (DragonForgeCoreRecipe recipe : recipes) {
            if (
                    DataHelper.dragonTypeMatches(entity.dragon, recipe.dragonType) &&
                    recipe.matches(inventory, entity)
            ) {
                result = recipe;
                break;
            }
        }
        if (result != null) {
            if (! canInsert(inventory, result.output, result)) {
                result = null;
            }
        }

        return result;
    }


    public static boolean canInsert(SimpleInventory inventory, ItemStack output, DragonForgeCoreRecipe recipe) {
        ItemStack stack = inventory.getStack(2);
        if (stack.isEmpty()) {
            return true;
        }
        if (! (
                output.getItem() == stack.getItem() &&
                stack.getCount() + output.getCount() <= stack.getMaxCount()
        )) {
            return false;
        }
        int typeSource = recipe.getTypeSource();
        if (typeSource != -1) {
            return NbtHelper.getItemStackDragonType(inventory.getStack(typeSource)) ==
                    NbtHelper.getItemStackDragonType(output);
        }
        return true;
    }


    public static void craftItem(DragonForgeCoreBlockEntity entity, SimpleInventory inventory, DragonForgeCoreRecipe recipe) {
        ItemStack stack = recipe.craft(inventory);
        stack.setCount(inventory.getStack(2).getCount() + stack.getCount());
        entity.setStack(2, stack);
        entity.removeStack(0, 1);
        entity.removeStack(1, 1);
        entity.removeStack(3, 1);
        entity.progress = 0;
    }

}
