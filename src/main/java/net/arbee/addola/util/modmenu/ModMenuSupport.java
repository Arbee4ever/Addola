package net.arbee.addola.util.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.Environment;
import net.arbee.addola.screens.configscreen;
import net.arbee.addola.util.configgui;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ModMenuSupport implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> new configscreen(new configgui(null));
	}
}
