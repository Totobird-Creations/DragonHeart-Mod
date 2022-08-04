package net.totobirdcreations.dragonheart.item.dragon;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totobirdcreations.dragonheart.item.dragon.tool.DragonToolItems;
import net.totobirdcreations.dragonheart.item.group.ItemGroups;

import static net.totobirdcreations.dragonheart.item.Items.registerItem;


public class DragonItems {


    public static final Item DRAGON_BONE = registerItem(
            "dragon_bone",
            new Item(
                new FabricItemSettings()
                        .group     (ItemGroup.MISC)
                        .maxCount  (16)
                        .fireproof ()
            )
    );

    public static final Item DRAGON_SCALE = registerItem(
            "dragon_scale",
            new DragonScaleItem(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (48)
                            .fireproof ()
            )
    );

    public static final Item DRAGON_BUCKET = registerItem(
            "dragon_bucket",
            new Item(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (1)
                            .fireproof ()
            )
    );

    public static final Item DRAGON_EGG = registerItem(
            "dragon_egg",
            new DragonEggItem(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (1)
                            .fireproof ()
            )
    );
    public static final Item DRAGON_EGG_CREATIVE = registerItem(
            "dragon_egg_creative",
            new DragonEggCreativeItem(
                    new FabricItemSettings()
                            .group     (ItemGroups.DRAGON)
                            .maxCount  (1)
                            .fireproof ()
            )
    );


    @SuppressWarnings("unused")
    public static final Item DRAGON_BLOOD = registerItem(
            "dragon_blood",
            new DragonBloodItem(
                    new FabricItemSettings()
                            .group(ItemGroups.DRAGON)
            )
    );


    public static final Item DRAGON_BREATH = registerItem(
            "dragon_breath",
            new DragonBreathItem(
                    new FabricItemSettings()
                            .group(ItemGroups.DRAGON)
            )
    );



    public static void register() {
        DragonToolItems.register();
    }


}
