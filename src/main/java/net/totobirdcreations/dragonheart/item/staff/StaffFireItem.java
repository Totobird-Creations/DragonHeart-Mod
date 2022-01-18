package net.totobirdcreations.dragonheart.item.staff;

import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

import static java.lang.Math.*;

public class StaffFireItem extends Item {

    public static double  RANDOM = 10.0;
    public static int     COUNT  = 25;

    public        boolean using  = false;

    public StaffFireItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        for (int i = 0; i < COUNT; i++) {
            throwParticle(player);
        }

        return TypedActionResult.success(player.getMainHandStack());
    }


    public void throwParticle(PlayerEntity player) {
        Random randomNumberGenerator = new Random();
        double   yaw    = (player.getHeadYaw() + (-RANDOM + (2.0 * RANDOM) * randomNumberGenerator.nextDouble())) * (PI / 180.0);
        double   pitch  = (player.getPitch() + (-RANDOM + (2.0 * RANDOM) * randomNumberGenerator.nextDouble())) * (PI / 180.0);
        Vector3d vector = new Vector3d(cos(pitch) * sin(-yaw), sin(pitch), cos(pitch) * cos(yaw));

        player.getWorld().addParticle(
                ParticleTypes.FLAME,
                true,
                player.getX(),
                player.getEyeY(),
                player.getZ(),
                vector.x,
                -vector.y,
                vector.z
        );
    }

}
