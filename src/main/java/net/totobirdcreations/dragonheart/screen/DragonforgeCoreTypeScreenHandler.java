package net.totobirdcreations.dragonheart.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.totobirdcreations.dragonheart.DragonHeart;
import net.totobirdcreations.dragonheart.screen.util.OutputSlot;

import static net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreTypeBlockEntity.DELEGATE_PROGRESS;
import static net.totobirdcreations.dragonheart.block.entity.DragonforgeCoreTypeBlockEntity.DELEGATE_MAXPROGRESS;



public class DragonforgeCoreTypeScreenHandler extends ScreenHandler {


    public enum ForgeType {
        FIRE,
        ICE,
        LIGHTNING
    }


    public Inventory        inventory;
    public PlayerInventory  playerInventory;
    public World            world;
    public PropertyDelegate propertyDelegate;


    public DragonforgeCoreTypeScreenHandler(int syncId, PlayerInventory playerInventory) {

        this(syncId, playerInventory, new SimpleInventory(3));

    }


    public DragonforgeCoreTypeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {

        this(ModScreens.DRAGONFORGE_CORE_FIRE, syncId, playerInventory, inventory, new ArrayPropertyDelegate(2));

    }


    public DragonforgeCoreTypeScreenHandler(ScreenHandlerType handlerType, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {

        super(handlerType, syncId);
        initialize(playerInventory, inventory, propertyDelegate);

    }


    public void initialize(PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {

        this.inventory        = inventory;
        this.playerInventory  = playerInventory;
        this.world            = playerInventory.player.world;
        this.propertyDelegate = propertyDelegate;
        checkSize(inventory, 3);
        inventory.onOpen(playerInventory.player);

        addSlot(new Slot(inventory, 0, 68, 51));
        addSlot(new Slot(inventory, 1, 92, 51));
        addSlot(new OutputSlot(inventory, 2, 80, 17));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);

    }


    public ForgeType getForgeType() {

        return ForgeType.FIRE;

    }


    public Identifier getScreenTexture() {

        ForgeType forgeType = getForgeType();
        if (forgeType == ForgeType.FIRE) {
            return new Identifier(DragonHeart.MOD_ID, "textures/gui/dragonforge_core_fire.png");
        } else if (forgeType == ForgeType.ICE) {
            return new Identifier(DragonHeart.MOD_ID, "textures/gui/dragonforge_core_ice.png");
        } else {
            return new Identifier(DragonHeart.MOD_ID, "textures/gui/dragonforge_core_lightning.png");
        }

    }


    public boolean isCrafting() {

        return propertyDelegate.get(DELEGATE_PROGRESS) > 0;

    }


    public int getScaledProgress() {

        int progress    = this.propertyDelegate.get(DELEGATE_PROGRESS);
        int maxProgress = this.propertyDelegate.get(DELEGATE_MAXPROGRESS);
        int textureSize = 14;

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
        if (slot != null && slot.hasStack()) {
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
