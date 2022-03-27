package net.arbee.addola;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.registries.Commands;
import net.arbee.addola.registries.Gamerules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Addola implements ModInitializer {
    public static final String MOD_NAME = "Addola";

    public static final EntityType<ChestBoatEntity> CHESTBOAT = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("addola", "chestboat"),
            FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    @Override
    public void onInitialize() {
        Gamerules.setupGamerules();
        Commands.setupCommands();
    }
}