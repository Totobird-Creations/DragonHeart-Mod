package net.totobirdcreations.dragonheart.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.staff.StaffFire;
import net.totobirdcreations.dragonheart.item.staff.StaffIce;

import static net.totobirdcreations.dragonheart.item.ModItems.registerItem;

public class StaffItems {


    public static final Item STAFF_FIRE = registerItem(
            "staff_fire",
            new StaffFire(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static final Item STAFF_ICE = registerItem(
            "staff_ice",
            new StaffIce(
                    new FabricItemSettings()
                            .group     (ItemGroup.COMBAT)
                            .fireproof ()
            )
    );


    public static void register() {}

}
