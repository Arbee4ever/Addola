package net.arbee.addola.registries;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AddolaEntities {
    public static final EntityType<ChestBoatEntity> CHESTBOAT = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("addola", "oak_chestboat"),
            FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).build()
    );

    public static void setupEntities() {

    }
}
