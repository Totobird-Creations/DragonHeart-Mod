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
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntities;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.*;
import net.totobirdcreations.dragonheart.resource.recipe.DragonForgeCoreRecipe;
import net.totobirdcreations.dragonheart.resource.recipe.RecipeResources;
import net.totobirdcreations.dragonheart.screen_handler.DragonForgeCoreScreenHandler;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;
import net.totobirdcreations.dragonheart.util.helper.InventoryHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
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
    public static final DragonBlockEntity.Relation<? extends DragonForgeBlockEntity> ALL_RELATIONS = DragonBlockEntity.Relation.empty(null)
            .addOffsetsFrom(APERTURE_SIDE)
            .addOffsetsFrom(HATCH_SIDE)
            .addOffsetsFrom(SUPPORT_CORNER)
            .addOffsetsFrom(BRICKS);

    public static int        INVENTORY_SIZE = 4;
    public static Identifier CONVERSION_ID  = new Identifier(DragonHeart.ID, "dragon_forge/conversion");


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



    public void tick(World world, BlockPos pos, BlockState state) {
        this.tickState  (world, pos, state                    );
        this.tickRecipe (world,      world.getBlockState(pos) );
    }


    public void tickState(World world, BlockPos pos, BlockState state) {
        boolean powered = false;

        DragonForgeApertureBlockEntity aperture = this.checkRelation(APERTURE_SIDE, 1);
        if (aperture != null && world.getBlockState(aperture.getPos()).get(Properties.POWERED)) {
            this.setPower(aperture.power);
            powered = true;
        }
        if (powered
                && (this.checkRelation( BRICKS         , 16 ) == null
                ||  this.checkRelation( SUPPORT_CORNER , 8  ) == null
                ||  this.checkRelation( HATCH_SIDE     , 1  ) == null
        )) {
            powered = false;
        }

        world.setBlockState(pos, state
                .with(Properties.POWERED, powered)
        );
    }


    @Nullable
    public <T extends DragonForgeBlockEntity> T checkRelation(Relation<T> relationObj, @Nullable Integer size) {
        return this.checkRelation(relationObj, size, false, false);
    }
    @Nullable
    public <T extends DragonForgeBlockEntity> T checkRelation(Relation<T> relationObj, @Nullable Integer size, boolean sameType, boolean untypedStrict) {
        Collection<T> relations = this.getRelation(relationObj);
        if (size != null && relations.size() != size) {
            return null;
        }
        for (T relation : relations) {
            if (relation.getRelation(relation.getCoreRelation(), sameType, untypedStrict).size() > 1) {
                return null;
            }
        }
        return relations.iterator().next();
    }


    public void tickRecipe(World world, BlockState state) {
        SimpleInventory       inventory = this.generateInventory();
        DragonForgeCoreRecipe recipe    = this.getRecipe(world, state, inventory);
        if (recipe != null) {
            this.maxProgress  = recipe.timeTicks;
            this.progress    += 1;
            if (this.progress > this.maxProgress) {
                this.craftItem(inventory, recipe);
            }
        } else {
            this.progress = 0;
        }
    }


    public SimpleInventory generateInventory() {
        SimpleInventory inventory = new SimpleInventory(this.inventory.size());
        for (int i = 0; i < this.inventory.size(); i++) {
            inventory.setStack(i, this.getStack(i));
        }
        return inventory;
    }


    @Nullable
    public DragonForgeCoreRecipe getRecipe(World world, BlockState state, SimpleInventory inventory) {
        if (! state.get(Properties.POWERED)
                || inventory.getStack(3).getItem() != Items.FIRE_CHARGE
                || (! this.type.equals(NbtHelper.EMPTY_TYPE) && ! this.power.equals(this.type))
        ) {
            return null;
        }
        if (       this.checkRelation( APERTURE_SIDE  , 1  , true, true) == null
                || this.checkRelation( BRICKS         , 16 , true, true) == null
                || this.checkRelation( SUPPORT_CORNER , 8  , true, true) == null
                || this.checkRelation( HATCH_SIDE     , 1  , true, true) == null

        ) {
            return null;
        }
        assert this.world != null;
        List<DragonForgeCoreRecipe> recipes = this.world.getRecipeManager().getAllMatches(RecipeResources.DRAGON_FORGE_CORE.type(), inventory, world);
        DragonForgeCoreRecipe       result  = null;
        for (DragonForgeCoreRecipe recipe : recipes) {
            if (DataHelper.dragonRecipeTypeMatches(this.type, recipe.dragonType)
                    && recipe.matches(inventory, this)
            ) {
                result = recipe;
                break;
            }
        }
        if (result != null) {
            if (! this.canInsert(inventory, result)) {
                result = null;
            }
        }

        return result;
    }


    public boolean canInsert(SimpleInventory inventory, DragonForgeCoreRecipe recipe) {
        ItemStack output = recipe.craft(inventory);
        ItemStack stack  = inventory.getStack(2);

        if (stack.isEmpty()) {
            return true;
        }
        if (output.getItem() != stack.getItem()
                || stack.getCount() + output.getCount() > stack.getMaxCount()
        ) {
            return false;
        }
        return recipe.getTypeSource() == -1 || (
                NbtHelper.getItemDragonType(stack)
                        .equals(NbtHelper.getItemDragonType(output))
        );
    }


    public void craftItem(SimpleInventory inventory, DragonForgeCoreRecipe recipe) {
        ItemStack stack = recipe.craft(inventory);
        stack.setCount(inventory.getStack(2).getCount() + stack.getCount());
        this.setStack(2, stack);
        this.removeStack(0, 1);
        this.removeStack(1, 1);
        this.removeStack(3, 1);
        this.progress = 0;
        if (recipe.getId().equals(CONVERSION_ID)) {
            this.convert();
        }
    }


    public void convert() {
        if (this.type.equals(NbtHelper.EMPTY_TYPE)) {
            ArrayList<? extends DragonBlockEntity> entities = new ArrayList<>(this.getRelation(ALL_RELATIONS, true, false));
            DragonBlockEntity                      entity;
            if (entities.size() == 0) {
                entity = this;
            } else if (entities.size() == 1) {
                entity = entities.get(0);
            } else {
                assert this.world != null;
                entity = entities.get(this.world.getRandom().nextBetween(0, entities.size() - 1));
            }
            entity.setType(this.power);
        }
    }


    @Nullable
    public Relation<DragonForgeCoreBlockEntity> getCoreRelation() {
        return null;
    }

}
