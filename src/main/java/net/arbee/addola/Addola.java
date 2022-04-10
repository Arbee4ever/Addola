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
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Addola implements ModInitializer {
    public static final String MOD_NAME = "Addola";

    public static final ChestBoatItem OAK_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.OAK, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem JUNGLE_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.JUNGLE, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem ACACIA_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.ACACIA, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem DARKOAK_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.DARK_OAK, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem BIRCH_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.BIRCH, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem SPRUCE_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.SPRUCE, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));

    public static final EntityType<ChestBoatEntity> CHESTBOAT = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("addola", "oak_chestboat"),
            FabricEntityTypeBuilder.<ChestBoatEntity>create(SpawnGroup.MISC, ChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375f, 0.5625f)).build()
    );

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("addola", "oak_chestboat"), OAK_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier("addola", "jungle_chestboat"), JUNGLE_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier("addola", "acacia_chestboat"), ACACIA_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier("addola", "dark_oak_chestboat"), DARKOAK_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier("addola", "birch_chestboat"), BIRCH_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier("addola", "spruce_chestboat"), SPRUCE_CHESTBOAT_ITEM);
        Gamerules.setupGamerules();
        Commands.setupCommands();
    }
}