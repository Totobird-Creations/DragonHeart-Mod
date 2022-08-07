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
import net.minecraft.entity.mob.*;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
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
import net.totobirdcreations.dragonheart.block.BlockTags;
import net.totobirdcreations.dragonheart.config.Config;
import net.totobirdcreations.dragonheart.damage.DamageSources;
import net.totobirdcreations.dragonheart.effect.StatusEffects;
import net.totobirdcreations.dragonheart.entity.dragon.ai.FindTargetGoal;
import net.totobirdcreations.dragonheart.entity.dragon.ai.PursueTargetGoal;
import net.totobirdcreations.dragonheart.entity.dragon.util.DragonSalt;
import net.totobirdcreations.dragonheart.entity.dragon.util.UuidOp;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;


public class DragonEntity extends FlyingEntity implements Monster, IAnimatable, VibrationListener.Callback {

    public static TagKey<GameEvent>    VIBRATIONS            = TagKey.of(Registry.GAME_EVENT_KEY, new Identifier(DragonHeart.ID, "dragon_can_listen"));
    public static int                  ROAR_ANIMATION_LENGTH = 41;
    public static float                JUMP_STRENGTH         = 1.0f;
    public static int                  MAX_STAGES            = 4;
    public static int                  MIN_NATURAL_SPAWN_AGE = Config.CONFIG.dragon.age.stage_ticks * 3;
    public static int                  MAX_NATURAL_SPAWN_AGE = Config.CONFIG.dragon.age.stage_ticks * 4;
    public static HashMap<Item, Float> HEAL_ITEMS            = new HashMap<>();
    static {
        HEAL_ITEMS.put( Items.COOKED_BEEF            , 50.0f  );
        HEAL_ITEMS.put( Items.COOKED_PORKCHOP        , 37.5f  );
        HEAL_ITEMS.put( Items.COOKED_MUTTON          , 25.0f  );
        HEAL_ITEMS.put( Items.COOKED_CHICKEN         , 12.5f  );
        HEAL_ITEMS.put( Items.COOKED_RABBIT          , 12.5f  );
        HEAL_ITEMS.put( Items.GOLDEN_APPLE           , 100.0f );
        HEAL_ITEMS.put( Items.ENCHANTED_GOLDEN_APPLE , 250.0f );
        //HEAL_ITEMS.put( FoodItems.DRAGON_MEAL         , 43.75f );
    }
    public static int   MAX_SHOULDER_STAGE = 0;
    public static float MIN_SOUND_PITCH    = 1.5f;
    public static float MAX_SOUND_PITCH    = 1.0f;

    public static float            MIN_MODEL_SCALE = 0.125f;
    public static float            MAX_MODEL_SCALE = 1.0f;
    public static float            EYE_HEIGHT      = 0.75f;
    public static EntityDimensions MAX_DIMENSIONS  = EntityDimensions.changing(2.5f, 0.1f);//1.625f);
    public static float            MIN_BOX_WIDTH   = 3.0f;
    public static float            MIN_BOX_HEIGHT  = 1.5f;

    public static float TARGET_MAX_DISTANCE  = 100.0f;
    public static int   TARGET_MAX_LAST_SEEN = 200;

    //public DragonPartEntity head = new DragonPartEntity(this, EntityDimensions.changing(0.5f, 0.5f), 1.5f);

    public static int   BLINK_COOLDOWN_TICKS = 200; // 5s
    public static int   BLINK_TICKS          = 5;   // 0.25s
    public static float BLINK_CHANCE         = 0.0025f;

    public int        blinkCooldownTicks = BLINK_COOLDOWN_TICKS;
    public int        blinkTicks         = 0;

    public enum DragonState {
        SLEEP,
        ROAR,
        NEST,
        PURSUE,
        WANDER;

