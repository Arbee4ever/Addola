package net.arbee.addola;

import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import io.github.cottonmc.jankson.JanksonFactory;
import net.arbee.addola.util.AddolaConfig;
import net.arbee.addola.Reference;
import net.arbee.addola.guis.backpack.DiamondBackpackGui;
import net.arbee.addola.guis.backpack.GoldBackpackGui;
import net.arbee.addola.guis.backpack.IronBackpackGui;
import net.arbee.addola.guis.backpack.NetheriteBackpackGui;
import net.arbee.addola.screens.backpack.DiamondBackpackScreen;
import net.arbee.addola.screens.backpack.GoldBackpackScreen;
import net.arbee.addola.screens.backpack.IronBackpackScreen;
import net.arbee.addola.screens.backpack.NetheriteBackpackScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class ReferenceClient implements ClientModInitializer {
	public static final Logger logger = LogManager.getLogger();
	public static volatile AddolaConfig config;
	
	public static final Jankson jankson = JanksonFactory.createJankson();
	
	@Override
    public void onInitializeClient() {
		ColorProviderRegistry.BLOCK.register((state,  view, pos, tintIndex) -> 8606770, Reference.BACKPACK_IRON, Reference.BACKPACK_GOLD, Reference.BACKPACK_DIAMOND, Reference.BACKPACK_NETHERITE);
		ColorProviderRegistry.ITEM.register((stack,  tintIndex) -> 8606770, Reference.BACKPACK_ITEM_IRON, Reference.BACKPACK_ITEM_GOLD, Reference.BACKPACK_ITEM_DIAMOND, Reference.BACKPACK_ITEM_NETHERITE);
		config = loadConfig();
		ScreenRegistry.<IronBackpackGui, IronBackpackScreen>register(Reference.SCREEN_HANDLER_TYPE_IRON, (gui, inventory, title) -> new IronBackpackScreen(gui, inventory.player, title));
		ScreenRegistry.<GoldBackpackGui, GoldBackpackScreen>register(Reference.SCREEN_HANDLER_TYPE_GOLD, (gui, inventory, title) -> new GoldBackpackScreen(gui, inventory.player, title));
		ScreenRegistry.<DiamondBackpackGui, DiamondBackpackScreen>register(Reference.SCREEN_HANDLER_TYPE_DIAMOND, (gui, inventory, title) -> new DiamondBackpackScreen(gui, inventory.player, title));
		ScreenRegistry.<NetheriteBackpackGui, NetheriteBackpackScreen>register(Reference.SCREEN_HANDLER_TYPE_NETHERITE, (gui, inventory, title) -> new NetheriteBackpackScreen(gui, inventory.player, title));
	}
	
	@SuppressWarnings("deprecation")
	public static AddolaConfig loadConfig() {
		try {
			File file = new File(FabricLoader.getInstance().getConfigDirectory(),"addola.json5");
			
			if (!file.exists()) saveConfig(new AddolaConfig());
			
			JsonObject json = jankson.load(file);
			config =  jankson.fromJson(json, AddolaConfig.class);
		} catch (Exception e) {
			logger.error("Error loading config: {}", e.getMessage());
		}
		return config;
	}

	@SuppressWarnings("deprecation")
	public static void saveConfig(AddolaConfig config) {
		try {
			File file = new File(FabricLoader.getInstance().getConfigDirectory(),"addola.json5");
			
			JsonElement json = jankson.toJson(config);
			String result = json.toJson(true, true);
			try (FileOutputStream out = new FileOutputStream(file, false)) {
				out.write(result.getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception e) {
			logger.error("Error saving config: {}", e.getMessage());
		}
	}
}
