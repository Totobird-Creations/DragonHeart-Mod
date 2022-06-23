package net.totobirdcreations.dragonheart.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.recipe.DragonforgeCoreTypeRecipe;
import net.totobirdcreations.dragonheart.recipe.DragonforgeCoreTypeRecipe.RequiredForgeType;
import net.totobirdcreations.dragonheart.screen.DragonforgeCoreLightningScreenHandler;
import org.jetbrains.annotations.Nullable;



public class DragonforgeCoreLightningBlockEntity extends DragonforgeCoreTypeBlockEntity {


    public DragonforgeCoreLightningBlockEntity(BlockPos pos, BlockState state) {

        super(ModBlockEntities.DRAGONFORGE_CORE_LIGHTNING, pos, state);

    }


    @Override
    public Text getDisplayName() {

        return Text.translatable("container." + DragonHeart.MOD_ID + ".dragonforge_core_lightning");

    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {

        return new DragonforgeCoreLightningScreenHandler(syncId, inv, this, this.propertyDelegate);

    }


    @Override
    public boolean isCorrectForgeType(RequiredForgeType requiredForgeType) {

        return requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.ANY ||
                requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.LIGHTNING ||
                requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.FIRE_OR_LIGHTNING ||
                requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.ICE_OR_LIGHTNING;

    }


}
