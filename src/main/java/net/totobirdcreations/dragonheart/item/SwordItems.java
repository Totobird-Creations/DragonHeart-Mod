package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.material.SwordMaterial;
import net.totobirdcreations.dragonheart.item.sword.*;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;



public class SwordItems {


    public static final Item SWORD_DRAGONBONE = registerItem(
            "sword_dragonbone",
            new Sword(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    SwordMaterial.DRAGONBONE
            )
    );

    public static final Item SWORD_DRAGONBONE_FIRE = registerItem(
            "sword_dragonbone_fire",
            new SwordFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    SwordMaterial.DRAGONBONE
            )
    );

    public static final Item SWORD_DRAGONBONE_ICE = registerItem(
            "sword_dragonbone_ice",
            new SwordIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    SwordMaterial.DRAGONBONE
            )
    );

    public static final Item SWORD_DRAGONBONE_LIGHTNING = registerItem(
            "sword_dragonbone_lightning",
            new SwordLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    SwordMaterial.DRAGONBONE
            )
    );


    public static final Item SWORD_DRAGONSTEEL_FIRE = registerItem(
            "sword_dragonsteel_fire",
            new SwordFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    SwordMaterial.DRAGONSTEEL
            )
    );

    public static final Item SWORD_DRAGONSTEEL_ICE = registerItem(
            "sword_dragonsteel_ice",
            new SwordIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    SwordMaterial.DRAGONSTEEL
            )
    );

    public static final Item SWORD_DRAGONSTEEL_LIGHTNING = registerItem(
            "sword_dragonsteel_lightning",
            new SwordLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof (),
                    SwordMaterial.DRAGONSTEEL
            )
    );


    public static void register() {



    }


}
