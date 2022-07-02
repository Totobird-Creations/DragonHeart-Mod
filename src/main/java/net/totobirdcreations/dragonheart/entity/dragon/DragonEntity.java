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
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.*;
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
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonSalt;
import net.totobirdcreations.dragonheart.entity.dragon.util.UuidOp;
import net.totobirdcreations.dragonheart.item.FoodItems;
import net.totobirdcreations.dragonheart.util.Curve;
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

import java.util.*;
import java.util.function.BiConsumer;


public abstract class DragonEntity extends HostileEntity implements IAnimatable, VibrationListener.Callback {

    public static int   BLINK_COOLDOWN_TICKS = 50;
    public static int   BLINK_TICKS          = 5;
    public static float BLINK_CHANCE         = 1.0f;

    public static TagKey<GameEvent>    VIBRATIONS               = TagKey.of(Registry.GAME_EVENT_KEY, new Identifier(DragonHeart.MOD_ID, "dragon_can_listen"));
    public static int                  WAKEUP_VIBRATIONS_NEEDED = 5;
    public static int                  ROAR_ANIMATION_LENGTH    = 41;
    public static float                ROAR_RADIUS              = 16.0f;
    public static float                ROAR_KNOCKBACK           = 100.0f;
    public static int                  ROAR_DESTROY_PER_TICK    = 1;
    public static float                ROAR_DAMAGE              = 30.0f;
    public static float                JUMP_STRENGTH            = 1.0f;
    public static int                  STAGE_TICKS              = 600000;
    public static int                  MAX_STAGES               = 4;
    public static int                  MIN_NATURAL_SPAWN_AGE    = STAGE_TICKS * 3;
    public static int                  MAX_NATURAL_SPAWN_AGE    = STAGE_TICKS * 4;
    public static HashMap<Item, Float> HEAL_ITEMS               = new HashMap<>();
    public static int                  MIN_BREED_STAGE          = 2;
    public static int                  MAX_SHOULDER_STAGE       = 0;
    public static int                  MIN_MOUNT_STAGE          = 2;
    static {
        HEAL_ITEMS.put( Items.COOKED_BEEF            , 50.0f  );
        HEAL_ITEMS.put( Items.COOKED_PORKCHOP        , 37.5f  );
        HEAL_ITEMS.put( Items.COOKED_MUTTON          , 25.0f  );
        HEAL_ITEMS.put( Items.COOKED_CHICKEN         , 12.5f  );
        HEAL_ITEMS.put( Items.COOKED_RABBIT          , 12.5f  );
        HEAL_ITEMS.put( Items.GOLDEN_APPLE           , 100.0f );
        HEAL_ITEMS.put( Items.ENCHANTED_GOLDEN_APPLE , 250.0f );
        HEAL_ITEMS.put( FoodItems.DRAGONMEAL         , 43.75f );
    }
    public static float                MIN_SCALE                = 0.125f;
    public static float                MAX_SCALE                = 1.0f;

    public static EntityDimensions     DIMENSIONS               = EntityDimensions.changing(2.75f, 1.625f);
    public static float                EYE_HEIGHT               = 1.5f;
    public static float                MIN_WIDTH                = 1.0f;
    public static float                MIN_HEIGHT               = 0.5f;

