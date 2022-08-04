package net.totobirdcreations.dragonheart.entity.dragon_egg;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.forge.DragonEggIncubatorBlock;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.sound.SoundEvents;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;


public class DragonEggEntity extends MobEntity implements IAnimatable {

    public static EntityDimensions DIMENSIONS = EntityDimensions.changing(0.375f, 0.5f);

    public static int MIN_SPAWN_AGE = 120000; // 100m
    public static int MAX_SPAWN_AGE = 240000; // 200m

    public static int   SHAKE_COOLDOWN_TICKS = 100; // 5s
    public static int   SHAKE_TICKS          = 40;  // 2s
    public static float SHAKE_CHANCE         = 0.01f;

    public int        shakeCooldownTicks = SHAKE_COOLDOWN_TICKS;
    public int        shakeTicks         = 0;

    /*-------------
    | Constructors |
     -------------*/

    public DragonEggEntity(EntityType<? extends DragonEggEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return createMobAttributes()
                .add( EntityAttributes.GENERIC_MAX_HEALTH , 0.01d );
    }


    /*--------------
    | Data Handling |
     --------------*/

    public static final TrackedData<String>  DRAGON;
    public static final TrackedData<Integer> COLOUR;
    public static final TrackedData<Integer> AGE;
    public static final TrackedData<Integer> SPAWN_AGE;
    public static final TrackedData<Integer> EYE_COLOUR;

