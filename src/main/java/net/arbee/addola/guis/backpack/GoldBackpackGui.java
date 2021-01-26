package net.arbee.addola.guis.backpack;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.arbee.addola.Reference;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;

public class GoldBackpackGui extends SyncedGuiDescription {
    public static final int INVENTORY_SIZE = 18;

    public GoldBackpackGui(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(Reference.SCREEN_HANDLER_TYPE_GOLD, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        WItemSlot w = WItemSlot.of(blockInventory, 0, 9, INVENTORY_SIZE/9);
        root.add(w, 0, 1);

        root.add(this.createPlayerInventoryPanel(), 0, (INVENTORY_SIZE/9)+2);

        root.validate(this);
    }
}
