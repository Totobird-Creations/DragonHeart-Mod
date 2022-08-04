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
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator.DragoneggIncubatorBlockEntity;
import net.totobirdcreations.dragonheart.block.entity.dragon.forge.egg_incubator.DragoneggIncubatorBlockEntityProperties;
import net.totobirdcreations.dragonheart.screenhandler.util.DragonBreathSlot;


public class DragonEggIncubatorScreenHandler extends ScreenHandler {

    public final Inventory        inventory;
    public final PropertyDelegate properties;


    public DragonEggIncubatorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(DragoneggIncubatorBlockEntity.INVENTORY_SIZE), new ArrayPropertyDelegate(DragoneggIncubatorBlockEntityProperties.SIZE));
    }

    public DragonEggIncubatorScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate properties) {
        super(ScreenHandlers.DRAGONEGG_INCUBATOR, syncId);
        checkSize(inventory, DragoneggIncubatorBlockEntity.INVENTORY_SIZE);

        this.inventory  = inventory;
        this.properties = properties;

        inventory.onOpen(playerInventory.player);

        this.addSlot(new DragonBreathSlot(this.inventory, 0, 80, 51));

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
