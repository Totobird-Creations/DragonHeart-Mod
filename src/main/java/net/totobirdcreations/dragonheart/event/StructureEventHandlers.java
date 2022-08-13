package net.totobirdcreations.dragonheart.event;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.config.Config;
import net.totobirdcreations.dragonheart.entity.DragonEggEntity;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.data.colour.RGBColour;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;
import net.totobirdcreations.jigsawlogiclib.api.LogicCommandManager;


public class StructureEventHandlers {


    static {
        LogicCommandManager.register(
                new Identifier(DragonHeart.ID, "spawn_dragon"),
                StructureEventHandlers::spawnDragon
        );
        LogicCommandManager.register(
                new Identifier(DragonHeart.ID, "spawn_dragon_egg"),
                StructureEventHandlers::spawnDragonEgg
        );
    }


    public static void spawnDragon(String metadata, World world, BlockPos blockPos, BlockPos structureOrigin) throws Exception {
        int size = switch (metadata) {
            case    "small"  -> 2;
            case    "medium" -> 3;
            case    "large"  -> 4;
            default          -> throw new Exception("Invalid dragon size `" + metadata + "`");
        };

        Identifier id = DataHelper.chooseDragonTypeForPos(world, structureOrigin, world.getRandom());
        if (id == null) {
            throw new Exception("Could not find valid dragon type for biome at " + structureOrigin.toString() + ".");
        }
        DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(id);

        int min_age = Config.CONFIG.dragon.age.stage_ticks * size;
        int max_age = Config.CONFIG.dragon.age.stage_ticks * (size + 1);
        DragonEntity entity = Entities.DRAGON.create(world);
        assert entity != null;
        Random random = entity.getRandom();
        entity.setPosition(Vec3d.ofBottomCenter(blockPos));
        entity.setDragonType(resource.id());
        entity.setAge(random.nextBetween(min_age, max_age));
        entity.setColour(resource.variateBodyColour(
                resource.chooseBodyColour(structureOrigin),
                structureOrigin
        ));
        entity.setSpawnPos(blockPos);
        entity.setNaturalSpawn(true);
        DataHelper.randomiseEntityRotation(entity);
        world.spawnEntity(entity);

    }


    public static void spawnDragonEgg(String metadata, World world, BlockPos blockPos, BlockPos structureOrigin) throws Exception {
        Random random = Random.create(blockPos.hashCode());
        if (random.nextBoolean()) {

            Identifier id = DataHelper.chooseDragonTypeForPos(world, structureOrigin, world.getRandom());
            if (id == null) {
                throw new Exception("Could not find valid dragon egg type for biome at " + structureOrigin.toString() + ".");
            }
            DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(id);

            DragonEggEntity entity = Entities.DRAGON_EGG.create(world);
            assert entity != null;
            entity.setPosition(Vec3d.ofBottomCenter(blockPos));
            entity.setDragonType(resource.id());
            entity.setColour(resource.variateBodyColour(resource.variateBodyColour(
                    resource.chooseBodyColour(structureOrigin),
                    structureOrigin
            ), entity.getUuid()));
            DataHelper.randomiseEntityRotation(entity);
            world.spawnEntity(entity);
        }
    }


    public static void register() {}

}