    public int        blinkCooldownTicks = BLINK_COOLDOWN_TICKS;
    public int        blinkTicks         = 0;

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
                default   -> SLEEP;
                case    1 -> ROAR;
                case    2 -> NEST;
                case    3 -> PURSUE;
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
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH           , 1000.0d )
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE        , 50.0f   )
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK     , 3.0f    )
                .add(EntityAttributes.GENERIC_ATTACK_SPEED         , 3.0f    )
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE , 1.0f    );
    }


    /*-------------
    | Constructors |
     -------------*/

    public DragonEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.moveControl         = new DragonEntityMoveController(this);
    }


    /*------------------
    | Abstract Methods |
     -----------------*/

    public abstract DragonType getDragonType();
    public abstract RGBColour  getDefaultEyeColour();


    /*--------------
    | Data Handling |
     --------------*/

    public static final TrackedData<BlockPos>       SPAWN_POS;
    public static final TrackedData<Integer>        HUNGER_LEVEL;
    public static final TrackedData<Integer>        COLOUR;
    public static final TrackedData<Integer>        STATE;
    public static final TrackedData<Integer>        AGE;
    public static final TrackedData<Integer>        WAKEUP_PROGRESS;
    public static final TrackedData<Integer>        ROAR_TICKS;
    public static final TrackedData<Boolean>        FLYING;
    public static final TrackedData<Optional<UUID>> TARGET;
    public static final TrackedData<BlockPos>       TARGET_POSITION;
    public static final TrackedData<Integer>        EYE_COLOUR;
    public static final TrackedData<Optional<UUID>> TAMED_OWNER;
    public static final TrackedData<Boolean>        HAS_BREEDED;
    public static final TrackedData<Boolean>        NATURAL_SPAWN;
    public static final TrackedData<Boolean>        SITTING;

    static {
        SPAWN_POS       = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS     );
        HUNGER_LEVEL    = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        COLOUR          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        STATE           = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        AGE             = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        WAKEUP_PROGRESS = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        ROAR_TICKS      = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        FLYING          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
        TARGET          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.OPTIONAL_UUID );
        TARGET_POSITION = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS     );
        EYE_COLOUR      = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        TAMED_OWNER     = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.OPTIONAL_UUID );
        HAS_BREEDED     = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
        NATURAL_SPAWN   = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
        SITTING         = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
    }


    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking( SPAWN_POS       , new BlockPos(0, 0, 0) );
        this.dataTracker.startTracking( HUNGER_LEVEL    , 0                     );
        this.dataTracker.startTracking( COLOUR          , 8355711               );
        this.dataTracker.startTracking( STATE           , 0                     );
        this.dataTracker.startTracking( AGE             , 0                     );
        calculateDimensions();
        this.dataTracker.startTracking( WAKEUP_PROGRESS , 0                     );
        this.dataTracker.startTracking( ROAR_TICKS      , 0                     );
        this.dataTracker.startTracking( FLYING          , false                 );
        this.dataTracker.startTracking( EYE_COLOUR      , 16777215              );
        this.dataTracker.startTracking( TAMED_OWNER     , null                  );
        this.dataTracker.startTracking( HAS_BREEDED     , false                 );
        this.dataTracker.startTracking( NATURAL_SPAWN   , false                 );
        this.dataTracker.startTracking( SITTING         , false                 );
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        dataTracker.set( SPAWN_POS       , getBlockPos()                                                                   );
        dataTracker.set( HUNGER_LEVEL    , 20 * 60 * 15                                                                    );
        dataTracker.set( COLOUR          , DragonEntityColourPicker.chooseFromCategory(getDragonType(), getUuid()).asInt() );
        dataTracker.set( STATE           , DragonState.SLEEP.toInt()                                                       );
        net.minecraft.util.math.random.Random rand = net.minecraft.util.math.random.Random.create(DragonSalt.AGE + UuidOp.uuidToInt(uuid));
        dataTracker.set( AGE             , rand.nextBetween(MIN_NATURAL_SPAWN_AGE, MAX_NATURAL_SPAWN_AGE)                  );
        calculateDimensions();
        dataTracker.set( WAKEUP_PROGRESS , 0                                                                               );
        dataTracker.set( ROAR_TICKS      , 0                                                                               );
        dataTracker.set( FLYING          , false                                                                           );
        dataTracker.set( EYE_COLOUR      , this.getDefaultEyeColour().asInt()                                              );
        dataTracker.set( TAMED_OWNER     , Optional.empty()                                                                );
        dataTracker.set( HAS_BREEDED     , false                                                                           );
        dataTracker.set( NATURAL_SPAWN   , true                                                                            );
        dataTracker.set( SITTING         , false                                                                           );
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putBoolean("Flying", dataTracker.get(FLYING));

        nbt.putInt("RoarTicks", dataTracker.get(ROAR_TICKS));

        nbt.putInt("WakeupProgress", dataTracker.get(WAKEUP_PROGRESS));

        nbt.putInt("Age", dataTracker.get(AGE));

        nbt.putInt("State", dataTracker.get(STATE));

        nbt.putInt("Colour", dataTracker.get(COLOUR));

        nbt.putInt("HungerLevel", dataTracker.get(HUNGER_LEVEL));

        BlockPos spawnPos = dataTracker.get(SPAWN_POS);
        nbt.putIntArray("SpawnPos", new int[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()});

        DataResult<NbtElement> data = VibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.gameEventHandler.getListener());
        data.resultOrPartial(DragonHeart.LOGGER::error).ifPresent((element) ->
            nbt.put("vibrationListener", element)
        );

        nbt.putInt("EyeColour", dataTracker.get(EYE_COLOUR));

        Optional<UUID> tamedOwner = dataTracker.get(TAMED_OWNER);
        if (tamedOwner.isPresent()) {
            nbt.putUuid("TamedOwner", tamedOwner.get());
        } else {
            nbt.remove("TamedOwner");
        }

        nbt.putBoolean("HasBreeded", dataTracker.get(HAS_BREEDED));

        nbt.putBoolean("NaturalSpawn", dataTracker.get(NATURAL_SPAWN));
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        dataTracker.set(FLYING, nbt.getBoolean("Flying"));

        dataTracker.set(ROAR_TICKS, nbt.getInt("RoarTicks"));

        dataTracker.set(WAKEUP_PROGRESS, nbt.getInt("WakeupProgress"));

        dataTracker.set(AGE, nbt.getInt("Age"));
        calculateDimensions();

        dataTracker.set(STATE, nbt.getInt("State"));

        dataTracker.set(COLOUR, nbt.getInt("Colour"));

        dataTracker.set(HUNGER_LEVEL, nbt.getInt("HungerLever"));

        int[] spawnPos = nbt.getIntArray("SpawnPos");
        if (spawnPos.length == 3) {
            dataTracker.set(SPAWN_POS, new BlockPos(spawnPos[0], spawnPos[1], spawnPos[2]));
        } else {
            dataTracker.set(SPAWN_POS, getBlockPos());
        }

        DataResult<VibrationListener> data = VibrationListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("vibrationListener")));
        data.resultOrPartial(DragonHeart.LOGGER::error).ifPresent((element) ->
            this.gameEventHandler.setListener(element, this.world)
        );

        dataTracker.set(EYE_COLOUR, nbt.getInt("EyeColour"));

        dataTracker.set(TAMED_OWNER, nbt.contains("TamedOwner") ? Optional.of(nbt.getUuid("TamedOwner")) : Optional.empty());

        dataTracker.set(HAS_BREEDED, nbt.getBoolean("HasBreeded"));

        dataTracker.set(NATURAL_SPAWN, nbt.getBoolean("NaturalSpawn"));
    }



    public DragonState getState() {
        return DragonState.fromInt(dataTracker.get(STATE));
    }

    public boolean areEyesOpen() {
        return getState() != DragonState.SLEEP;
    }

    public int getStage() {
        return Math.max(Math.min(Math.floorDiv(this.getAge(), STAGE_TICKS), MAX_STAGES), 0);
    }

    public boolean isTamed() {
        return dataTracker.get(TAMED_OWNER).isPresent();
    }

    public boolean canBreed(DragonEntity other) {
        return this._canBreed() && other._canBreed();
    }
    public boolean _canBreed() {
        return this.isTamed() && ! dataTracker.get(HAS_BREEDED) && getStage() >= MIN_BREED_STAGE;
    }

    public boolean isNaturallySpawned() {
        return dataTracker.get(NATURAL_SPAWN);
    }

    public boolean isTamedOwner(PlayerEntity player) {
        Optional<UUID> tamedOwner = dataTracker.get(TAMED_OWNER);
        return (
                isTamed() &&
                tamedOwner.isPresent() &&
                tamedOwner.get().equals(player.getUuid())
        );
    }

    public int getAge() {
        return dataTracker.get(AGE);
    }

    public int getMaxAge() {
        return STAGE_TICKS * MAX_STAGES;
    }

    public float getAgeInterpolation() {
        return Math.min(Math.max((float)(getAge()) / (float)(getMaxAge()), 0.0f), 1.0f);
    }

    public float getScale() {
        return MIN_SCALE + (MAX_SCALE - MIN_SCALE) * getAgeInterpolation();
    }



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

    public void addAge(int amount) {
        dataTracker.set(AGE, Math.min(Math.max(dataTracker.get(AGE) + amount, 0), getMaxAge()));
        calculateDimensions();
    }

    public void toggleSitting() {
        dataTracker.set(SITTING, ! dataTracker.get(SITTING));
    }


    /*-------------------
    | Animation Handling |
     -------------------*/

    public final AnimationFactory animationFactory = new AnimationFactory(this);


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
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }


    /*---------------------------------
    | Physics, Despawn, and Resistance |
     ---------------------------------*/

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
    @Override
    public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return EYE_HEIGHT * getScale();
    }
    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        float t = getAgeInterpolation();
        return EntityDimensions.changing(
                MIN_WIDTH  + ( DIMENSIONS.width  - MIN_WIDTH  ) * t,
                MIN_HEIGHT + ( DIMENSIONS.height - MIN_HEIGHT ) * t
        );
    }


    /*---
    | AI |
     ---*/

    public boolean isValidTarget(@Nullable Entity entity) {
        return (
                entity instanceof LivingEntity livingEntity &&
                EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity) &&
                ! this.isTeammate(entity) &&
                ! entity.isInvulnerable() &&
                ! livingEntity.isDead() &&
                this.world.getWorldBorder().contains(entity.getBoundingBox())
        );
    }


    @Override
    public void onPlayerCollision(PlayerEntity player) {
        super.onPlayerCollision(player);
        incrementWakeupProgress(true, player);
    }


    @Override
    public void initGoals() {
        // TODO
        // Ridden
        // Flee
        // Sleep
        // Sit
        // Pursue
    }


    @Override
    public void tick() {
        if (this.world instanceof ServerWorld serverWorld) {
            // Tick vibration handler.
            this.gameEventHandler.getListener().tick(serverWorld);
        }

        // Server side.
        if (! world.isClient()) {
            if (getState() == DragonState.ROAR) {
                // Throw all nearby entities away & deafen them, depending on distance to them.
                List<Entity> entities = world.getOtherEntities(this, Box.of(this.getPos(), ROAR_RADIUS, ROAR_RADIUS, ROAR_RADIUS));
                for (Entity entity : entities) {

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
                        Optional<RegistryKey<Block>> key = Registry.BLOCK.getKey(block);
                        if (key.isPresent()) {
                            if (!Registry.BLOCK.getOrCreateEntry(key.get()).isIn(ModBlockTags.DRAGON_UNGRIEFABLE)) {
                                world.breakBlock(result.getBlockPos(), true, this);
                            }
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

            // Tick age.
            addAge(1);

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

        // Handle dragon size
        this.calculateDimensions();

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
            /*if (alerter != null) {
                dataTracker.set(TARGET, Optional.of(alerter.getUuid()));
            }*/
            setState(DragonState.ROAR);
        }
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            ItemStack stack = player.getStackInHand(hand);
            if (isTamed()) {
                if (stack == null && isTamedOwner(player)) {
                    if (player.isSneaking()) {
                        if (canRideShoulder(player)) {
                            // Dragon ride player shoulder.
                            if (! this.world.isClient()) {
                                rideShoulder(player);
                            }
                            return ActionResult.success(this.world.isClient());
                        } else if (canPlayerMount(player)) {
                            // Player ride dragon.
                            if (! this.world.isClient()) {
                                mountPlayer(player);
                            }
                            return ActionResult.success(this.world.isClient());
                        }
                    } else {
                        // Toggle dragon sitting.
                        if (! this.world.isClient()) {
                            toggleSitting();
                        }
                        return ActionResult.success(this.world.isClient());
                    }
                } else if (getHealth() < getMaxHealth()) {
                    assert stack != null;
                    for (Item item : HEAL_ITEMS.keySet()) {
                        if (stack.isOf(item)) {
                            // Feed & Heal
                            if (! this.world.isClient()) {
                                heal(HEAL_ITEMS.get(item));
                                if (! player.getAbilities().creativeMode) {
                                    stack.decrement(1);
                                }
                            }
                            world.playSound(getX(), getY(), getZ(), SoundEvents.ENTITY_HORSE_EAT, SoundCategory.NEUTRAL, 2.5f, 1.0f, false);
                            return ActionResult.success(this.world.isClient());
                        }
                    }
                }
            } else if (canTame(player)) {
                if (stack == null) {
                    if (! this.world.isClient()) {
                        tamedBy(player);
                    }
                    return ActionResult.success(this.world.isClient());
                }
            }
        }
        // Run parent interact.
        return super.interactMob(player, hand);
    }


    /*-------------------
    | Vibration Handling |
     -------------------*/

    public EntityGameEventHandler<VibrationListener> gameEventHandler = new EntityGameEventHandler<>(new VibrationListener(new EntityPositionSource(this, this.getStandingEyeHeight()), 16, this, null, 0.0F, 0));


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
        if (
                ! this.isAiDisabled() &&
                ! this.isDead() &&
                world.getWorldBorder().contains(pos) &&
                ! this.isRemoved() &&
                this.getState() == DragonState.SLEEP
        ) {
            if (emitter != null) {
                Entity entity = emitter.sourceEntity();
                return (
                        entity instanceof LivingEntity livingentity &&
                        this.isValidTarget(livingentity)
                );
            }
        }
        return false;
    }

    @Override
    public void accept(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float distance) {
        if (
                ! this.isAiDisabled() &&
                ! this.isDead() &&
                world.getWorldBorder().contains(pos) &&
                ! this.isRemoved()
        ) {
            if (entity instanceof LivingEntity livingEntity) {
                if (this.isValidTarget(livingEntity)) {
                    this.incrementWakeupProgress(false, sourceEntity instanceof LivingEntity ? sourceEntity : null);
                }
            } else {
                this.incrementWakeupProgress(false, sourceEntity instanceof LivingEntity ? sourceEntity : null);
            }
        }
    }


    /*-------
    | Taming |
     -------*/

    public boolean canTame(PlayerEntity player) {
        return ((! isNaturallySpawned()) || player.isCreative()) && getStage() <= MAX_SHOULDER_STAGE && (! isTamed());
    }


    public void tamedBy(PlayerEntity tamedOwner) {
        dataTracker.set(TAMED_OWNER, Optional.of(tamedOwner.getUuid()));
        Random random = new Random();
        Box    bounds = this.getBoundingBox();
        for (int i=0;i< 7 ;i++) {
            this.world.addParticle(
                    ParticleTypes.HEART,
                    random.nextDouble(bounds.maxX - bounds.minX),
                    random.nextDouble(bounds.maxY - bounds.minY),
                    random.nextDouble(bounds.maxZ - bounds.minZ),
                    random.nextGaussian() * 0.02,
                    random.nextGaussian() * 0.02,
                    random.nextGaussian() * 0.02
            );
        }
    }


    /*----------------
    | Shoulder Riding |
     ----------------*/

    public boolean canRideShoulder(PlayerEntity player) {
        return player.getShoulderEntityLeft() == null && player.getShoulderEntityRight() == null;
    }

    public void rideShoulder(PlayerEntity player) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("id", this.getSavedEntityId());
        this.writeNbt(nbtCompound);
        if (player.addShoulderEntity(nbtCompound)) {
            this.discard();
        }
    }


    /*----------------
    | Player Mounting |
     ----------------*/

    public boolean canPlayerMount(PlayerEntity player) {
        return isTamedOwner(player) && getStage() >= MIN_MOUNT_STAGE;
    }

    public void mountPlayer(PlayerEntity player) {
        player.startRiding(this);
    }

    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        if (passenger instanceof MobEntity mobEntity) {
            this.headYaw = mobEntity.headYaw;
        }

    }


    /*---------
    | Breeding |
     ---------*/

    public boolean canBreedWith(Entity other) {
        return(
                other != this &&
                other instanceof DragonEntity otherDragon &&
                this.canBreed(otherDragon)
        );
    }


    public void breed(ServerWorld world, DragonEntity other) {
        // TODO
    }


    @Nullable
    public Item getBreedingItem() {
        return FoodItems.DRAGONMEAL;
    }


}
