package net.totobirdcreations.dragonheart.item.staff;

import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

import static java.lang.Math.*;
import static java.lang.Math.cos;



public class Staff extends Item {


    public static double RANDOM = 10.0;
    public static int    COUNT  = 10;

    public int ticksLeft = 0;



    public Staff(Settings settings) {

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
                throwParticle(entity);
            }
            ticksLeft -= 1;
        }

    }


    public void throwParticle(Entity user) {

        Random randomNumberGenerator = new Random();
        double   yaw    = (user.getHeadYaw() + (-RANDOM + (2.0 * RANDOM) * randomNumberGenerator.nextDouble())) * (PI / 180.0);
        double   pitch  = (user.getPitch() + (-RANDOM + (2.0 * RANDOM) * randomNumberGenerator.nextDouble())) * (PI / 180.0);
        Vector3d vector = new Vector3d(cos(pitch) * sin(-yaw), sin(pitch), cos(pitch) * cos(yaw));

        /*user.getWorld().addParticle(
                PARTICLE,
                true,
                user.getX(),
                user.getEyeY(),
                user.getZ(),
                vector.x,
                -vector.y,
                vector.z
        );*/

    }


}
