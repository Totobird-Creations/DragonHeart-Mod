package net.totobirdcreations.dragonheart.item.dragon.tool;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import static net.totobirdcreations.dragonheart.item.Items.registerItem;


public class DragonToolItems {

    public static final Item UNINFUSED_DRAGON_BONE_AXE = registerItem(
            "uninfused_dragon_bone_axe",
            new DragonAxeToolItem(
                    DragonToolItemMaterials.UNINFUSED_DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    false
            )
    );

    public static final Item UNINFUSED_DRAGON_BONE_HOE = registerItem(
            "uninfused_dragon_bone_hoe",
            new DragonHoeToolItem(
                    DragonToolItemMaterials.UNINFUSED_DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    false
            )
    );

    public static final Item UNINFUSED_DRAGON_BONE_PICKAXE = registerItem(
            "uninfused_dragon_bone_pickaxe",
            new DragonPickaxeToolItem(
                    DragonToolItemMaterials.UNINFUSED_DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    false
            )
    );

    public static final Item UNINFUSED_DRAGON_BONE_SHOVEL = registerItem(
            "uninfused_dragon_bone_shovel",
            new DragonShovelToolItem(
                    DragonToolItemMaterials.UNINFUSED_DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    false
            )
    );

    public static final Item UNINFUSED_DRAGON_BONE_SWORD = registerItem(
            "uninfused_dragon_bone_sword",
            new DragonSwordToolItem(
                    DragonToolItemMaterials.UNINFUSED_DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.COMBAT),
                    false
            )
    );


    public static final Item DRAGON_BONE_AXE = registerItem(
            "dragon_bone_axe",
            new DragonAxeToolItem(
                    DragonToolItemMaterials.DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_BONE_HOE = registerItem(
            "dragon_bone_hoe",
            new DragonHoeToolItem(
                    DragonToolItemMaterials.DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_BONE_PICKAXE = registerItem(
            "dragon_bone_pickaxe",
            new DragonPickaxeToolItem(
                    DragonToolItemMaterials.DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_BONE_SHOVEL = registerItem(
            "dragon_bone_shovel",
            new DragonShovelToolItem(
                    DragonToolItemMaterials.DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_BONE_SWORD = registerItem(
            "dragon_bone_sword",
            new DragonSwordToolItem(
                    DragonToolItemMaterials.DRAGON_BONE,
                    new FabricItemSettings()
                            .group(ItemGroup.COMBAT),
                    true
            )
    );


    public static final Item DRAGON_STEEL_AXE = registerItem(
            "dragon_steel_axe",
            new DragonAxeToolItem(
                    DragonToolItemMaterials.DRAGON_STEEL,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_STEEL_HOE = registerItem(
            "dragon_steel_hoe",
            new DragonHoeToolItem(
                    DragonToolItemMaterials.DRAGON_STEEL,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_STEEL_PICKAXE = registerItem(
            "dragon_steel_pickaxe",
            new DragonPickaxeToolItem(
                    DragonToolItemMaterials.DRAGON_STEEL,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_STEEL_SHOVEL = registerItem(
            "dragon_steel_shovel",
            new DragonShovelToolItem(
                    DragonToolItemMaterials.DRAGON_STEEL,
                    new FabricItemSettings()
                            .group(ItemGroup.TOOLS),
                    true
            )
    );

    public static final Item DRAGON_STEEL_SWORD = registerItem(
            "dragon_steel_sword",
            new DragonSwordToolItem(
                    DragonToolItemMaterials.DRAGON_STEEL,
                    new FabricItemSettings()
                            .group(ItemGroup.COMBAT),
                    true
            )
    );


    public static void register() {}

}
