package net.arbee.addola.registries;

import net.arbee.addola.server.command.PlayerstatCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class AddolaCommands {
    public static void setupCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            PlayerstatCommand.register(dispatcher);
        });
    }
}
