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
import net.minecraft.nbt.NbtCompound;
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


public abstract class DragoneggEntity extends MobEntity implements IAnimatable {

    public static EntityDimensions DIMENSIONS = EntityDimensions.changing(0.375f, 0.5f );

    /*-------------
    | Constructors |
     -------------*/

    public DragoneggEntity(EntityType<? extends MobEntity> entityType, World world) {
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

    static {
        COLOUR = DataTracker.registerData( DragoneggEntity.class , TrackedDataHandlerRegistry.INTEGER );
    }


    public void setColour(int colour) {
        dataTracker.set(COLOUR, colour);
    }

    public int getColour() {return dataTracker.get(COLOUR);}


    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking( COLOUR , 8355711 );
    }


    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        net.minecraft.util.math.random.Random rand;
        dataTracker.set( COLOUR , 8355711 );
        super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        return entityData;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("Colour", dataTracker.get(COLOUR));
    }


    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        dataTracker.set(COLOUR, nbt.getInt("Colour"));
    }


    /*-------------------
    | Animation Handling |
     -------------------*/

    public final AnimationFactory animationFactory = new AnimationFactory(this);


    public PlayState animationPredicate(AnimationEvent<DragoneggEntity> event) {

        AnimationBuilder builder   = new AnimationBuilder();
        PlayState        playState = PlayState.CONTINUE;

        builder.addAnimation("idle");

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
        boolean res = super.damage(source, this.getHealth());
        return res;
    }

    @Override
    public void onDeath(DamageSource source) {
        this.remove(RemovalReason.KILLED);
        this.dropItem(this.getDropItem());
    }

    public abstract Item getDropItem();


    /*---------
    | Spawning |
     ---------*/

    public void spawnEntity() {
        ItemEntity entity = this.getSpawnEntity().create(this.getWorld());
        entity.setPosition(this.getPos());
        entity.setYaw(this.getRandom().nextFloat() % (float)(Math.PI * 2.0f));

        NbtCompound nbt = entity.getStack().getOrCreateSubNbt("display");
        nbt.putInt("color", this.getColour());

        this.getWorld().spawnEntity(entity);
    }

    public abstract EntityType<? extends ItemEntity> getSpawnEntity();

}
