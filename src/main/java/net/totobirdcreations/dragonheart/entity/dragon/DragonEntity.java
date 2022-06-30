package net.totobirdcreations.dragonheart.entity.dragon;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.EntityPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.VibrationListener;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.ModBlockTags;
import net.totobirdcreations.dragonheart.damage.ModDamageSources;
import net.totobirdcreations.dragonheart.effect.ModStatusEffects;
import net.totobirdcreations.dragonheart.entity.dragon.ai.DragonEntityMoveController;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonEntityColourPicker;
import net.totobirdcreations.dragonheart.util.colour.RGBColour;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiConsumer;


public abstract class DragonEntity extends HostileEntity implements IAnimatable, VibrationListener.Callback {

    public static int   BLINK_COOLDOWN_TICKS = 50;
    public static int   BLINK_TICKS          = 5;
    public static float BLINK_CHANCE         = 1.0f;

    public static TagKey<GameEvent> VIBRATIONS               = TagKey.of(Registry.GAME_EVENT_KEY, new Identifier(DragonHeart.MOD_ID, "dragon_can_listen"));
    public static int               WAKEUP_VIBRATIONS_NEEDED = 5;
    public static int               ROAR_ANIMATION_LENGTH    = 41;
    public static float             ROAR_RADIUS              = 16.0f;
    public static float             ROAR_KNOCKBACK           = 100.0f;
    public static int               ROAR_DESTROY_PER_TICK    = 8;
    public static float             ROAR_DAMAGE              = 30.0f;
    public static float             JUMP_STRENGTH            = 1.0f;

    public AnimationFactory                          animationFactory = new AnimationFactory(this);
    public EntityGameEventHandler<VibrationListener> gameEventHandler = new EntityGameEventHandler(new VibrationListener(new EntityPositionSource(this, this.getStandingEyeHeight()), 16, this, (VibrationListener.Vibration)null, 0.0F, 0));

    public int        blinkCooldownTicks = BLINK_COOLDOWN_TICKS;
    public int        blinkTicks         = 0;

    public static final TrackedData<BlockPos>            SPAWN_POS;
    public static final TrackedData<Integer>             HUNGER_LEVEL;
    public static final TrackedData<Integer>             COLOUR;
    public static final TrackedData<Integer>             STATE;
    public static final TrackedData<Float>               AGE;
    public static final TrackedData<Integer>             WAKEUP_PROGRESS;
    public static final TrackedData<Integer>             ROAR_TICKS;
    public static final TrackedData<Boolean>             FLYING;
    public static final TrackedData<Optional<UUID>>      TARGET;
    public static final TrackedData<BlockPos>            TARGET_POSITION;
    public static final TrackedData<Integer>             EYE_COLOUR;

