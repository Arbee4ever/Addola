package net.arbee.addola.util.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.Environment;
import net.arbee.addola.Reference;
import net.arbee.addola.screens.configscreen;
import net.arbee.addola.util.configgui;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ModMenuSupport implements ModMenuApi {
	@Override
	public String getModId() {
		return Reference.MOD_ID;
	}
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> new configscreen(new configgui(null));
	}
}
