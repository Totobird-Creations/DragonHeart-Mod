package net.totobirdcreations.dragonheart.util.helper;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;

import javax.annotation.Nullable;
import java.util.ArrayList;


public class DataHelper {

    public static boolean dragonRecipeTypeMatches(Identifier entityType, Identifier recipeType) {
        if (entityType.equals(NbtHelper.EMPTY_TYPE)) {
            return recipeType.equals(NbtHelper.EMPTY_TYPE);
        }
        return stringPartMatches(entityType.getNamespace(), recipeType.getNamespace())
                && stringPartMatches(entityType.getPath(), recipeType.getPath());
    }

    public static boolean stringPartMatches(String source, String pattern) {
        return pattern.equals(".") || pattern.equals(source);
    }


    @Nullable
    public static Identifier chooseDragonTypeForPos(WorldView world, BlockPos pos, Random random) {
        ArrayList<Identifier> allowedIds = new ArrayList<>();
        for (Identifier id : DragonResourceLoader.getIdentifiers()) {
            DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(id);
            if (world.getBiome(pos).isIn(
                    resource.spawnsIn()
            )) {
                allowedIds.add(id);
            }
        }
        if (allowedIds.size() > 0) {
            int index = allowedIds.size() > 1
                    ? random.nextInt(allowedIds.size())
                    : 0;
            return allowedIds.get(index);
        } else {
            return null;
        }
    }


    public static void randomiseEntityRotation(Entity entity) {
        float yaw = entity.getWorld().getRandom().nextFloat() * 360.0f;
        entity.setYaw(yaw);
        entity.setBodyYaw(yaw);
        entity.setHeadYaw(yaw);
    }

}
