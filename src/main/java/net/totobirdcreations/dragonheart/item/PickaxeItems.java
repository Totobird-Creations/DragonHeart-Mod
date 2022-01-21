package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.pickaxe.DragonbonePickaxeMaterial;
import net.totobirdcreations.dragonheart.item.pickaxe.DragonsteelPickaxeMaterial;
import net.totobirdcreations.dragonheart.item.pickaxe.*;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;

public class PickaxeItems {

    public static final Item DRAGONBONE_PICKAXE = registerItem(
            "dragonbone_pickaxe",
            new DragonPickaxe(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    DragonbonePickaxeMaterial.INSTANCE
            )
    );

    public static final Item DRAGONBONE_PICKAXE_FIRE = registerItem(
            "dragonbone_pickaxe_fire",
            new DragonPickaxeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    DragonbonePickaxeMaterial.INSTANCE
            )
    );

    public static final Item DRAGONBONE_PICKAXE_ICE = registerItem(
            "dragonbone_pickaxe_ice",
            new DragonPickaxeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    DragonbonePickaxeMaterial.INSTANCE
            )
    );

    public static final Item DRAGONBONE_PICKAXE_LIGHTNING = registerItem(
            "dragonbone_pickaxe_lightning",
            new DragonPickaxeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    DragonbonePickaxeMaterial.INSTANCE
            )
    );

    public static final Item DRAGONSTEEL_PICKAXE_FIRE = registerItem(
            "dragonsteel_pickaxe_fire",
            new DragonPickaxeFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    DragonsteelPickaxeMaterial.INSTANCE
            )
    );

    public static final Item DRAGONSTEEL_PICKAXE_ICE = registerItem(
            "dragonsteel_pickaxe_ice",
            new DragonPickaxeIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    DragonsteelPickaxeMaterial.INSTANCE
            )
    );

    public static final Item DRAGONSTEEL_PICKAXE_LIGHTNING = registerItem(
            "dragonsteel_pickaxe_lightning",
            new DragonPickaxeLightning(
                    new FabricItemSettings()
                            .group     (ItemGroup.TOOLS)
                            .fireproof (),
                    DragonsteelPickaxeMaterial.INSTANCE
            )
    );

    public static void register() {}

}
