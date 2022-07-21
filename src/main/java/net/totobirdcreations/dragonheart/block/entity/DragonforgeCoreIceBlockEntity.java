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
import net.totobirdcreations.dragonheart.client.screen.DragonforgeCoreIceScreenHandler;
import org.jetbrains.annotations.Nullable;



public class DragonforgeCoreIceBlockEntity extends DragonforgeCoreTypeBlockEntity {


    public DragonforgeCoreIceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRAGONFORGE_CORE_ICE, pos, state);
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + DragonHeart.MOD_ID + ".dragonforge_core_ice");
    }


    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new DragonforgeCoreIceScreenHandler(syncId, inv, this, this.propertyDelegate);
    }


    @Override
    public boolean isCorrectForgeType(RequiredForgeType requiredForgeType) {
        return requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.ANY ||
                requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.ICE ||
                requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.FIRE_OR_ICE ||
                requiredForgeType == DragonforgeCoreTypeRecipe.RequiredForgeType.ICE_OR_LIGHTNING;
    }


}