        public int toInt() {
            return switch (this) {
                case SLEEP  -> 0;
                case ROAR   -> 1;
                case NEST   -> 2;
                case PURSUE -> 3;
                case WANDER -> 4;
            };
        }
        public static DragonState fromInt(int from) {
            return switch (from) {
                default   -> SLEEP;
                case    1 -> ROAR;
                case    2 -> NEST;
                case    3 -> PURSUE;
                case    4 -> WANDER;
            };
        }
        public String toString() {
            return switch (this) {
                case SLEEP  -> "sleep";
                case ROAR   -> "roar";
                case NEST   -> "nest";
                case PURSUE -> "pursue";
                case WANDER -> "wander";
            };
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return createMobAttributes()
                .add( EntityAttributes.GENERIC_MAX_HEALTH           , 1000.0d )
                .add( EntityAttributes.GENERIC_ATTACK_DAMAGE        , 50.0f   )
                .add( EntityAttributes.GENERIC_ATTACK_KNOCKBACK     , 3.0f    )
                .add( EntityAttributes.GENERIC_ATTACK_SPEED         , 3.0f    )
                .add( EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE , 1.0f    );
    }


    /*-------------
    | Constructors |
     -------------*/

    public DragonEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }


    /*--------------
    | Data Handling |
     --------------*/

    public static final TrackedData<String>         DRAGON;
    public static final TrackedData<BlockPos>       SPAWN_POS;
    public static final TrackedData<Integer>        HUNGER_LEVEL;
    public static final TrackedData<Integer>        COLOUR;
    public static final TrackedData<Integer>        STATE;
    public static final TrackedData<Integer>        AGE;
    public static final TrackedData<Integer>        WAKEUP_PROGRESS;
    public static final TrackedData<Integer>        ROAR_TICKS;
    public static final TrackedData<Boolean>        FLYING;
    public static final TrackedData<Optional<UUID>> TARGET;
    public static final TrackedData<BlockPos>       TARGET_POS;
    public static final TrackedData<Integer>        TARGET_LAST_SEEN;
    public static final TrackedData<Integer>        EYE_COLOUR;
    public static final TrackedData<Optional<UUID>> TAMED_OWNER;
    public static final TrackedData<Boolean>        HAS_BREEDED;
    public static final TrackedData<Boolean>        NATURAL_SPAWN;
    public static final TrackedData<Boolean>        SITTING;

