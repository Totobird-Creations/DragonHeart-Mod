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
import net.totobirdcreations.dragonheart.client.screen.DragonforgeCoreFireScreenHandler;
import org.jetbrains.annotations.Nullable;



public class DragonforgeCoreFireBlockEntity extends DragonforgeCoreTypeBlockEntity {


    public DragonforgeCoreFireBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRAGONFORGE_CORE_FIRE, pos, state);
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + DragonHeart.MOD_ID + ".dragonforge_core_fire");
    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new DragonforgeCoreFireScreenHandler(syncId, inv, this, this.propertyDelegate);
    }


    @Override
    public boolean isCorrectForgeType(RequiredForgeType requiredForgeType) {
        return requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.ANY ||
                requiredForgeType == RequiredForgeType.FIRE ||
                requiredForgeType == RequiredForgeType.FIRE_OR_ICE ||
                requiredForgeType == RequiredForgeType.FIRE_OR_LIGHTNING;
    }


}
