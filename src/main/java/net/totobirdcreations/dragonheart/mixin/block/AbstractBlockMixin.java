package net.totobirdcreations.dragonheart.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.totobirdcreations.dragonheart.block.entity.dragon.DragonGriefedBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Redirect(
            method = "calcBlockBreakingDelta",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"
            )
    )
    public float getHardness(BlockState state, BlockView world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof DragonGriefedBlockEntity griefedEntity) {
            if (griefedEntity.resetState != null) {
                return griefedEntity.resetState.getHardness(world, pos) / 2.0f;
            }
        }
        return state.getHardness(world, pos);
    }

}
