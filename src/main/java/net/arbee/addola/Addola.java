package net.arbee.addola;

import net.arbee.addola.registries.*;
import net.fabricmc.api.ModInitializer;

public class Addola implements ModInitializer {
    public static final String MOD_ID = "addola";
    @Override
    public void onInitialize() {
        AddolaGamerules.setupGamerules();
        AddolaCommands.setupCommands();
        AddolaEntities.setupEntities();
        AddolaItems.setupItems();
    }
}