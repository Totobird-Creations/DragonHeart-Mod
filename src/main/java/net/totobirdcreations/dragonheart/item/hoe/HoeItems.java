package net.totobirdcreations.dragonheart.item.hoe;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.hoe.*;
import net.totobirdcreations.dragonheart.item.material.HoeMaterial;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;


public class HoeItems {


    @SuppressWarnings("unused")
    public static final Item HOE_DRAGONBONE = registerItem(
            "hoe_dragonbone",
            new Hoe(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    HoeMaterial.PREFORGED
            )
    );

    @SuppressWarnings("unused")
    public static final Item HOE_DRAGONBONE_FIRE = registerItem(
            "hoe_dragonbone_fire",
            new HoeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    HoeMaterial.DRAGONBONE
            )
    );

    @SuppressWarnings("unused")
    public static final Item HOE_DRAGONBONE_ICE = registerItem(
            "hoe_dragonbone_ice",
            new HoeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    HoeMaterial.DRAGONBONE
            )
    );

    @SuppressWarnings("unused")
    public static final Item HOE_DRAGONBONE_LIGHTNING = registerItem(
            "hoe_dragonbone_lightning",
            new HoeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    HoeMaterial.DRAGONBONE
            )
    );


    @SuppressWarnings("unused")
    public static final Item HOE_DRAGONSTEEL_FIRE = registerItem(
            "hoe_dragonsteel_fire",
            new HoeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    HoeMaterial.DRAGONSTEEL
            )
    );

    @SuppressWarnings("unused")
    public static final Item HOE_DRAGONSTEEL_ICE = registerItem(
            "hoe_dragonsteel_ice",
            new HoeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    HoeMaterial.DRAGONSTEEL
            )
    );

    @SuppressWarnings("unused")
    public static final Item HOE_DRAGONSTEEL_LIGHTNING = registerItem(
            "hoe_dragonsteel_lightning",
            new HoeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    HoeMaterial.DRAGONSTEEL
            )
    );


    public static void register() {}


}
