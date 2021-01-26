package net.arbee.addola.screens.backpack;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.arbee.addola.guis.backpack.IronBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class IronBackpackScreen extends CottonInventoryScreen<IronBackpackGui> {
    public IronBackpackScreen(IronBackpackGui gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}