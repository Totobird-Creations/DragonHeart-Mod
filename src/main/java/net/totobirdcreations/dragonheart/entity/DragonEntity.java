package net.totobirdcreations.dragonheart.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.VibrationListener;
import net.totobirdcreations.dragonheart.DragonHeart;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;


public abstract class DragonEntity extends MobEntity implements IAnimatable, VibrationListener.Callback {

    public static final TagKey<GameEvent> VIBRATIONS = TagKey.of(Registry.GAME_EVENT_KEY, new Identifier(DragonHeart.MOD_ID, "dragon_can_listen"));

    public AnimationFactory animationFactory = new AnimationFactory(this);

    public static final TrackedData<BlockPos> SPAWN_POS;
    public static final TrackedData<Integer>  HUNGER_LEVEL;
    public static final TrackedData<Integer>  COLOUR;
    public static final TrackedData<Integer>  STATE;
    public static final TrackedData<Float>    AGE;

    enum DragonType {
        NONE,
        FIRE,
        ICE,
        LIGHTNING
    }

    public enum DragonState {
        SLEEP,
        NEST;

        public int toInt() {
            return switch (this) {
                case SLEEP -> 0;
                case NEST  -> 1;
            };
        }
        public static DragonState fromInt(int from) {
            return switch (from) {
                case    0 -> SLEEP;
                case    1 -> NEST;
                default   -> SLEEP;
            };
        }
        public String toString() {
            return switch (this) {
                case SLEEP -> "sleep";
                case NEST  -> "nest";
            };
        }
        public static DragonState fromString(String from) {
            return switch (from) {
                case    "sleep" -> SLEEP;
                case    "nest"  -> NEST;
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


    public final EntityGameEventHandler<VibrationListener> vibrationListener = new EntityGameEventHandler(new VibrationListener(new EntityPositionSource(this, this.getStandingEyeHeight()), 16, this, null, 0.0F, 0));


    public DragonEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.moveControl         = new DragonEntityMoveController(this);
    }


    public void setState(DragonState state) {
        this.dataTracker.set(STATE, state.toInt());
    }


    public PlayState animationPredicate(AnimationEvent<DragonEntity> event) {

        DragonState state = DragonState.fromInt(this.dataTracker.get(STATE));

        AnimationBuilder builder   = new AnimationBuilder();
        PlayState        playState = PlayState.CONTINUE;

        if (state == DragonState.SLEEP) {
            builder.addAnimation("animation.dragon.sleep", true);
            event.getController().transitionLengthTicks = 20;
            event.getController().easingType            = EasingType.EaseInOutCubic;
        } else {
            if (isOnGround()) {
                builder.addAnimation("animation.dragon.stand", true);
                event.getController().transitionLengthTicks = 10;
                event.getController().easingType            = EasingType.EaseInOutCubic;
            } else {
                builder.addAnimation("animation.dragon.fly", true);
                event.getController().transitionLengthTicks = 10;
                event.getController().easingType            = EasingType.Linear;
            }
        }
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


    public abstract DragonType getDragonType();


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        dataTracker.set( SPAWN_POS    , getBlockPos()                                                                   );
        dataTracker.set( HUNGER_LEVEL , 20 * 60 * 15                                                                    );
        dataTracker.set( COLOUR       , DragonEntityColourPicker.chooseFromCategory(getDragonType(), getUuid()).asInt() );
        dataTracker.set( STATE        , DragonState.SLEEP.toInt()                                                       );
        dataTracker.set( AGE          , 0.0f                                                                            );
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }


    @Override
    public void initGoals() {
        //this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        //this.goalSelector.add(1, new LookAroundGoal(this));
    }



    @Override
    public boolean isPersistent() {
        return true;
    }
    @Override
    public boolean cannotDespawn() {
        return true;
    }
    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    @Override
    public boolean isFireImmune() {
        return true;
    }
    @Override
    public boolean canFreeze() {
        return false;
    }
    @Override
    public boolean canBreatheInWater() {
        return true;
    }
    @Override
    public boolean canUsePortals() {
        return false;
    }
    @Override
    public boolean occludeVibrationSignals() {
        return true;
    }
    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }
    @Override
    public float getSoundVolume() {
        return 4.0f;
    }


    public boolean isValidTarget(@Nullable Entity entity) {
        if (entity instanceof PlayerEntity player) {
            if (EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(player) &&
                    ! this.isTeammate(player) &&
                    ! player.isInvulnerable() &&
                    ! player.isDead() &&
                    this.world.getWorldBorder().contains(player.getBoundingBox())
            ) {
                return true;
            }
        }
        return false;
    }


    public TagKey<GameEvent> getTag() {
        return VIBRATIONS;
    }
    public boolean triggersAvoidCriterion() {
        return true;
    }
    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable GameEvent.Emitter emitter) {
        if (! this.isAiDisabled() && ! this.isDead() && world.getWorldBorder().contains(pos) && ! this.isRemoved()) {
            Entity entity = emitter.sourceEntity();
            if (this.isValidTarget(entity)) {
                return true;
            }
        }
        return false;
    }
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
        if (! this.isAiDisabled() && ! this.isDead() && world.getWorldBorder().contains(pos) && ! this.isRemoved()) {
            if (this.isValidTarget(entity)) {
                alerted(entity);
            }
        }
    }



    @Override
    public void onPlayerCollision(PlayerEntity player) {
        alerted(player);
        super.onPlayerCollision(player);
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        if (! this.world.isClient && ! this.isAiDisabled()) {
            Entity entity = source.getAttacker();
            if (entity instanceof PlayerEntity) {
                if (this.isValidTarget(entity)) {
                    alerted(entity);
                }
            } else {
                entity.kill();
            }
        }
        return super.damage(source, amount);
    }



    @Override
    public void tick() {
        if (this.world instanceof ServerWorld serverWorld) {
            (this.vibrationListener.getListener()).tick(serverWorld);
        }
        super.tick();
    }



    public void alerted(Entity target) {
        setState(DragonState.NEST);
    }


}
