package net.totobirdcreations.dragonheart.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;



public class DragonforgeCoreFireScreenHandler extends DragonforgeCoreTypeScreenHandler {


    public DragonforgeCoreFireScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(2));
    }

    public DragonforgeCoreFireScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreens.DRAGONFORGE_CORE_FIRE, syncId, playerInventory, inventory, propertyDelegate);
    }


    @Override
    public ForgeType getForgeType() {

        return ForgeType.FIRE;

    }


}
