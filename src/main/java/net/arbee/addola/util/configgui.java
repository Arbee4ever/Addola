package net.arbee.addola.util;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.arbee.addola.ReferenceClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class configgui extends LightweightGuiDescription {

	public configgui(Screen previous) {
		WGridPanel General = new WGridPanel();
		
		WTabPanel tabs = new WTabPanel();
		tabs.add(General, tab -> tab.icon(new TextureIcon(new Identifier("addola:icon.png"))).tooltip(new TranslatableText("options.addola.general")));
		setRootPanel(tabs).setFullscreen(true);
		tabs.setLocation(10, 10);
		WToggleButton settingsButton = new WToggleButton(new TranslatableText("option.addola.settingsbutton")) {
			@Override
			public void onToggle(boolean on) {
				ReferenceClient.config.settingsButtonOn = on;
				ReferenceClient.saveConfig(ReferenceClient.config);
			}
		};
		WToggleButton villagerFollow = new WToggleButton(new TranslatableText("option.addola.villagersfollow")) {
			@Override
			public void onToggle(boolean on) {
				ReferenceClient.config.villagersFollow = on;
				ReferenceClient.saveConfig(ReferenceClient.config);
			}
		};
		WToggleButton sneakBerryBush = new WToggleButton(new TranslatableText("option.addola.sneakberrybush")) {
			@Override
			public void onToggle(boolean on) {
				ReferenceClient.config.sneakBerryBush = on;
				ReferenceClient.saveConfig(ReferenceClient.config);
			}
		};
		villagerFollow.setToggle(ReferenceClient.config.villagersFollow);
		settingsButton.setToggle(ReferenceClient.config.settingsButtonOn);
		sneakBerryBush.setToggle(ReferenceClient.config.sneakBerryBush);
		General.add(settingsButton, 0, 2, 20, 1);
		General.add(villagerFollow, 0, 3, 20, 1);
		General.add(sneakBerryBush, 0, 4, 20, 1);
		
		WButton doneButton = new WButton(new TranslatableText("gui.done"));
		doneButton.setOnClick(()-> MinecraftClient.getInstance().openScreen(previous));
		General.add(doneButton, 0, 6, 3, 1);
		
		General.setBackgroundPainter(BackgroundPainter.SLOT);
		
		tabs.validate(this);
	}
}
