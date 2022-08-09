package net.totobirdcreations.dragonheart.event;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.config.Config;
import net.totobirdcreations.dragonheart.entity.Entities;
import net.totobirdcreations.dragonheart.entity.dragon.DragonEntity;
import net.totobirdcreations.dragonheart.resource.DragonResourceLoader;
import net.totobirdcreations.dragonheart.util.helper.DataHelper;
import net.totobirdcreations.jigsawlogiclib.api.LogicCommandManager;


public class StructureEventHandlers {


    static {
        LogicCommandManager.register(
                new Identifier(DragonHeart.ID, "spawn_dragon"),
                StructureEventHandlers::spawnDragon
        );
    }


    public static void spawnDragon(String metadata, World world, BlockPos pos) throws Exception {
        Identifier id = DataHelper.chooseDragonTypeForPos(world, pos, world.getRandom());
        if (id == null) {
            return;
        }
        DragonResourceLoader.DragonResource resource = DragonResourceLoader.getResource(id);
        int size = switch (metadata) {
            case    "small"  -> 2;
            case    "medium" -> 3;
            case    "large"  -> 4;
            default          -> throw new Exception("Invalid dragon size `" + metadata + "`");
        };
        int min_age = Config.CONFIG.dragon.age.stage_ticks * size;
        int max_age = Config.CONFIG.dragon.age.stage_ticks * (size + 1);
        DragonEntity entity = Entities.DRAGON.create(world);
        assert entity != null;
        Random random = entity.getRandom();
        entity.setPosition(new Vec3d(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f));
        entity.setDragon(resource.id().toString());
        entity.setAge(random.nextBetween(min_age, max_age));
        entity.setColour(resource.chooseBodyColour(entity.getUuid()));
        entity.setSpawnPos(pos);
        entity.setNaturalSpawn(true);
        DataHelper.randomiseEntityRotation(entity);
        world.spawnEntity(entity);
        world.spawnEntity(entity);

    }


    public static void register() {}

}
