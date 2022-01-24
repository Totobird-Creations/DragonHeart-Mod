package net.totobirdcreations.dragonheart.item.staff;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.entity.Dragonbreath;
import net.totobirdcreations.dragonheart.entity.DragonbreathIce;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Math.cos;



public class StaffIce extends Item {


    public static double RANDOM = 10.0;
    public static int    COUNT  = 10;

    public int ticksLeft = 0;



    public StaffIce(Settings settings) {

        super(settings);

    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ticksLeft = 5;
        return TypedActionResult.success(user.getMainHandStack());

    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (! selected) {
            ticksLeft = 0;
        }

        if (ticksLeft > 0) {
            for (int i = 0; i < COUNT; i++) {
                try {
                    throwParticle(entity);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            ticksLeft -= 1;
        }

    }


    public void throwParticle(Entity user) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        World world = user.getWorld();

        if (! world.isClient()) {
            Random randomNumberGenerator = new Random();
            double yaw = (user.getHeadYaw() + (-RANDOM + (2.0 * RANDOM) * randomNumberGenerator.nextDouble())) * (PI / 180.0);
            double pitch = (-user.getPitch() + (-RANDOM + (2.0 * RANDOM) * randomNumberGenerator.nextDouble())) * (PI / 180.0);
            Vec3d vector = new Vec3d(cos(pitch) * sin(-yaw), sin(pitch), cos(pitch) * cos(yaw));

            Dragonbreath entity = new DragonbreathIce(world, (LivingEntity)user);
            entity.setOwner(user);
            entity.setPitch(user.getPitch());
            entity.setYaw(user.getYaw());
            entity.setVelocity(vector);
            world.spawnEntity(entity);
        }

    }


}
