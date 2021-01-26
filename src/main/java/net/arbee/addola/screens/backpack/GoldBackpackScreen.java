package net.arbee.addola.screens.backpack;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.arbee.addola.guis.backpack.GoldBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class GoldBackpackScreen extends CottonInventoryScreen<GoldBackpackGui> {
    public GoldBackpackScreen(GoldBackpackGui gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}