    static {
        DRAGON     = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.STRING  );
        COLOUR     = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        AGE        = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        SPAWN_AGE  = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        EYE_COLOUR = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
    }


    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking( DRAGON     , "" );
        this.dataTracker.startTracking( COLOUR     , 0  );
        this.dataTracker.startTracking( AGE        , 0  );
        this.dataTracker.startTracking( SPAWN_AGE  , 0  );
        this.dataTracker.startTracking( EYE_COLOUR , 0  );
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.dataTracker.set( DRAGON     , NbtHelper.EMPTY_TYPE.toString() );
        this.dataTracker.set( COLOUR     , 8355711                         );
        this.dataTracker.set( AGE        , 0                               );
        this.dataTracker.set( SPAWN_AGE  , 0                               );
        this.dataTracker.set( EYE_COLOUR , 8355711                         );

        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString ( "dragon"    , dataTracker.get( DRAGON     ));
        nbt.putInt    ( "colour"    , dataTracker.get( COLOUR     ));
        nbt.putInt    ( "age"       , dataTracker.get( AGE        ));
        nbt.putInt    ( "spawnAge"  , dataTracker.get( SPAWN_AGE  ));
        nbt.putInt    ( "eyeColour" , dataTracker.get( EYE_COLOUR ));
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.dataTracker.set( DRAGON     , nbt.getString ("dragon"    ));
        this.dataTracker.set( COLOUR     , nbt.getInt    ("colour"    ));
        this.dataTracker.set( AGE        , nbt.getInt    ("age"       ));
        this.dataTracker.set( SPAWN_AGE  , nbt.getInt    ("spawnAge"  ));
        this.dataTracker.set( EYE_COLOUR , nbt.getInt    ("eyeColour" ));
    }


    public String getDragon() {return this.dataTracker.get(DRAGON);}

    public int getColour() {return this.dataTracker.get(COLOUR);}

    public int getAge() {return this.dataTracker.get(AGE);}

    public int getSpawnAge() {return this.dataTracker.get(SPAWN_AGE);}

    public int getEyeColour() {return this.dataTracker.get(EYE_COLOUR);}


    public void setDragon(String dragon) {this.dataTracker.set(DRAGON, dragon);}

    public void setColour(int colour) {this.dataTracker.set(COLOUR, colour);}

    public void setAge(int age) {this.dataTracker.set(AGE, age);}
    public void addAge(int age) {this.dataTracker.set(AGE, getAge() + age);}

    public void setSpawnAge(int age) {this.dataTracker.set(SPAWN_AGE, age);}

    public void setEyeColour(int colour) {this.dataTracker.set(EYE_COLOUR, colour);}


    /*-------------------
    | Animation Handling |
     -------------------*/

    public final AnimationFactory animationFactory = new AnimationFactory(this);


    public PlayState animationPredicate(AnimationEvent<DragonEggEntity> event) {

        AnimationBuilder builder   = new AnimationBuilder();
        PlayState        playState = PlayState.CONTINUE;

        if (shakeTicks > 0) {
            builder.addAnimation("animation.dragon_egg.shake", false);
        } else {
            builder.addAnimation("animation.dragon_egg.idle", true);
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


    /*--------
    | Destroy |
     --------*/

    @Override
    public boolean damage(DamageSource source, float amount) {
        return super.damage(source, this.getHealth());
    }

    @Override
    public void onDeath(DamageSource source) {
        this.remove(RemovalReason.KILLED);

        ItemStack stack = new ItemStack(DragonItems.DRAGON_EGG);
        DragonColouredItem.setColour(stack, this.getColour());
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString ("dragon"    , this.getDragon()    );
        nbt.putInt    ("age"       , this.getAge()       );
        nbt.putInt    ("spawnAge"  , this.getSpawnAge()  );
        nbt.putInt    ("eyeColour" , this.getEyeColour() );
        this.dropStack(stack);
    }


    /*---------
    | Spawning |
     ---------*/

    @Override
    public void tick() {
        if (! world.isClient()) {

            if (this.isIncubated()) {
                this.addAge(1);
                if (this.getAge() >= this.getSpawnAge()) {
                    this.crack();
                }
            }

        } else { // world.isClient()

            if (shakeTicks > 0) {
                shakeTicks -= 1;

            } else { // shakeTicks <= 0
                if (shakeCooldownTicks > 0) {
                    shakeCooldownTicks -= 1;
                } else { // shakeCooldownTicks <= 0
                    Random rand = new Random();
                    if (this.isIncubated() && rand.nextFloat() <= SHAKE_CHANCE) {
                        shakeCooldownTicks = SHAKE_COOLDOWN_TICKS;
                        shakeTicks         = SHAKE_TICKS;
                    }
                }

            }

        }
    }


    public boolean isIncubated() {
        BlockState state = world.getBlockState(getBlockPos().add(new Vec3i(0, -1, 0)));
        return state.getBlock() instanceof DragonEggIncubatorBlock && state.get(Properties.POWERED);
    }


    public void crack() {
        SoundEvent hatchSound = this.getHatchSound();
        if (hatchSound != null) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), hatchSound, this.getSoundCategory(), this.getSoundVolume(), this.getSoundPitch(), true);
        }

        ((ServerWorld)world).spawnParticles(
                new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(DragonItems.DRAGON_EGG_CREATIVE)),
                this.getX(), this.getY() + getHeight() / 2.0f, this.getZ(),
                25,
                0.05, 0.1, 0.05,
                0.075
        );

        DragonEntity dragon = this.convertTo(Entities.DRAGON, false);
        assert dragon != null;
        dragon.setDragon(this.dataTracker.get(DRAGON));
        dragon.setNaturalSpawn(false);
        dragon.setState(DragonEntity.DragonState.WANDER);
        this.createEntity(dragon);
    }


    public void createEntity(DragonEntity dragon) {
        dragon.setYaw(this.getRandom().nextFloat() % (float)(Math.PI * 2.0f));

        dragon.setAge(0);
        dragon.setState(DragonEntity.DragonState.WANDER);
        dragon.setColour(this.getColour());
        dragon.setSpawnPos(dragon.getBlockPos());
        dragon.setEyeColour(this.getEyeColour());
        dragon.setNaturalSpawn(false);
    }


    /*--------------
    | Data & Sounds |
     --------------*/

    // Data
    @Override
    public Text getName() {
        return Text.translatable("entity." + DragonHeart.ID + ".dragon_egg",
                DragonResourceLoader.getResource(new Identifier(this.getDragon())).getName()
        );
    }

    // Sounds
    @Nullable
    public SoundEvent getPlaceSound() {return SoundEvents.DRAGON_EGG_PLACE;}
    @Nullable
    public SoundEvent getHatchSound() {return SoundEvents.DRAGON_EGG_HATCH;}
    @Override
    @Nullable
    public SoundEvent getHurtSound(DamageSource source) {
        return null;
    }
    @Override
    @Nullable
    public SoundEvent getDeathSound() {
        return SoundEvents.DRAGON_EGG_BREAK;
    }
    @Override
    public float getSoundVolume() {
        return 0.5f;
    }
    @Override
    public float getSoundPitch() {
        return 0.75f;
    }
    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.BLOCKS;
    }

}
