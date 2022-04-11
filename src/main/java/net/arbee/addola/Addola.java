package net.arbee.addola;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.registries.AddolaEntities;
import net.arbee.addola.registries.AddolaItems;
import net.arbee.addola.registries.AddolaCommands;
import net.arbee.addola.registries.AddolaGamerules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Addola implements ModInitializer {
    public static final String MOD_NAME = "Addola";

    @Override
    public void onInitialize() {
        AddolaGamerules.setupGamerules();
        AddolaCommands.setupCommands();
        AddolaItems.setupItems();
        AddolaEntities.setupEntities();
    }
}