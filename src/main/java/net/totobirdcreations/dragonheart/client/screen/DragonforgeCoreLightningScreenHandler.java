package net.totobirdcreations.dragonheart.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;



public class DragonforgeCoreLightningScreenHandler extends DragonforgeCoreTypeScreenHandler {


    public DragonforgeCoreLightningScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(2));
    }

    public DragonforgeCoreLightningScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ClientScreens.DRAGONFORGE_CORE_LIGHTNING, syncId, playerInventory, inventory, propertyDelegate);
    }


    @Override
    public ForgeType getForgeType() {
        return ForgeType.LIGHTNING;
    }


}
