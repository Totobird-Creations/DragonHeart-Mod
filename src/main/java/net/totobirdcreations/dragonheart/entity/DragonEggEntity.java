package net.totobirdcreations.dragonheart.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.dragon.forge.DragonEggIncubatorBlock;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator.DragonEggIncubatorBlockEntity;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.item.dragon.DragonItems;
import net.totobirdcreations.dragonheart.item.util.DragonColouredItem;
import net.totobirdcreations.dragonheart.particle_effect.ParticleEffects;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.sound.SoundEvents;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;
import net.totobirdcreations.dragonheart.util.helper.NbtHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Collection;


public class DragonEggEntity extends MobEntity implements IAnimatable {

    public static EntityDimensions DIMENSIONS = EntityDimensions.changing(0.375f, 0.5f);

    public static int MIN_SPAWN_AGE       = 120000; // 100m
    public static int MAX_SPAWN_AGE       = 240000; // 200m
    public static int MAX_WARMTH_DISTANCE = 2;

    public static int   SHAKE_COOLDOWN_TICKS = 20; // 1s
    public static int   SHAKE_TICKS          = 40; // 2s
    public static float SHAKE_CHANCE         = 0.125f;

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

    public static final TrackedData<String>  TYPE;
    public static final TrackedData<Integer> COLOUR;
    public static final TrackedData<Integer> AGE;
    public static final TrackedData<Integer> SPAWN_AGE;
    public static final TrackedData<Integer> EYE_COLOUR;

