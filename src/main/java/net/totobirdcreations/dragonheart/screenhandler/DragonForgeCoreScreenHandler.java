package net.totobirdcreations.dragonheart.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge_core.DragonForgeCoreBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge_core.DragonForgeCoreBlockEntityProperties;
import net.totobirdcreations.dragonheart.screenhandler.util.DragonBreathSlot;
import net.totobirdcreations.dragonheart.screenhandler.util.FireChargeSlot;
import net.totobirdcreations.dragonheart.screenhandler.util.OutputSlot;


public class DragonForgeCoreScreenHandler extends ScreenHandler {

    public final Inventory        inventory;
    public final PropertyDelegate properties;


    public DragonForgeCoreScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ArrayPropertyDelegate(DragonForgeCoreBlockEntityProperties.SIZE));
    }

    public DragonForgeCoreScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate properties) {
        super(ScreenHandlers.DRAGON_FORGE_CORE, syncId);
        checkSize(inventory, DragonForgeCoreBlockEntity.INVENTORY_SIZE);

        this.inventory  = inventory;
        this.properties = properties;

        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot           (this.inventory, 0, 48  , 22));
        this.addSlot(new Slot           (this.inventory, 1, 48  , 55));
        this.addSlot(new OutputSlot     (this.inventory, 2, 112 , 22));
        this.addSlot(new FireChargeSlot (this.inventory, 3, 112 , 55));

        this.addPlayerInventory (playerInventory);
        this.addPlayerHotbar    (playerInventory);

        this.addProperties(this.properties);
    }



    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot      slot  = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            stack = originalStack.copy();
            if (index < this.inventory.size()) {
                if (! this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (! this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return stack;
    }


    public void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
            }
        }
    }

    public void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

}