    static {
        DRAGON           = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.STRING        );
        SPAWN_POS        = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS     );
        HUNGER_LEVEL     = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        COLOUR           = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        STATE            = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        AGE              = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        WAKEUP_PROGRESS  = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        ROAR_TICKS       = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        FLYING           = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
        TARGET           = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.OPTIONAL_UUID );
        TARGET_POS       = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BLOCK_POS     );
        TARGET_LAST_SEEN = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        EYE_COLOUR       = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.INTEGER       );
        TAMED_OWNER      = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.OPTIONAL_UUID );
        HAS_BREEDED      = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
        NATURAL_SPAWN    = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
        SITTING          = DataTracker.registerData( DragonEntity.class , TrackedDataHandlerRegistry.BOOLEAN       );
    }


    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking( DRAGON           , ""                      );
        this.dataTracker.startTracking( SPAWN_POS        , new BlockPos(0, 0, 0)   );
        this.dataTracker.startTracking( HUNGER_LEVEL     , 0                       );
        this.dataTracker.startTracking( COLOUR           , RGBColour.WHITE.asInt() );
        this.dataTracker.startTracking( STATE            , 0                       );
        this.dataTracker.startTracking( AGE              , 0                       );
        this.calculateDimensions();
        this.dataTracker.startTracking( WAKEUP_PROGRESS  , 0                       );
        this.dataTracker.startTracking( ROAR_TICKS       , 0                       );
        this.dataTracker.startTracking( FLYING           , false                   );
        this.dataTracker.startTracking( TARGET           , null                    );
        this.dataTracker.startTracking( TARGET_POS       , new BlockPos(0, 0, 0)   );
        this.dataTracker.startTracking( TARGET_LAST_SEEN , 0                       );
        this.dataTracker.startTracking( EYE_COLOUR       , RGBColour.WHITE.asInt() );
        this.dataTracker.startTracking( TAMED_OWNER      , null                    );
        this.dataTracker.startTracking( HAS_BREEDED      , false                   );
        this.dataTracker.startTracking( NATURAL_SPAWN    , false                   );
        this.dataTracker.startTracking( SITTING          , false                   );
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        net.minecraft.util.math.random.Random rand;

        this.dataTracker.set( DRAGON           , entityNbt != null && entityNbt.contains("dragon", NbtElement.STRING_TYPE) ? entityNbt.getString("dragon") : NbtHelper.EMPTY_TYPE.toString() );
        this.dataTracker.set( SPAWN_POS        , this.getBlockPos()                                                                                                                          );
        this.dataTracker.set( HUNGER_LEVEL     , 20 * 60 * 15                                                                                                                                );
        this.dataTracker.set( COLOUR           , RGBColour.WHITE.asInt()                                                                                                                     );
        this.dataTracker.set( STATE            , DragonState.SLEEP.toInt()                                                                                                                   );
        this.dataTracker.set( AGE              , 0                                                                                                                                           );
        this.calculateDimensions();
        this.dataTracker.set( WAKEUP_PROGRESS  , 0                                                                                                                                           );
        this.dataTracker.set( ROAR_TICKS       , 0                                                                                                                                           );
        this.dataTracker.set( FLYING           , false                                                                                                                                       );
        this.dataTracker.set( TARGET           , null                                                                                                                                        );
        this.dataTracker.set( TARGET_POS       , new BlockPos(0, 0, 0)                                                                                                                       );
        this.dataTracker.set( TARGET_LAST_SEEN , 0                                                                                                                                           );
        this.dataTracker.set( EYE_COLOUR       , DragonResourceLoader.getResource(new Identifier(this.dataTracker.get(DRAGON))).eyeColour().asInt()                                          );
        this.dataTracker.set( TAMED_OWNER      , Optional.empty()                                                                                                                            );
        this.dataTracker.set( HAS_BREEDED      , false                                                                                                                                       );
        this.dataTracker.set( NATURAL_SPAWN    , true                                                                                                                                        );
        this.dataTracker.set( SITTING          , false                                                                                                                                       );
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString("dragon", this.dataTracker.get(DRAGON));

        nbt.putBoolean("flying", this.dataTracker.get(FLYING));

        nbt.putInt("roarTicks", this.dataTracker.get(ROAR_TICKS));

        nbt.putInt("wakeupProgress", this.dataTracker.get(WAKEUP_PROGRESS));

        nbt.putInt("age", this.dataTracker.get(AGE));

        nbt.putInt("state", this.dataTracker.get(STATE));

        nbt.putInt("colour", this.dataTracker.get(COLOUR));

        nbt.putInt("hungerLevel", this.dataTracker.get(HUNGER_LEVEL));

        BlockPos spawnPos = this.dataTracker.get(SPAWN_POS);
        nbt.putIntArray("spawnPos", new int[]{spawnPos.getX(), spawnPos.getY(), spawnPos.getZ()});

        DataResult<NbtElement> data = VibrationListener.createCodec(this).encodeStart(NbtOps.INSTANCE, this.gameEventHandler.getListener());
        data.resultOrPartial(DragonHeart.LOGGER::error).ifPresent((element) ->
            nbt.put("vibrationListener", element)
        );

        UUID target = this.getTargetUuid();
        if (target != null) {
            nbt.putUuid("target", target);
            BlockPos targetPos = this.dataTracker.get(TARGET_POS);
            nbt.putIntArray("targetPos", new int[]{targetPos.getX(), targetPos.getY(), targetPos.getZ()});
            nbt.putInt("targetLastSeen", this.dataTracker.get(TARGET_LAST_SEEN));
        } else {
            nbt.remove("target");
            nbt.remove("targetPos");
            nbt.remove("targetLastSeen");
        }

        nbt.putInt("eyeColour", this.dataTracker.get(EYE_COLOUR));

        Optional<UUID> tamedOwner = this.dataTracker.get(TAMED_OWNER);
        if (tamedOwner != null && tamedOwner.isPresent()) {
            nbt.putUuid("tamedOwner", tamedOwner.get());
        } else {
            nbt.remove("tamedOwner");
        }

        nbt.putBoolean("hasBreeded", this.dataTracker.get(HAS_BREEDED));

        nbt.putBoolean("naturalSpawn", this.dataTracker.get(NATURAL_SPAWN));

        nbt.putBoolean("sitting", this.dataTracker.get(SITTING));
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.dataTracker.set(DRAGON, nbt.getString("dragon"));

        this.dataTracker.set(FLYING, nbt.getBoolean("flying"));

        this.dataTracker.set(ROAR_TICKS, nbt.getInt("roarTicks"));

        this.dataTracker.set(WAKEUP_PROGRESS, nbt.getInt("wakeupProgress"));

        this.dataTracker.set(AGE, nbt.getInt("age"));
        this.calculateDimensions();

        this.dataTracker.set(STATE, nbt.getInt("state"));

        this.dataTracker.set(COLOUR, nbt.getInt("colour"));

        this.dataTracker.set(HUNGER_LEVEL, nbt.getInt("hungerLever"));

        int[] spawnPos = nbt.getIntArray("spawnPos");
        if (spawnPos.length == 3) {
            this.dataTracker.set(SPAWN_POS, new BlockPos(spawnPos[0], spawnPos[1], spawnPos[2]));
        } else {
            this.dataTracker.set(SPAWN_POS, getBlockPos());
        }

        DataResult<VibrationListener> data = VibrationListener.createCodec(this).parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("vibrationListener")));
        data.resultOrPartial(DragonHeart.LOGGER::error).ifPresent((element) ->
            this.gameEventHandler.setListener(element, this.world)
        );

        this.dataTracker.set(TARGET, Optional.empty());
        this.dataTracker.set(TARGET_POS, new BlockPos(0, 0, 0));
        this.dataTracker.set(TARGET_LAST_SEEN, 0);
        if (nbt.contains("target")) {
            int[] targetPos = nbt.getIntArray("targetPos");
            if (targetPos.length == 3) {
                this.dataTracker.set(TARGET, Optional.of(nbt.getUuid("target")));
                this.dataTracker.set(TARGET_POS, new BlockPos(targetPos[0], targetPos[1], targetPos[2]));
                this.dataTracker.set(TARGET_LAST_SEEN, nbt.getInt("targetLastSeen"));
            }
        }

        this.dataTracker.set(EYE_COLOUR, nbt.getInt("eyeColour"));

        this.dataTracker.set(TAMED_OWNER, nbt.contains("tamedOwner") ? Optional.of(nbt.getUuid("tamedOwner")) : Optional.empty());

        this.dataTracker.set(HAS_BREEDED, nbt.getBoolean("hasBreeded"));

        this.dataTracker.set(NATURAL_SPAWN, nbt.getBoolean("naturalSpawn"));

        this.dataTracker.set(SITTING, nbt.getBoolean("sitting"));
    }



    public String getDragon() {return this.dataTracker.get(DRAGON);}

    public DragonState getState() {
        return DragonState.fromInt(this.dataTracker.get(STATE));
    }

    public boolean areEyesOpen() {
        return getState() != DragonState.SLEEP;
    }

    public int getStage() {
        return Math.max(Math.min(Math.floorDiv(this.getAge(), Config.CONFIG.dragon.age.stage_ticks), MAX_STAGES), 0);
    }

    public int getColour() {return this.dataTracker.get(COLOUR);}

    public boolean isTamed() {
        Optional<UUID> owner = this.dataTracker.get(TAMED_OWNER);
        return owner != null && owner.isPresent();
    }

    public boolean isNaturallySpawned() {
        return this.dataTracker.get(NATURAL_SPAWN);
    }

    public boolean isTamedOwner(PlayerEntity player) {
        Optional<UUID> tamedOwner = this.dataTracker.get(TAMED_OWNER);
        return (
                isTamed() &&
                tamedOwner.isPresent() &&
                tamedOwner.get().equals(player.getUuid())
        );
    }

    public int getAge() {
        return this.dataTracker.get(AGE);
    }

    public int getMaxAge() {
        return Config.CONFIG.dragon.age.stage_ticks * MAX_STAGES;
    }

    public float getAgeInterpolation() {
        return Math.min(Math.max((float)(getAge()) / (float)(getMaxAge()), 0.0f), 1.0f);
    }

    @Nullable
    public UUID getTargetUuid() {
        Optional<UUID> uuid = this.dataTracker.get(TARGET);
        if (uuid != null && uuid.isPresent() &&
                this.dataTracker.get(TARGET_POS).isWithinDistance(this.getPos(), TARGET_MAX_DISTANCE) &&
                this.dataTracker.get(TARGET_LAST_SEEN) <= TARGET_MAX_LAST_SEEN
        ) {
            return uuid.get();
        } else {
            return null;
        }
    }



    public void setDragon(String dragon) {
        this.dataTracker.set(DRAGON, dragon);
    }

    public void setState(DragonState state) {
        switch (state) {
            case SLEEP:
                this.dataTracker.set(DragonEntity.FLYING, false);
                this.dataTracker.set(DragonEntity.WAKEUP_PROGRESS, 0);
            case PURSUE:
                this.dataTracker.set(DragonEntity.FLYING, true);
                Vec3d velocity = getVelocity();
                setVelocity(velocity.x, JUMP_STRENGTH, velocity.z);
                this.dataTracker.set(FLYING, true);
        }
        this.dataTracker.set(STATE, state.toInt());
    }

    public void setAge(int age) {
        this.dataTracker.set(AGE, age);
    }

    public void addAge(int age) {
        this.dataTracker.set(AGE, Math.min(Math.max(this.dataTracker.get(AGE) + age, 0), getMaxAge()));
        this.calculateDimensions();
    }

    public void setColour(int colour) {
        this.dataTracker.set(COLOUR, colour);
    }

    public void setSpawnPos(BlockPos pos) {this.dataTracker.set(SPAWN_POS, pos);}

    public void setEyeColour(int colour) {this.dataTracker.set(EYE_COLOUR, colour);}

    public void setNaturalSpawn(boolean natural) {this.dataTracker.set(NATURAL_SPAWN, natural);}

    public void toggleSitting() {
        this.dataTracker.set(SITTING, ! this.dataTracker.get(SITTING));
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


    /*------------------------------------------------------------------------------
    | Data, Sounds, Scale, Physics, Despawn, Death, Blocking, Slots, and Immunities |
     ------------------------------------------------------------------------------*/

    // Data
    @Override
    public Text getName() {
        return Text.translatable("entity." + DragonHeart.ID + ".dragon",
                DragonResourceLoader.getResource(new Identifier(this.getDragon())).getName().toString()
        );
    }

    // Sounds
    @Override
    public SoundCategory getSoundCategory() {return SoundCategory.NEUTRAL;}
    @Override
    public SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_HOSTILE_SWIM;
    }
    @Override
    public SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }
    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_HOSTILE_HURT;
    }
    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HOSTILE_DEATH;
    }
    @Override
    public FallSounds getFallSounds() {
        return new FallSounds(SoundEvents.ENTITY_HOSTILE_SMALL_FALL, SoundEvents.ENTITY_HOSTILE_BIG_FALL);
    }
    @Override
    public float getSoundVolume() {
        return 5.0f;
    }
    @Override
    public float getSoundPitch() {
        return MIN_SOUND_PITCH + (MAX_SOUND_PITCH - MIN_SOUND_PITCH) * getAgeInterpolation();
    }

    // Scale
    public float getModelScale() {
        return MIN_MODEL_SCALE + (MAX_MODEL_SCALE - MIN_MODEL_SCALE) * getAgeInterpolation();
    }
    @Override
    public float getScaleFactor() {
        return 1.0f;
    }
    @Override
    public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return EYE_HEIGHT * dimensions.height;
    }
    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        float t = getAgeInterpolation();
        return EntityDimensions.changing(
                MIN_BOX_WIDTH  + (MAX_DIMENSIONS.width  - MIN_BOX_WIDTH  ) * t,
                MIN_BOX_HEIGHT + (MAX_DIMENSIONS.height - MIN_BOX_HEIGHT ) * t
        );
    }

    // Physics
    @Override
    public boolean hasNoDrag() {return ! this.isOnGround();}
    @Override
    public void takeKnockback(double strength, double x, double z) {}
    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    public boolean isPushedByFluids() {return false;}
    @Override
    public boolean hasNoGravity() {return dataTracker.get(FLYING);}

    // Despawn
    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }
    @Override
    public boolean cannotDespawn() {
        return true;
    }
    @Override
    public boolean isDisallowedInPeaceful() {
        return false;
    }
    @Override
    public int getLimitPerChunk() {
        return -1;
    }
    @Override
    public boolean isPersistent() {
        return true;
    }
    @Override
    public boolean shouldSwimInFluids() {
        return ! isSleeping();
    }

    // Death
    @Override
    public boolean shouldDropXp() {
        return ! isTamed() && getStage() >= Config.CONFIG.dragon.age.min_xp_stage;
    }
    @Override
    public boolean shouldDropLoot() {
        return true;
    }
    @Override
    public int getXpToDrop() {
        return (int)(500 * this.getAgeInterpolation());
    }
    @Override
    public boolean shouldAlwaysDropXp() {
        return this.shouldDropXp();
    }

    // Blocking
    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }
    @Override
    public boolean canStartRiding(Entity entity) {
        return false;
    }
    @Override
    public boolean canUsePortals() {
        return false;
    }

    // Slots
    @Override
    public boolean tryEquip(ItemStack equipment) {
        return false;
    }
    @Override
    public boolean canPickupItem(ItemStack stack) {
        return false;
    }
    @Override
    public boolean canPickUpLoot() {
        return false;
    }
    @Override
    public boolean canEquip(ItemStack stack) {
        return false;
    }
    @Override
    public boolean isArmorSlot(EquipmentSlot slot) {
        return false;
    }

    // Immunities
    @Override
    public boolean canBreatheInWater() {
        return true;
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }
    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    @Override
    public boolean hurtByWater() {
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
        //this.goalSelector.add(1, new PursueTargetGoal(this));
        //this.targetSelector.add(1, new FindTargetGoal(this));
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
                List<Entity> entities = world.getOtherEntities(this, Box.of(this.getPos(), Config.CONFIG.dragon.wakeup.roar_radius, Config.CONFIG.dragon.wakeup.roar_radius, Config.CONFIG.dragon.wakeup.roar_radius));
                for (Entity entity : entities) {

                    if (!entity.isRemoved() && entity instanceof LivingEntity livingEntity) {
                        float distance = entity.distanceTo(this);
                        if (distance <= Config.CONFIG.dragon.wakeup.roar_radius) {
                            // Throw & damage entity
                            Vec3d vector = entity.getPos().add(this.getPos().multiply(-1)).multiply(1.0 / distance);
                            float power = 1.0f - distance / Config.CONFIG.dragon.wakeup.roar_radius;
                            Vec3d velocity = vector.multiply(power * Config.CONFIG.dragon.wakeup.roar_knockback);
                            entity.addVelocity(velocity.x, velocity.y, velocity.z);
                            entity.damage(DamageSources.ROAR, power * Config.CONFIG.dragon.wakeup.roar_damage);
                            // Add deafened effect
                            StatusEffectInstance effect = new StatusEffectInstance(StatusEffects.DEAFENED, 10, 0, false, false, true);
                            livingEntity.addStatusEffect(effect, this);
                        }
                    }
                }

                // Destroy blocks in random directions
                for (int i = 0; i < Config.CONFIG.dragon.wakeup.roar_destroy; i++) {
                    float yaw = this.random.nextFloat() * (((float)(Math.PI)) * 2.0f);
                    float arc = (float) Math.PI / 2.0f;
                    float pitch = this.random.nextFloat() * (arc * 0.5f) + (arc * 0.5f);
                    Vec3d vector = new Vec3d(
                            Math.sin(yaw) * Math.cos(pitch),
                            Math.sin(pitch),
                            Math.cos(yaw) * Math.cos(pitch)
                    );
                    BlockHitResult result = world.raycast(new RaycastContext(this.getPos(), this.getPos().add(vector.multiply(Config.CONFIG.dragon.wakeup.roar_radius)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, this));
                    // If raycast hit a block
                    if (result.getType() == HitResult.Type.BLOCK) {
                        // Make sure block is not protected.
                        if (BlockTags.isOf(world.getBlockState(result.getBlockPos()), BlockTags.ROAR_IMMUNE)) {
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

            // Tick age.
            this.addAge(1);

        } else { // world.isClient()

            // Client side blinking animation
            if (blinkTicks > 0) {
                blinkTicks -= 1;

            } else { // blinkTicks <= 0
                if (blinkCooldownTicks > 0) {
                    blinkCooldownTicks -= 1;
                } else { // blinkCooldownTicks <= 0
                    if (this.random.nextFloat() <= BLINK_CHANCE) {
                        blinkCooldownTicks = BLINK_COOLDOWN_TICKS;
                        blinkTicks         = BLINK_TICKS;
                    }
                }

            }

            // Handle dragon size
            this.calculateDimensions();

        }

        // Run parent tick
        super.tick();
    }


    @Override
    public void tickMovement() {
        super.tickMovement();
        //this.setPartPosition(head, new Vec3d(0.0f, 1.5f, 2.0f));
    }


    /*public void setPartPosition(DragonPartEntity part, Vec3d offset) {
        offset = offset.rotateY(this.getHeadYaw());
        part.setPosition(this.getPos().add(offset));
    }*/



    public void incrementWakeupProgress(boolean all, @Nullable Entity alerter) {
        if (all) {
            dataTracker.set(WAKEUP_PROGRESS, Config.CONFIG.dragon.wakeup.vibrations);
        } else {
            dataTracker.set(WAKEUP_PROGRESS, dataTracker.get(WAKEUP_PROGRESS) + 1);
        }
        if (dataTracker.get(WAKEUP_PROGRESS) >= Config.CONFIG.dragon.wakeup.vibrations) {
            alerted(alerter);
        }
    }

    public void alerted(@Nullable Entity ignored /*alerter*/) {
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
                            if (!this.world.isClient()) {
                                rideShoulder(player);
                            }
                            return ActionResult.success(this.world.isClient());
                        } else if (canPlayerMount(player)) {
                            // Player ride dragon.
                            if (!this.world.isClient()) {
                                mountPlayer(player);
                            }
                            return ActionResult.success(this.world.isClient());
                        }
                    } else {
                        // Toggle dragon sitting.
                        if (!this.world.isClient()) {
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
                if (stack == null || stack.isEmpty()) {
                    tamedBy(player);
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
    public boolean occludeVibrationSignals() {
        return true;
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
        if (! this.world.isClient()) {
            dataTracker.set(TAMED_OWNER, Optional.of(tamedOwner.getUuid()));

        } else { // this.world.isClient()
            Box bounds = this.getBoundingBox();
            for (int i = 0; i < 7; i++) {
                this.world.addParticle(
                        ParticleTypes.HEART,
                        this.random.nextDouble() * (bounds.maxX - bounds.minX),
                        this.random.nextDouble() * (bounds.maxY - bounds.minY),
                        this.random.nextDouble() * (bounds.maxZ - bounds.minZ),
                        this.random.nextGaussian() * 0.02,
                        this.random.nextGaussian() * 0.02,
                        this.random.nextGaussian() * 0.02
                );
            }
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
        return isTamedOwner(player) && getStage() >= Config.CONFIG.dragon.age.min_mount_stage;
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


}
