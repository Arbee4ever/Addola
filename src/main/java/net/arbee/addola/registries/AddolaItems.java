package net.arbee.addola.registries;

import net.arbee.addola.Addola;
import net.arbee.addola.client.render.BoatItemRenderer;
import net.arbee.addola.client.render.ChestBoatItemRenderer;
import net.arbee.addola.item.ChestBoatItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AddolaItems {
    public static final ChestBoatItem OAK_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.OAK, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem JUNGLE_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.JUNGLE, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem ACACIA_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.ACACIA, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem DARKOAK_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.DARK_OAK, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem BIRCH_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.BIRCH, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final ChestBoatItem SPRUCE_CHESTBOAT_ITEM = new ChestBoatItem(BoatEntity.Type.SPRUCE, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).maxCount(1));

    public static void setupItems() {
        Registry.register(Registry.ITEM, new Identifier(Addola.MOD_ID, "oak_chestboat"), OAK_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Addola.MOD_ID, "jungle_chestboat"), JUNGLE_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Addola.MOD_ID, "acacia_chestboat"), ACACIA_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Addola.MOD_ID, "dark_oak_chestboat"), DARKOAK_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Addola.MOD_ID, "birch_chestboat"), BIRCH_CHESTBOAT_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Addola.MOD_ID, "spruce_chestboat"), SPRUCE_CHESTBOAT_ITEM);
    }

    public static void setupItemRenderers() {
        BuiltinItemRendererRegistry.INSTANCE.register(Items.ACACIA_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.OAK_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.BIRCH_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.DARK_OAK_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.JUNGLE_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(Items.SPRUCE_BOAT, new BoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(OAK_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(SPRUCE_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(BIRCH_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(JUNGLE_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ACACIA_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(DARKOAK_CHESTBOAT_ITEM, new ChestBoatItemRenderer());
    }
}