    static {
        TYPE       = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.STRING  );
        COLOUR     = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        AGE        = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        SPAWN_AGE  = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
        EYE_COLOUR = DataTracker.registerData( DragonEggEntity.class , TrackedDataHandlerRegistry.INTEGER );
    }


    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking( TYPE       , "" );
        this.dataTracker.startTracking( COLOUR     , 0  );
        this.dataTracker.startTracking( AGE        , 0  );
        this.dataTracker.startTracking( SPAWN_AGE  , 0  );
        this.dataTracker.startTracking( EYE_COLOUR , 0  );
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.dataTracker.set( TYPE       , NbtHelper.EMPTY_TYPE.toString() );
        this.dataTracker.set( COLOUR     , 8355711                         );
        this.dataTracker.set( AGE        , 0                               );
        this.dataTracker.set( SPAWN_AGE  , 0                               );
        this.dataTracker.set( EYE_COLOUR , 8355711                         );

        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putString ( "type"      , dataTracker.get( TYPE       ));
        nbt.putInt    ( "colour"    , dataTracker.get( COLOUR     ));
        nbt.putInt    ( "age"       , dataTracker.get( AGE        ));
        nbt.putInt    ( "spawnAge"  , dataTracker.get( SPAWN_AGE  ));
        nbt.putInt    ( "eyeColour" , dataTracker.get( EYE_COLOUR ));
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.dataTracker.set( TYPE       , nbt.getString ("type"      ));
        this.dataTracker.set( COLOUR     , nbt.getInt    ("colour"    ));
        this.dataTracker.set( AGE        , nbt.getInt    ("age"       ));
        this.dataTracker.set( SPAWN_AGE  , nbt.getInt    ("spawnAge"  ));
        this.dataTracker.set( EYE_COLOUR , nbt.getInt    ("eyeColour" ));
    }


    public Identifier getDragonType() {return new Identifier(this.dataTracker.get(TYPE));}

    public RGBColour getColour() {return new RGBColour(this.dataTracker.get(COLOUR));}

    public int getAge() {return this.dataTracker.get(AGE);}

    public int getSpawnAge() {return this.dataTracker.get(SPAWN_AGE);}

    public int getEyeColour() {return this.dataTracker.get(EYE_COLOUR);}


    public void setDragonType(Identifier dragon) {this.dataTracker.set(TYPE, dragon.toString());}

    public void setColour(RGBColour colour) {this.setColour(colour.asInt());}
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
        NbtHelper.setItemDragonType  (stack, this.getDragonType());
        DragonColouredItem.setColour (stack, this.getColour());
        nbt.putInt    ("age"       , this.getAge()       );
        nbt.putInt    ("spawnAge"  , this.getSpawnAge()  );
        nbt.putInt    ("eyeColour" , this.getEyeColour() );
        ItemEntity entity = this.dropStack(stack);
        entity.setNeverDespawn();
    }


    /*---------
    | Spawning |
     ---------*/

    @Override
    public void tick() {
        if (! this.world.isClient()) {

            if (this.isIncubated()) {
                this.addAge(1);
            }
            if (this.getSpawnAge() > 0 && this.getAge() >= this.getSpawnAge()) {
                this.crack();
            }

            if (this.world instanceof ServerWorld world) {
                Collection<Entity> entities = world.getOtherEntities(this, this.getBoundingBox());
                for (Entity entity : entities) {
                    if (entity instanceof DragonEggEntity) {
                        double angle;
                        if (entity.getPos().equals(this.getPos())) {
                            angle = Random.create().nextFloat() * Math.PI * 2.0f;
                        } else {
                            angle = Math.atan2(
                                    entity.getZ() - this.getZ(),
                                    entity.getX() - this.getX()
                            );
                        }
                        double x = Math.cos(angle) * 0.01f;
                        double z = Math.sin(angle) * 0.01f;
                        this.addVelocity(x, 0.0f, z);
                        entity.addVelocity(x, 0.0f, z);
                    }
                }
            }

        } else { // world.isClient()

            if (this.shakeTicks > 0) {
                this.shakeTicks -= 1;

            } else { // shakeTicks <= 0
                if (this.shakeCooldownTicks > 0) {
                    this.shakeCooldownTicks -= 1;
                } else { // shakeCooldownTicks <= 0
                    Random rand = Random.create();
                    if (this.isIncubated() && rand.nextFloat() <= SHAKE_CHANCE) {
                        this.shakeCooldownTicks = SHAKE_COOLDOWN_TICKS;
                        this.shakeTicks         = SHAKE_TICKS;
                    }
                }

            }

        }

        super.tick();
    }


    public boolean isIncubated() {
        for (int i = 0; i < MAX_WARMTH_DISTANCE + 1; i++) {
            BlockPos   pos   = this.getBlockPos().down(i);
            BlockState state = this.world.getBlockState(pos);
            if (state.getBlock() instanceof DragonEggIncubatorBlock) {
                if (state.get(Properties.POWERED) && this.world.getBlockEntity(pos) instanceof DragonEggIncubatorBlockEntity entity) {
                    return entity.power.equals(this.getDragonType());
                }
                break;
            }
        }
        return false;
    }


    public void crack() {
        SoundEvent hatchSound = this.getHatchSound();
        if (hatchSound != null) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), hatchSound, this.getSoundCategory(), this.getSoundVolume(), this.getSoundPitch(), true);
        }

        if (this.world instanceof ServerWorld world) {
            for (int i = 0; i < 25; i++) {
                ParticleEffects.createDragonEggCrack(world, this.getPos(), this.getColour());
            }
        }

        DragonEntity dragon = this.convertTo(Entities.DRAGON, false);
        assert dragon != null;
        this.createEntity(dragon);
    }


    public void createEntity(DragonEntity dragon) {
        dragon.setDragonType(this.dataTracker.get(TYPE));
        dragon.setAge(0);
        dragon.setState(DragonEntity.DragonState.WANDER);
        dragon.setColour(this.getColour());
        dragon.setSpawnPos(dragon.getBlockPos());
        dragon.setNaturalSpawn(false);
        DataHelper.randomiseEntityRotation(dragon);
    }


    /*--------------
    | Data & Sounds |
     --------------*/

    // Data
    @Override
    public Text getName() {
        return Text.translatable("entity." + DragonHeart.ID + ".dragon_egg",
                DragonResourceLoader.getResource(this.getDragonType()).getName()
        );
    }
    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity instanceof DragonEggEntity) {
            super.pushAwayFrom(entity);
        }
    }

    // Sounds
    @Nullable
    public net.minecraft.sound.SoundEvent getPlaceSound() {return SoundEvents.DRAGON_EGG_PLACE;}
    @Nullable
    public net.minecraft.sound.SoundEvent getHatchSound() {return SoundEvents.DRAGON_EGG_HATCH;}
    @Override
    @Nullable
    public net.minecraft.sound.SoundEvent getHurtSound(DamageSource source) {
        return null;
    }
    @Override
    @Nullable
    public net.minecraft.sound.SoundEvent getDeathSound() {
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
