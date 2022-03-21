package net.arbee.addola;

import net.arbee.addola.server.command.PlayerstatCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Addola implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_NAME = "Addola";

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

	@Override
    public void onInitialize() {
        Gamerules.setupGamerules();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            PlayerstatCommand.register(dispatcher);
        });
    }
}