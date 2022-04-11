package net.arbee.addola;

import net.arbee.addola.registries.AddolaCommands;
import net.arbee.addola.registries.AddolaEntities;
import net.arbee.addola.registries.AddolaGamerules;
import net.arbee.addola.registries.AddolaItems;
import net.fabricmc.api.ModInitializer;

public class Addola implements ModInitializer {
    public static final String MOD_NAME = "Addola";

    @Override
    public void onInitialize() {
        AddolaGamerules.setupGamerules();
        AddolaCommands.setupCommands();
        AddolaEntities.setupEntities();
        AddolaItems.setupItems();
    }
}