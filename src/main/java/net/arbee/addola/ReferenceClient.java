package net.arbee.addola;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import io.github.cottonmc.jankson.JanksonFactory;
import net.arbee.addola.util.AddolaConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Environment(EnvType.CLIENT)
public class ReferenceClient implements ClientModInitializer {
	public static final Logger logger = LogManager.getLogger();
	public static volatile AddolaConfig config;

	public static final Jankson jankson = JanksonFactory.createJankson();

	@Override
	public void onInitializeClient() {
		config = loadConfig();
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
