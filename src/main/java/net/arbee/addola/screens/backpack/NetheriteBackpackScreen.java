package net.arbee.addola.screens.backpack;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.arbee.addola.guis.backpack.NetheriteBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class NetheriteBackpackScreen extends CottonInventoryScreen<NetheriteBackpackGui> {
    public NetheriteBackpackScreen(NetheriteBackpackGui gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}