    static {
        SPAWN_POS       = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS     );
        HUNGER_LEVEL    = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        COLOUR          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        STATE           = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        AGE             = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.FLOAT         );
        WAKEUP_PROGRESS = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        ROAR_TICKS      = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        FLYING          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
        TARGET          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.OPTIONAL_UUID );
        TARGET_POSITION = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS     );
        EYE_COLOUR      = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
    }

    public enum DragonType {
        NONE,
        FIRE,
        ICE,
        LIGHTNING
    }

    public enum DragonState {
        SLEEP,
        ROAR,
        NEST,
        PURSUE;

        public int toInt() {
            return switch (this) {
                case SLEEP  -> 0;
                case ROAR   -> 1;
                case NEST   -> 2;
                case PURSUE -> 3;
            };
        }
        public static DragonState fromInt(int from) {
            return switch (from) {
                case    0 -> SLEEP;
                case    1 -> ROAR;
                case    2 -> NEST;
                case    3 -> PURSUE;
                default   -> SLEEP;
            };
        }
        public String toString() {
            return switch (this) {
                case SLEEP  -> "sleep";
                case ROAR   -> "roar";
                case NEST   -> "nest";
                case PURSUE -> "pursue";
            };
        }
        public static DragonState fromString(String from) {
            return switch (from) {
                case    "sleep"  -> SLEEP;
                case    "roar"   -> ROAR;
                case    "nest"   -> NEST;
                case    "pursue" -> PURSUE;
                default          -> SLEEP;
            };
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH           , 1000.0d )
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE        , 50.0f   )
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK     , 3.0f    )
                .add(EntityAttributes.GENERIC_ATTACK_SPEED         , 3.0f    )
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE , 1.0f    );
    }


    public DragonEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.moveControl         = new DragonEntityMoveController(this);
    }


    public abstract DragonType getDragonType();
    public abstract RGBColour  getDefaultEyeColour();


    public void setState(DragonState state) {
        switch (state) {
            case SLEEP:
                dataTracker.set(DragonEntity.FLYING, false);
                dataTracker.set(DragonEntity.WAKEUP_PROGRESS, 0);
            case PURSUE:
                dataTracker.set(DragonEntity.FLYING, true);
                Vec3d velocity = getVelocity();
                setVelocity(velocity.x, JUMP_STRENGTH, velocity.z);
                dataTracker.set(FLYING, true);
        }
        this.dataTracker.set(STATE, state.toInt());
    }


    public DragonState getState() {
        return DragonState.fromInt(this.dataTracker.get(STATE));
    }


    public boolean eyesOpen() {
        return getState() != DragonState.SLEEP;
    }



    public PlayState animationPredicate(AnimationEvent<DragonEntity> event) {

        DragonState state = getState();

        AnimationBuilder builder   = new AnimationBuilder();
        PlayState        playState = PlayState.CONTINUE;

        if (state == DragonState.SLEEP) {
            event.getController().transitionLengthTicks = 20;
            event.getController().easingType            = EasingType.EaseInOutCubic;
            builder.addAnimation("animation.dragon.sleep", true);
        } else if (state == DragonState.ROAR) {
            event.getController().transitionLengthTicks = 0;
            event.getController().easingType            = EasingType.Linear;
            builder.addAnimation("animation.dragon.roar", false);
        } else {
            if (isOnGround()) {
                event.getController().transitionLengthTicks = 10;
                event.getController().easingType            = EasingType.EaseInOutCubic;
                builder.addAnimation("animation.dragon.stand", true);
            } else {
                event.getController().transitionLengthTicks = 10;
                event.getController().easingType            = EasingType.NONE;
                builder.addAnimation("animation.dragon.fly", true);
            }
        }
        event.getController().setAnimation(builder);
        return playState;

    }


    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::animationPredicate));
    }



    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking( SPAWN_POS       , new BlockPos(0, 0, 0) );
        this.dataTracker.startTracking( HUNGER_LEVEL    , 0                     );
        this.dataTracker.startTracking( COLOUR          , 0                     );
        this.dataTracker.startTracking( STATE           , 0                     );
        this.dataTracker.startTracking( AGE             , 0.0f                  );
        this.dataTracker.startTracking( WAKEUP_PROGRESS , 0                     );
        this.dataTracker.startTracking( ROAR_TICKS      , 0                     );
        this.dataTracker.startTracking( FLYING          , false                 );
        this.dataTracker.startTracking( EYE_COLOUR      , 0                     );
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean("Flying", dataTracker.get(FLYING));

        nbt.putInt("RoarTicks", dataTracker.get(ROAR_TICKS));

        nbt.putInt("WakeupProgress", dataTracker.get(WAKEUP_PROGRESS));

        nbt.putFloat("Age", dataTracker.get(AGE));

        nbt.putInt("State", dataTracker.get(STATE));

        nbt.putInt("Colour", dataTracker.get(COLOUR));

        nbt.putInt("HungerLevel", dataTracker.get(HUNGER_LEVEL));

        BlockPos spawnPos = dataTracker.get(SPAWN_POS);
        nbt.putIntArray("SpawnPos", new int[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()});

        DataResult data = VibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.gameEventHandler.getListener());
        data.resultOrPartial(DragonHeart.LOGGER::error).ifPresent((element) -> {
            nbt.put("vibrationListener", (NbtElement) element);
        });

        nbt.putInt("EyeColour", dataTracker.get(EYE_COLOUR));
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        dataTracker.set(FLYING, nbt.getBoolean("Flying"));

        dataTracker.set(ROAR_TICKS, nbt.getInt("RoarTicks"));

        dataTracker.set(WAKEUP_PROGRESS, nbt.getInt("WakeupProgress"));

        dataTracker.set(AGE, nbt.getFloat("Age"));

        dataTracker.set(STATE, nbt.getInt("State"));

        dataTracker.set(COLOUR, nbt.getInt("Colour"));

        dataTracker.set(HUNGER_LEVEL, nbt.getInt("HungerLever"));

        int[] spawnPos = nbt.getIntArray("SpawnPos");
        if (spawnPos.length == 3) {
            dataTracker.set(SPAWN_POS, new BlockPos(spawnPos[0], spawnPos[1], spawnPos[2]));
        } else {
            dataTracker.set(SPAWN_POS, getBlockPos());
        }

        DataResult data = VibrationListener.createCodec(this).parse(new Dynamic(NbtOps.INSTANCE, nbt.getCompound("vibrationListener")));
        data.resultOrPartial(DragonHeart.LOGGER::error).ifPresent((element) -> {
            this.gameEventHandler.setListener((VibrationListener) element, this.world);
        });

        dataTracker.set(EYE_COLOUR, nbt.getInt("EyeColour"));
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        dataTracker.set( SPAWN_POS       , getBlockPos()                                                                   );
        dataTracker.set( HUNGER_LEVEL    , 20 * 60 * 15                                                                    );
        dataTracker.set( COLOUR          , DragonEntityColourPicker.chooseFromCategory(getDragonType(), getUuid()).asInt() );
        dataTracker.set( STATE           , DragonState.SLEEP.toInt()                                                       );
        dataTracker.set( AGE             , 0.0f                                                                            );
        dataTracker.set( WAKEUP_PROGRESS , 0                                                                               );
        dataTracker.set( ROAR_TICKS      , 0                                                                               );
        dataTracker.set( FLYING          , false                                                                           );
        dataTracker.set( EYE_COLOUR      , this.getDefaultEyeColour().asInt()                                              );
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
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
    @Override
    public boolean isDisallowedInPeaceful() {
        return false;
    }
    @Override
    public boolean canStartRiding(Entity entity) {
        return false;
    }
    @Override
    public void takeKnockback(double strength, double x, double z) {}
    @Override
    public boolean hasNoGravity() {return dataTracker.get(FLYING);}



    public boolean isValidTarget(@Nullable Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity) &&
                    ! this.isTeammate(entity) &&
                    ! entity.isInvulnerable() &&
                    ! livingEntity.isDead() &&
                    this.world.getWorldBorder().contains(entity.getBoundingBox())
            ) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void initGoals() {
        //this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        //this.goalSelector.add(1, new LookAroundGoal(this));
    }


    @Override
    public TagKey<GameEvent> getTag() {
        return VIBRATIONS;
    }

    @Override
    public boolean triggersAvoidCriterion() {
        return true;
    }

    @Override
    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        if (this.world instanceof ServerWorld serverWorld) {
            callback.accept(this.gameEventHandler, serverWorld);
        }
    }

    @Override
    public boolean accepts(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable GameEvent.Emitter emitter) {
        if (! this.isAiDisabled() && ! this.isDead() && world.getWorldBorder().contains(pos) && ! this.isRemoved() && this.getState() == DragonState.SLEEP) {
            Entity entity = emitter.sourceEntity();
            if (entity instanceof LivingEntity livingEntity) {
                if (! this.isValidTarget(livingEntity)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
        if (! this.isAiDisabled() && ! this.isDead() && world.getWorldBorder().contains(pos) && ! this.isRemoved()) {
            if (entity instanceof LivingEntity livingEntity) {
                if (this.isValidTarget(livingEntity)) {
                    this.incrementWakeupProgress(false, sourceEntity instanceof LivingEntity ? sourceEntity : null);
                }
            } else {
                this.incrementWakeupProgress(false, sourceEntity instanceof LivingEntity ? sourceEntity : null);
            }
        }
    }



    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
        incrementWakeupProgress(true, player);
    }



    @Override
    public void tick() {
        if (this.world instanceof ServerWorld serverWorld) {
            this.gameEventHandler.getListener().tick(serverWorld);
        }

        if (!world.isClient()) {
            if (getState() == DragonState.ROAR) {
                // Throw all nearby entities away & deafen them, depending on distance to them.
                List<Entity> entities = world.getOtherEntities(this, Box.of(this.getPos(), ROAR_RADIUS, ROAR_RADIUS, ROAR_RADIUS));
                for (int i = 0; i < entities.size(); i++) {

                    Entity entity = entities.get(i);
                    if (!entity.isRemoved() && entity instanceof LivingEntity livingEntity) {
                        float distance = entity.distanceTo(this);
                        if (distance <= ROAR_RADIUS) {
                            // Throw & damage entity
                            Vec3d vector = entity.getPos().add(this.getPos().multiply(-1)).multiply(1.0 / distance);
                            float power = 1.0f - distance / ROAR_RADIUS;
                            Vec3d velocity = vector.multiply(power * ROAR_KNOCKBACK);
                            entity.addVelocity(velocity.x, velocity.y, velocity.z);
                            entity.damage(ModDamageSources.ROAR, power * ROAR_DAMAGE);
                            // Add deafened effect
                            StatusEffectInstance effect = new StatusEffectInstance(ModStatusEffects.DEAFENED, 10, 0, false, false, true);
                            livingEntity.addStatusEffect(effect, this);
                        }
                    }
                }

                // Destroy blocks in random directions
                Random rand = new Random();
                for (int i = 0; i < ROAR_DESTROY_PER_TICK; i++) {
                    float yaw = rand.nextFloat((float) Math.PI * 2.0f);
                    float arc = (float) Math.PI / 2.0f;
                    float pitch = rand.nextFloat(arc * 0.5f) + (arc * 0.5f);
                    Vec3d vector = new Vec3d(
                            Math.sin(yaw) * Math.cos(pitch),
                            Math.sin(pitch),
                            Math.cos(yaw) * Math.cos(pitch)
                    );
                    BlockHitResult result = world.raycast(new RaycastContext(this.getPos(), this.getPos().add(vector.multiply(ROAR_RADIUS)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, this));
                    // If raycast hit a block
                    if (result.getType() == HitResult.Type.BLOCK) {
                        Block block = world.getBlockState(result.getBlockPos()).getBlock();
                        // Make sure block is not protected.
                        if (!Registry.BLOCK.getOrCreateEntry(Registry.BLOCK.getKey(block).get()).isIn(ModBlockTags.DRAGON_UNGRIEFABLE)) {
                            world.breakBlock(result.getBlockPos(), true, this);
                        }
                    }
                }

                // Decrease roar ricks
                int ticks = dataTracker.get(ROAR_TICKS) - 1;
                dataTracker.set(ROAR_TICKS, ticks);
                if (ticks <= 0) {
                    // Roar done
                    setState(DragonState.PURSUE);
                }
            }

        }

        // Client side blinking animation
        if (blinkTicks > 0) {
            if (blinkCooldownTicks > 0) {
                blinkTicks -= 1;
            }
            if (blinkCooldownTicks <= 0) {
                Random rand = new Random();
                if (rand.nextFloat() <= BLINK_CHANCE) {
                    blinkCooldownTicks = BLINK_COOLDOWN_TICKS;
                    blinkTicks = BLINK_TICKS;
                }
            }
        } else {
            blinkTicks -= 1;
        }

        // Run parent tick
        super.tick();
    }



    public void incrementWakeupProgress(boolean all, @Nullable Entity alerter) {
        if (all) {
            dataTracker.set(WAKEUP_PROGRESS, WAKEUP_VIBRATIONS_NEEDED);
        } else {
            dataTracker.set(WAKEUP_PROGRESS, dataTracker.get(WAKEUP_PROGRESS) + 1);
        }
        if (dataTracker.get(WAKEUP_PROGRESS) >= WAKEUP_VIBRATIONS_NEEDED) {
            alerted(alerter);
        }
    }

    public void alerted(@Nullable Entity alerter) {
        if (getState() == DragonState.SLEEP) {
            dataTracker.set(ROAR_TICKS, ROAR_ANIMATION_LENGTH);
            setState(DragonState.ROAR);
        }
    }


}
