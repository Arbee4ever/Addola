package net.arbee.addola;

import net.arbee.addola.entity.vehicle.ChestBoatEntity;
import net.arbee.addola.item.ChestBoatItem;
import net.arbee.addola.registries.Commands;
import net.arbee.addola.registries.Gamerules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Addola implements ModInitializer {
    public static final String MOD_NAME = "Addola";

    public static final ChestBoatItem CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.OAK, new FabricItemSettings().group(ItemGroup.TRANSPORTATION));

    public static final EntityType<ChestBoatEntity> CHESTBOAT = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("addola", "chestboat"),
            FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).build()
    );

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("addola", "chestboat"), CHESTBOAT_ITEM);
        Gamerules.setupGamerules();
        Commands.setupCommands();
    }
}