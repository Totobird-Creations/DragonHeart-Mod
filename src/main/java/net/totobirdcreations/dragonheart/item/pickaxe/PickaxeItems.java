package net.totobirdcreations.dragonheart.item.pickaxe;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.material.PickaxeMaterial;
import net.totobirdcreations.dragonheart.item.pickaxe.*;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;


public class PickaxeItems {


    @SuppressWarnings("unused")
    public static final Item PICKAXE_DRAGONBONE = registerItem(
            "pickaxe_dragonbone",
            new Pickaxe(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    PickaxeMaterial.PREFORGED
            )
    );

    @SuppressWarnings("unused")
    public static final Item PICKAXE_DRAGONBONE_FIRE = registerItem(
            "pickaxe_dragonbone_fire",
            new PickaxeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    PickaxeMaterial.DRAGONBONE
            )
    );

    @SuppressWarnings("unused")
    public static final Item PICKAXE_DRAGONBONE_ICE = registerItem(
            "pickaxe_dragonbone_ice",
            new PickaxeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    PickaxeMaterial.DRAGONBONE
            )
    );

    @SuppressWarnings("unused")
    public static final Item PICKAXE_DRAGONBONE_LIGHTNING = registerItem(
            "pickaxe_dragonbone_lightning",
            new PickaxeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    PickaxeMaterial.DRAGONBONE
            )
    );


    @SuppressWarnings("unused")
    public static final Item PICKAXE_DRAGONSTEEL_FIRE = registerItem(
            "pickaxe_dragonsteel_fire",
            new PickaxeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    PickaxeMaterial.DRAGONSTEEL
            )
    );

    @SuppressWarnings("unused")
    public static final Item PICKAXE_DRAGONSTEEL_ICE = registerItem(
            "pickaxe_dragonsteel_ice",
            new PickaxeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    PickaxeMaterial.DRAGONSTEEL
            )
    );

    @SuppressWarnings("unused")
    public static final Item PICKAXE_DRAGONSTEEL_LIGHTNING = registerItem(
            "pickaxe_dragonsteel_lightning",
            new PickaxeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    PickaxeMaterial.DRAGONSTEEL
            )
    );


    public static void register() {}


}
