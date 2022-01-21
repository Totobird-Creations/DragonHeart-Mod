package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.sword.*;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;

public class SwordItems {

    public static final Item DRAGONBONE_SWORD = registerItem(
            "dragonbone_sword",
            new DragonSword(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    DragonboneSwordMaterial.INSTANCE
            )
    );

    public static final Item DRAGONBONE_SWORD_FIRE = registerItem(
            "dragonbone_sword_fire",
            new DragonSwordFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    DragonboneSwordMaterial.INSTANCE
            )
    );

    public static final Item DRAGONBONE_SWORD_ICE = registerItem(
            "dragonbone_sword_ice",
            new DragonSwordIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    DragonboneSwordMaterial.INSTANCE
            )
    );

    public static final Item DRAGONBONE_SWORD_LIGHTNING = registerItem(
            "dragonbone_sword_lightning",
            new DragonSwordLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    DragonboneSwordMaterial.INSTANCE
            )
    );

    public static final Item DRAGONSTEEL_SWORD_FIRE = registerItem(
            "dragonsteel_sword_fire",
            new DragonSwordFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    DragonsteelSwordMaterial.INSTANCE
            )
    );

    public static final Item DRAGONSTEEL_SWORD_ICE = registerItem(
            "dragonsteel_sword_ice",
            new DragonSwordIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    DragonsteelSwordMaterial.INSTANCE
            )
    );

    public static final Item DRAGONSTEEL_SWORD_LIGHTNING = registerItem(
            "dragonsteel_sword_lightning",
            new DragonSwordLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    DragonsteelSwordMaterial.INSTANCE
            )
    );

    public static void register() {}

}
