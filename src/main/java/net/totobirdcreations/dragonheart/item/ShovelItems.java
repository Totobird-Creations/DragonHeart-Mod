package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.material.ShovelMaterial;
import net.totobirdcreations.dragonheart.item.shovel.*;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;


public class ShovelItems {


    public static final Item SHOVEL_DRAGONBONE = registerItem(
            "shovel_dragonbone",
            new Shovel(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    ShovelMaterial.PREFORGED
            )
    );

    public static final Item SHOVEL_DRAGONBONE_FIRE = registerItem(
            "shovel_dragonbone_fire",
            new ShovelFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    ShovelMaterial.DRAGONBONE
            )
    );

    public static final Item SHOVEL_DRAGONBONE_ICE = registerItem(
            "shovel_dragonbone_ice",
            new ShovelIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    ShovelMaterial.DRAGONBONE
            )
    );

    public static final Item SHOVEL_DRAGONBONE_LIGHTNING = registerItem(
            "shovel_dragonbone_lightning",
            new ShovelLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    ShovelMaterial.DRAGONBONE
            )
    );


    public static final Item SHOVEL_DRAGONSTEEL_FIRE = registerItem(
            "shovel_dragonsteel_fire",
            new ShovelFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    ShovelMaterial.DRAGONSTEEL
            )
    );

    public static final Item SHOVEL_DRAGONSTEEL_ICE = registerItem(
            "shovel_dragonsteel_ice",
            new ShovelIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    ShovelMaterial.DRAGONSTEEL
            )
    );

    public static final Item SHOVEL_DRAGONSTEEL_LIGHTNING = registerItem(
            "shovel_dragonsteel_lightning",
            new ShovelLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    ShovelMaterial.DRAGONSTEEL
            )
    );


    public static void register() {



    }


}
