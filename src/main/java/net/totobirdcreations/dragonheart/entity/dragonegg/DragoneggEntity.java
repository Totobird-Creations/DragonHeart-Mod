package net.totobirdcreations.dragonheart.entity.dragonegg;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.item.misc.MiscItems;
import net.totobirdcreations.dragonheart.sound.ModSoundEvents;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;


public abstract class DragoneggEntity<T extends DragonEntity> extends MobEntity implements IAnimatable {

    public static EntityDimensions DIMENSIONS = EntityDimensions.changing(0.375f, 0.5f);

    public static int MIN_SPAWN_AGE = 120000; // 100m
    public static int MAX_SPAWN_AGE = 240000; // 200m

    public static int   SHAKE_COOLDOWN_TICKS = 100; // 5s
    public static int   SHAKE_TICKS          = 40;  // 2s
    public static float SHAKE_CHANCE         = 0.005f;

    public int        shakeCooldownTicks = SHAKE_COOLDOWN_TICKS;
    public int        shakeTicks         = 0;

    /*-------------
    | Constructors |
     -------------*/

    public DragoneggEntity(EntityType<? extends DragoneggEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return createMobAttributes()
                .add( EntityAttributes.GENERIC_MAX_HEALTH , 0.01d );
    }


    /*--------------
    | Data Handling |
     --------------*/

    public static final TrackedData<Integer> COLOUR;
    public static final TrackedData<Integer> AGE;
    public static final TrackedData<Integer> SPAWN_AGE;
    public static final TrackedData<Integer> EYE_COLOUR;

    static {
        COLOUR     = DataTracker.registerData( DragoneggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        AGE        = DataTracker.registerData( DragoneggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        SPAWN_AGE  = DataTracker.registerData( DragoneggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        EYE_COLOUR = DataTracker.registerData( DragoneggEntity.class , TrackedDataHandlerRegistry.INTEGER );
    }


    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking( COLOUR     , 0 );
        this.dataTracker.startTracking( AGE        , 0 );
        this.dataTracker.startTracking( SPAWN_AGE  , 0 );
        this.dataTracker.startTracking( EYE_COLOUR , 0 );
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        dataTracker.set( COLOUR     , 8355711 );
        dataTracker.set( AGE        , 0       );
        dataTracker.set( SPAWN_AGE  , 0       );
        dataTracker.set( EYE_COLOUR , 8355711 );

        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("Colour"    , dataTracker.get(COLOUR     ));
        nbt.putInt("Age"       , dataTracker.get(AGE        ));
        nbt.putInt("SpawnAge"  , dataTracker.get(SPAWN_AGE  ));
        nbt.putInt("EyeColour" , dataTracker.get(EYE_COLOUR ));
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        dataTracker.set(COLOUR     , nbt.getInt("Colour"    ));
        dataTracker.set(AGE        , nbt.getInt("Age"       ));
        dataTracker.set(SPAWN_AGE  , nbt.getInt("SpawnAge"  ));
        dataTracker.set(EYE_COLOUR , nbt.getInt("EyeColour" ));
    }


    public int getColour() {return dataTracker.get(COLOUR);}

    public int getAge() {return dataTracker.get(AGE);}

    public int getSpawnAge() {return dataTracker.get(SPAWN_AGE);}

    public int getEyeColour() {return dataTracker.get(EYE_COLOUR);}


    public void setColour(int colour) {dataTracker.set(COLOUR, colour);}

    public void setAge(int age) {dataTracker.set(AGE, age);}
    public void addAge(int age) {dataTracker.set(AGE, getAge() + age);}

    public void setSpawnAge(int age) {dataTracker.set(SPAWN_AGE, age);}

    public void setEyeColour(int colour) {dataTracker.set(EYE_COLOUR, colour);}


    /*-------------------
    | Animation Handling |
     -------------------*/

    public final AnimationFactory animationFactory = new AnimationFactory(this);


    public PlayState animationPredicate(AnimationEvent<DragoneggEntity<T>> event) {

        AnimationBuilder builder   = new AnimationBuilder();
        PlayState        playState = PlayState.CONTINUE;

        if (shakeTicks > 0) {
            builder.addAnimation("animation.dragonegg.shake", false);
        } else {
            builder.addAnimation("animation.dragonegg.idle", true);
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

        ItemStack   stack = new ItemStack(this.getDropItem());
        NbtCompound nbt   = stack.getOrCreateSubNbt("display");
        nbt.putInt("color"     , this.getColour()    );
        nbt = stack.getOrCreateNbt();
        nbt.putInt("Colour"    , this.getColour()    );
        nbt.putInt("Age"       , this.getAge()       );
        nbt.putInt("SpawnAge"  , this.getSpawnAge()  );
        nbt.putInt("EyeColour" , this.getEyeColour() );
        this.dropStack(stack);
    }

    public abstract Item getDropItem();


    /*---------
    | Spawning |
     ---------*/

    @Override
    public void tick() {
        if (! world.isClient()) {

            this.addAge(1);
            if (this.getAge() >= this.getSpawnAge()) {
                this.crack();
            }

        } else { // world.isClient()

            if (shakeTicks > 0) {
                shakeTicks -= 1;

            } else { // shakeTicks <= 0
                if (shakeCooldownTicks > 0) {
                    shakeCooldownTicks -= 1;
                } else { // shakeCooldownTicks <= 0
                    Random rand = new Random();
                    if (rand.nextFloat() <= SHAKE_CHANCE) {
                        shakeCooldownTicks = SHAKE_COOLDOWN_TICKS;
                        shakeTicks         = SHAKE_TICKS;
                    }
                }

            }

        }
    }


    public void crack() {
        SoundEvent hatchSound = this.getHatchSound();
        if (hatchSound != null) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), hatchSound, this.getSoundCategory(), this.getSoundVolume(), this.getSoundPitch(), true);
        }

        ItemStack   stack = new ItemStack(MiscItems.DRAGONEGG_FIRE);
        NbtCompound nbt   = stack.getOrCreateSubNbt("display");
        nbt.putInt("color", getColour());

        ((ServerWorld)world).spawnParticles(
                new ItemStackParticleEffect(ParticleTypes.ITEM, stack),
                this.getX(), this.getY() + getHeight() / 2.0f, this.getZ(),
                25,
                0.05, 0.1, 0.05,
                0.075
        );

        T dragon = this.convertTo(getSpawnEntity(), false);
        assert dragon != null;
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


    public abstract EntityType<T> getSpawnEntity();


    /*-------
    | Sounds |
     -------*/

    @Nullable
    public SoundEvent getPlaceSound() {return ModSoundEvents.DRAGONEGG_PLACE;}

    @Nullable
    public SoundEvent getHatchSound() {return ModSoundEvents.DRAGONEGG_HATCH;}

    @Override
    @Nullable
    public SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Override
    @Nullable
    public SoundEvent getDeathSound() {
        return ModSoundEvents.DRAGONEGG_BREAK;
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
