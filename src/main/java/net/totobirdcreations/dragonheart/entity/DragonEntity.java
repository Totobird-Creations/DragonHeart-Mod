package net.totobirdcreations.dragonheart.entity;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public class DragonEntity extends FlyingEntity implements IAnimatable {


    public AnimationFactory animationFactory = new AnimationFactory(this);

    public static final TrackedData<BlockPos> SPAWN_POS;
    public static final TrackedData<Integer>  HUNGER_LEVEL;
    public static final TrackedData<Integer>  COLOUR;
    public static final TrackedData<Integer>  STATE;
    public static final TrackedData<Float>    AGE;

    public enum DragonState {
        SLEEP,
        STAND,
        WALK,
        FLY,
        DIVE;

        public int toInt() {
            return switch (this) {
                case SLEEP -> 0;
                case STAND -> 1;
                case WALK  -> 2;
                case FLY   -> 3;
                case DIVE  -> 4;
            };
        }
        public static DragonState fromInt(int from) {
            return switch (from) {
                case    1 -> STAND;
                case    2 -> WALK;
                case    3 -> FLY;
                case    4 -> DIVE;
                default   -> SLEEP;
            };
        }
        public String toString() {
            return switch (this) {
                case SLEEP -> "sleep";
                case STAND -> "stand";
                case WALK  -> "walk";
                case FLY   -> "fly";
                case DIVE  -> "dive";
            };
        }
        public static DragonState fromString(String from) {
            return switch (from) {
                case    "stand" -> STAND;
                case    "walk"  -> WALK;
                case    "fly"   -> FLY;
                case    "dive"  -> DIVE;
                default         -> SLEEP;
            };
        }
    }

    static {
        SPAWN_POS       = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS );
        HUNGER_LEVEL    = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER   );
        COLOUR          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER   );
        STATE           = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER   );
        AGE             = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.FLOAT     );
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH           , 1000.0d )
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE        , 50.0f   )
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK     , 3.0f    )
                .add(EntityAttributes.GENERIC_ATTACK_SPEED         , 3.0f    )
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE , 1.0f    );
    }


    public DragonEntity(EntityType<? extends FlyingEntity> entityType, World world) {

        super(entityType, world);
        this.ignoreCameraFrustum = true;
        moveControl = new DragonEntityMoveController(this);

    }


    public void setState(DragonState state) {

        this.dataTracker.set(STATE, state.toInt());

    }


    public PlayState animationPredicate(AnimationEvent<DragonEntity> event) {

        DragonState state = DragonState.fromInt(this.dataTracker.get(STATE));

        AnimationBuilder builder   = new AnimationBuilder();
        PlayState        playState = PlayState.CONTINUE;

        switch (state) {
            case STAND -> builder.addAnimation("animation.dragon.stand" , true);
            case FLY   -> builder.addAnimation("animation.dragon.fly"   , true);
        }

        event.getController().transitionLengthTicks = 20;
        event.getController().setAnimation(builder);
        return playState;

    }


    @Override
    public void registerControllers(AnimationData data) {

        data.addAnimationController(new AnimationController<DragonEntity>(this, "controller", 0, this::animationPredicate));

    }

    @Override
    public AnimationFactory getFactory() {

        return animationFactory;

    }


    @Override
    public void initDataTracker() {

        super.initDataTracker();

        this.dataTracker.startTracking( SPAWN_POS       , new BlockPos(0, 0, 0) );
        this.dataTracker.startTracking( HUNGER_LEVEL    , 0                     );
        this.dataTracker.startTracking( COLOUR          , 0                     );
        this.dataTracker.startTracking( STATE           , 0                     );
        this.dataTracker.startTracking( AGE             , 0.0f                  );

    }


    public void writeCustomDataToNbt(NbtCompound nbt) {

        super.writeCustomDataToNbt(nbt);
        BlockPos spawnPos = dataTracker.get(SPAWN_POS);
        nbt.putIntArray ( "SpawnPos"       , new int[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()} );
        nbt.putInt      ( "HungerLevel"    , dataTracker.get(HUNGER_LEVEL)                                );
        nbt.putInt      ( "Colour"         , dataTracker.get(COLOUR)                                      );
        nbt.putInt      ( "State"          , dataTracker.get(STATE)                                       );
        nbt.putFloat    ( "Age"            , dataTracker.get(AGE)                                         );

    }


    public void readCustomDataFromNbt(NbtCompound nbt) {

        dataTracker.set( AGE             , nbt.getFloat("Age"            ));
        dataTracker.set( STATE           , nbt.getInt  ("State"          ));
        dataTracker.set( COLOUR          , nbt.getInt  ("Colour"         ));
        dataTracker.set( HUNGER_LEVEL    , nbt.getInt  ("HungerLever"    ));
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

        dataTracker.set( SPAWN_POS       , getBlockPos() );
        dataTracker.set( HUNGER_LEVEL    , 20 * 60 * 15  );
        dataTracker.set( COLOUR          , 16777215      );
        dataTracker.set( STATE           , 0             );
        dataTracker.set( AGE             , 0.0f          );
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;

    }


    @Override
    public boolean hasNoGravity() {
       return false;
    }


    @Override
    public void initGoals() {
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        this.goalSelector.add(1, new LookAroundGoal(this));
    }


}
