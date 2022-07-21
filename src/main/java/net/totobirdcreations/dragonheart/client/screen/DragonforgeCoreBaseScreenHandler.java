package net.totobirdcreations.dragonheart.client.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreBaseBlockEntity.*;



public class DragonforgeCoreBaseScreenHandler extends ScreenHandler {

    public final Inventory        inventory;
    public final World            world;
    public final PropertyDelegate propertyDelegate;


    public DragonforgeCoreBaseScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(3));
    }


    public DragonforgeCoreBaseScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ClientScreens.DRAGONFORGE_CORE_BASE, syncId);
        checkSize(inventory, 1);
        this.inventory        = inventory;
        this.world            = playerInventory.player.world;
        this.propertyDelegate = propertyDelegate;
        inventory.onOpen(playerInventory.player);

        addSlot(new Slot(inventory, 0, 80, 36));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }


    public boolean isConverting() {
        return propertyDelegate.get(DELEGATE_PROGRESS) > 0 && getConversionMode() != CONVERSIONMODE_NONE;
    }


    public int getConversionMode() {
        return propertyDelegate.get(DELEGATE_CONVERSIONMODE);
    }


    public int getScaledProgress() {
        int progress    = propertyDelegate.get(DELEGATE_PROGRESS);
        int maxProgress = propertyDelegate.get(DELEGATE_MAXPROGRESS);
        int textureSize = 40;
        if (maxProgress != 0 && progress != 0) {
            return progress * textureSize / maxProgress;
        } else {
            return 0;
        }
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }


    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }


    public void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }


    public void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }


}
