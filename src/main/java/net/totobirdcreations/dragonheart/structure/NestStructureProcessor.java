package net.totobirdcreations.dragonheart.structure;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class NestStructureProcessor extends StructureProcessor {

    public static final NestStructureProcessor INSTANCE = new NestStructureProcessor();
    public static final Codec<NestStructureProcessor> CODEC    = Codec.unit(() -> INSTANCE);

    public static final HashMap<Block, Float>               CHANCES = new HashMap<>();
    static {
        CHANCES.put(Blocks.WHITE_WOOL, 1.0f);
        CHANCES.put(Blocks.ORANGE_WOOL, 0.75f);
        CHANCES.put(Blocks.MAGENTA_WOOL, 0.5f);
        CHANCES.put(Blocks.LIGHT_BLUE_WOOL, 0.25f);
    }

    @Override
    public StructureTemplate.StructureBlockInfo process(
            WorldView world, BlockPos pos, BlockPos pivot,
            StructureTemplate.StructureBlockInfo originalInfo,
            StructureTemplate.StructureBlockInfo currentInfo,
            StructurePlacementData data
    ) {

        Identifier id = DataHelper.chooseDragonTypeForPos(world, pos, data.getRandom(pos));
        if (id == null) {
            return originalInfo;
        }

        Random random = data.getRandom(currentInfo.pos);
        Float  chance = CHANCES.get(currentInfo.state.getBlock());
        if (chance != null) {
            // Block is wool with chance attached.
            if (random.nextFloat() <= chance) {
                // If chance is matched, place griefed block.
                NbtCompound nbt = currentInfo.nbt != null
                        ? currentInfo.nbt
                        : new NbtCompound();
                nbt.putString("dragon", id.toString());
                return new StructureTemplate.StructureBlockInfo(
                        currentInfo.pos,
                        DragonBlocks.DRAGON_GRIEFED
                                .getDefaultState(),
                        nbt
                );
            } else {
                // Chance not matched.
                return new StructureTemplate.StructureBlockInfo(
                        currentInfo.pos,
                        Blocks.AIR.getDefaultState(),
                        currentInfo.nbt
                );
            }
        } else if (currentInfo.state.isOf(Blocks.AIR) && originalInfo.state.isOf(Blocks.WATER)) {
            // Handle underwater spawning.
            return new StructureTemplate.StructureBlockInfo(
                    currentInfo.pos,
                    Blocks.WATER.getDefaultState(),
                    currentInfo.nbt
            );
        }
        // Nothing matches, just allow pass through.
        return currentInfo;
    }


    @Override
    public StructureProcessorType<?> getType() {
        return Structures.NEST_PROCESSOR_TYPE;
    }

}
