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
import net.totobirdcreations.dragonheart.util.helper.DataHelper;

import java.util.HashMap;


public class NestStructureProcessor extends StructureProcessor {

    public static final NestStructureProcessor        INSTANCE = new NestStructureProcessor();
    public static final Codec<NestStructureProcessor> CODEC    = Codec.unit(() -> INSTANCE);

    public static final HashMap<Block, Chance> CHANCES = new HashMap<>();
    static {
        CHANCES.put(Blocks. WHITE_WOOL            , new Chance( 1.0f  , false ));
        CHANCES.put(Blocks. ORANGE_WOOL           , new Chance( 0.75f , false ));
        CHANCES.put(Blocks. MAGENTA_WOOL          , new Chance( 0.5f  , false ));
        CHANCES.put(Blocks. LIGHT_BLUE_WOOL       , new Chance( 0.25f , false ));
        CHANCES.put(Blocks. WHITE_TERRACOTTA      , new Chance( 1.0f  , true  ));
        CHANCES.put(Blocks. ORANGE_TERRACOTTA     , new Chance( 0.75f , true  ));
        CHANCES.put(Blocks. MAGENTA_TERRACOTTA    , new Chance( 0.5f  , true  ));
        CHANCES.put(Blocks. LIGHT_BLUE_TERRACOTTA , new Chance( 0.25f , true  ));
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
        Chance chance = CHANCES.get(currentInfo.state.getBlock());
        if (chance != null) {
            // Block is wool with chance attached.
            if (random.nextFloat() <= chance.threshold) {
                // If chance is matched, place griefed block.
                NbtCompound nbt = currentInfo.nbt != null
                        ? currentInfo.nbt
                        : new NbtCompound();
                nbt.putString("type", id.toString());
                return new StructureTemplate.StructureBlockInfo(
                        currentInfo.pos,
                        DragonBlocks.DRAGON_GRIEFED
                                .getDefaultState(),
                        nbt
                );
            } else {
                // Chance not matched.
                if (chance.failIsVoid) {
                    return originalInfo;
                } else {
                    return new StructureTemplate.StructureBlockInfo(
                            currentInfo.pos,
                            Blocks.AIR.getDefaultState(),
                            currentInfo.nbt
                    );
                }
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


    public record Chance(float threshold, boolean failIsVoid) {}

}
