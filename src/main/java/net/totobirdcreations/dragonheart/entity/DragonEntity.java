package net.totobirdcreations.dragonheart.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.goal.dragon.SearchAnimal;
import net.totobirdcreations.dragonheart.entity.goal.dragon.ToSpawn;
import org.jetbrains.annotations.Nullable;


public class DragonEntity extends PathAwareEntity {


    public static final TrackedData<BlockPos> SPAWN_POS;
    public static final TrackedData<Integer>  HUNGER_LEVEL;
    public static final TrackedData<Integer>  COLOUR;

    static {
        SPAWN_POS    = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS );
        HUNGER_LEVEL = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER   );
        COLOUR       = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER   );
    }


    public DragonEntity(EntityType<? extends PathAwareEntity> entityType, World world) {

        super(entityType, world);

    }


    @Override
    public void initGoals() {

        int priority = 0;
        this.goalSelector.add(++priority, new ToSpawn(this));
        this.goalSelector.add(priority, new SearchAnimal(this));

    }


    @Override
    public void initDataTracker() {

        super.initDataTracker();

        this.dataTracker.startTracking( SPAWN_POS    , new BlockPos(0, 0, 0) );
        this.dataTracker.startTracking( HUNGER_LEVEL , 0                     );
        this.dataTracker.startTracking( COLOUR       , 0                     );

    }


    public void writeCustomDataToNbt(NbtCompound nbt) {

        super.writeCustomDataToNbt(nbt);
        BlockPos spawnPos = dataTracker.get(SPAWN_POS);
        nbt.putIntArray ( "SpawnPos"    , new int[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()} );
        nbt.putInt      ( "HungerLevel" , dataTracker.get(HUNGER_LEVEL)                                );
        nbt.putInt      ( "Colour"      , dataTracker.get(COLOUR)                                      );

    }


    public void readCustomDataFromNbt(NbtCompound nbt) {

        dataTracker.set( COLOUR       , nbt.getInt("Colour")      );
        dataTracker.set( HUNGER_LEVEL , nbt.getInt("HungerLever") );
        int[] spawnPos = nbt.getIntArray("SpawnPos");
        if (spawnPos.length == 3) {
            dataTracker.set(SPAWN_POS, new BlockPos(spawnPos[0], spawnPos[1], spawnPos[2]));
        } else {
            dataTracker.set(SPAWN_POS, getBlockPos());
        }
        super.readCustomDataFromNbt(nbt);

    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {

        dataTracker.set( SPAWN_POS    , getBlockPos() );
        dataTracker.set( HUNGER_LEVEL , 20 * 60 * 15  );
        dataTracker.set( COLOUR       , 16777215      );
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;

    }


}
