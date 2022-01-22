package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.material.ArmourMaterial;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;



public class ArmourItems {

    public static final Item HELMET_DRAGONBONE = registerItem(
            "helmet_dragonbone",
            new ArmorItem(
                    ArmourMaterial.PREFORGED,
                    EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item CHESTPLATE_DRAGONBONE = registerItem(
            "chestplate_dragonbone",
            new ArmorItem(
                    ArmourMaterial.PREFORGED,
                    EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item LEGGINGS_DRAGONBONE = registerItem(
            "leggings_dragonbone",
            new ArmorItem(
                    ArmourMaterial.PREFORGED,
                    EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item BOOTS_DRAGONBONE = registerItem(
            "boots_dragonbone",
            new ArmorItem(
                    ArmourMaterial.PREFORGED,
                    EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static final Item HELMET_DRAGONBONE_FIRE = registerItem(
            "helmet_dragonbone_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_FIRE,
                    EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item CHESTPLATE_DRAGONBONE_FIRE = registerItem(
            "chestplate_dragonbone_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_FIRE,
                    EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item LEGGINGS_DRAGONBONE_FIRE = registerItem(
            "leggings_dragonbone_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_FIRE,
                    EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item BOOTS_DRAGONBONE_FIRE = registerItem(
            "boots_dragonbone_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_FIRE,
                    EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static final Item HELMET_DRAGONBONE_ICE = registerItem(
            "helmet_dragonbone_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_ICE,
                    EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item CHESTPLATE_DRAGONBONE_ICE = registerItem(
            "chestplate_dragonbone_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_ICE,
                    EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item LEGGINGS_DRAGONBONE_ICE = registerItem(
            "leggings_dragonbone_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_ICE,
                    EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item BOOTS_DRAGONBONE_ICE = registerItem(
            "boots_dragonbone_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_ICE,
                    EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static final Item HELMET_DRAGONBONE_LIGHTNING = registerItem(
            "helmet_dragonbone_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_LIGHTNING,
                    EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item CHESTPLATE_DRAGONBONE_LIGHTNING = registerItem(
            "chestplate_dragonbone_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_LIGHTNING,
                    EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item LEGGINGS_DRAGONBONE_LIGHTNING = registerItem(
            "leggings_dragonbone_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_LIGHTNING,
                    EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item BOOTS_DRAGONBONE_LIGHTNING = registerItem(
            "boots_dragonbone_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONBONE_LIGHTNING,
                    EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static final Item HELMET_DRAGONSTEEL_FIRE = registerItem(
            "helmet_dragonsteel_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_FIRE,
                    EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item CHESTPLATE_DRAGONSTEEL_FIRE = registerItem(
            "chestplate_dragonsteel_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_FIRE,
                    EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item LEGGINGS_DRAGONSTEEL_FIRE = registerItem(
            "leggings_dragonsteel_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_FIRE,
                    EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item BOOTS_DRAGONSTEEL_FIRE = registerItem(
            "boots_dragonsteel_fire",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_FIRE,
                    EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static final Item HELMET_DRAGONSTEEL_ICE = registerItem(
            "helmet_dragonsteel_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_ICE,
                    EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item CHESTPLATE_DRAGONSTEEL_ICE = registerItem(
            "chestplate_dragonsteel_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_ICE,
                    EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item LEGGINGS_DRAGONSTEEL_ICE = registerItem(
            "leggings_dragonsteel_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_ICE,
                    EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item BOOTS_DRAGONSTEEL_ICE = registerItem(
            "boots_dragonsteel_ice",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_ICE,
                    EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static final Item HELMET_DRAGONSTEEL_LIGHTNING = registerItem(
            "helmet_dragonsteel_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_LIGHTNING,
                    EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item CHESTPLATE_DRAGONSTEEL_LIGHTNING = registerItem(
            "chestplate_dragonsteel_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_LIGHTNING,
                    EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item LEGGINGS_DRAGONSTEEL_LIGHTNING = registerItem(
            "leggings_dragonsteel_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_LIGHTNING,
                    EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );

    public static final Item BOOTS_DRAGONSTEEL_LIGHTNING = registerItem(
            "boots_dragonsteel_lightning",
            new ArmorItem(
                    ArmourMaterial.DRAGONSTEEL_LIGHTNING,
                    EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static void register() {

    }


}
