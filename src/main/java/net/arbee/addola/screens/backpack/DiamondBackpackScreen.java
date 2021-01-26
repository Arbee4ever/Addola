package net.arbee.addola.screens.backpack;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.arbee.addola.guis.backpack.DiamondBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class DiamondBackpackScreen extends CottonInventoryScreen<DiamondBackpackGui> {
    public DiamondBackpackScreen(DiamondBackpackGui gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}