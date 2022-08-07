package net.totobirdcreations.dragonheart.structure;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.WorldView;
import net.totobirdcreations.dragonheart.block.dragon.DragonBlocks;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class DragonNestStructureProcessor extends StructureProcessor {

    public static final DragonNestStructureProcessor        INSTANCE = new DragonNestStructureProcessor();
    public static final Codec<DragonNestStructureProcessor> CODEC    = Codec.unit(() -> INSTANCE);

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

        ArrayList<Identifier> allowedIds = new ArrayList<>();
        for (Identifier id : DragonResourceLoader.getIdentifiers()) {
            DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(id);
            if (world.getBiome(pos).isIn(
                    resource.spawnsIn()
            )) {
                allowedIds.add(id);
            }
        }

        Random random = data.getRandom(pos);
        Identifier id;
        if (allowedIds.size() > 0) {
            int index = allowedIds.size() > 1
                    ? random.nextInt(allowedIds.size())
                    : 0;
            id = allowedIds.get(index);
        } else {
            return originalInfo;
        }


        random        = data.getRandom(currentInfo.pos);
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
        } else if (currentInfo.state.isOf(Blocks.COMMAND_BLOCK)) {
            // Block is command block. Consider as data block.
            if (world instanceof ChunkRegion chunkRegion) {
                ServerWorld serverWorld = chunkRegion.toServerWorld();
                String command = currentInfo.nbt.getString("Command");
                if (command.startsWith("dragon")) {
                    // Data specifies dragon spawn.
                    DragonEntity dragon = Entities.DRAGON.create(serverWorld);
                    assert dragon != null;
                    dragon.setPosition(new Vec3d(
                            currentInfo.pos.getX() + 0.5f,
                            currentInfo.pos.getY(),
                            currentInfo.pos.getZ() + 0.5f
                    ));
                    dragon.setDragon(id.toString());
                    dragon.setAge(random.nextBetween(DragonEntity.MIN_NATURAL_SPAWN_AGE, DragonEntity.MAX_NATURAL_SPAWN_AGE));
                    dragon.setColour(DragonResourceLoader.getResource(id)
                            .chooseBodyColour(dragon.getUuid())
                            .asInt()
                    );
                    float yaw = random.nextFloat() * 360.0f;
                    dragon.setYaw(yaw);
                    dragon.setBodyYaw(yaw);
                    dragon.setHeadYaw(yaw);
                    serverWorld.spawnEntity(dragon);
                }
            }
            return new StructureTemplate.StructureBlockInfo(
                    currentInfo.pos,
                    Blocks.AIR.getDefaultState(),
                    currentInfo.nbt
            );
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
        return Structures.DRAGON_NEST;
    }

}
