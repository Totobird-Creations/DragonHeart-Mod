package net.totobirdcreations.dragonheart.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.block.DragonforgeBlocks;
import net.totobirdcreations.dragonheart.block.entity.DragonforgeApertureBlockEntity;
import net.totobirdcreations.dragonheart.item.HiddenItems;
import net.totobirdcreations.dragonheart.packet.EntitySpawnPacket;


public class Dragonbreath extends ThrownItemEntity {


    public Dragonbreath(EntityType<? extends ThrownItemEntity> entityType, World world) {

        super(entityType, world);

    }


    public Dragonbreath(EntityType<? extends ThrownItemEntity> entityType, World world, LivingEntity owner) {

        super(entityType, owner, world);

    }


    public Dragonbreath(EntityType<? extends ThrownItemEntity> entityType, double x, double y, double z, World world) {

        super(entityType, x, y, z, world);

    }


    public Dragonbreath(World world, LivingEntity owner) {

        super(ModEntities.DRAGONBREATH_FIRE_TYPE, owner, world);

    }


    public Dragonbreath(double x, double y, double z, World world) {

        super(ModEntities.DRAGONBREATH_FIRE_TYPE, x, y,z, world);

    }


    @Override
    public Item getDefaultItem() {

        return HiddenItems.DRAGONBREATH_FIRE;

    }


    @Override
    public Packet createSpawnPacket() {

        return EntitySpawnPacket.create(this, ModEntities.SPAWN_PACKET);

    }


    public void powerApertureIfFound(World world, BlockPos pos, BlockState state) {

        if (state.isOf(DragonforgeBlocks.DRAGONFORGE_APERTURE_BASE.block)) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof DragonforgeApertureBlockEntity) {
                ((DragonforgeApertureBlockEntity) entity).breathedOn();
            }
        }

    }


}
