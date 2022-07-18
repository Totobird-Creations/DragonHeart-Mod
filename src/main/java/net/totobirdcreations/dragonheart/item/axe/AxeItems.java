package net.totobirdcreations.dragonheart.item.axe;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.axe.*;
import net.totobirdcreations.dragonheart.item.material.AxeMaterial;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;



public class AxeItems {


    @SuppressWarnings("unused")
    public static final Item AXE_DRAGONBONE = registerItem(
            "axe_dragonbone",
            new Axe(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    AxeMaterial.PREFORGED
            )
    );

    @SuppressWarnings("unused")
    public static final Item AXE_DRAGONBONE_FIRE = registerItem(
            "axe_dragonbone_fire",
            new AxeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    AxeMaterial.DRAGONBONE
            )
    );

    @SuppressWarnings("unused")
    public static final Item AXE_DRAGONBONE_ICE = registerItem(
            "axe_dragonbone_ice",
            new AxeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    AxeMaterial.DRAGONBONE
            )
    );

    @SuppressWarnings("unused")
    public static final Item AXE_DRAGONBONE_LIGHTNING = registerItem(
            "axe_dragonbone_lightning",
            new AxeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    AxeMaterial.DRAGONBONE
            )
    );


    @SuppressWarnings("unused")
    public static final Item AXE_DRAGONSTEEL_FIRE = registerItem(
            "axe_dragonsteel_fire",
            new AxeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    AxeMaterial.DRAGONSTEEL
            )
    );

    @SuppressWarnings("unused")
    public static final Item AXE_DRAGONSTEEL_ICE = registerItem(
            "axe_dragonsteel_ice",
            new AxeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    AxeMaterial.DRAGONSTEEL
            )
    );

    @SuppressWarnings("unused")
    public static final Item AXE_DRAGONSTEEL_LIGHTNING = registerItem(
            "axe_dragonsteel_lightning",
            new AxeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    AxeMaterial.DRAGONSTEEL
            )
    );


    public static void register() {}


}
