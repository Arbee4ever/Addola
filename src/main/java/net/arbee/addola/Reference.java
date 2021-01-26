package net.arbee.addola;

import net.arbee.addola.blockentities.backpack.DiamondBackpackEntity;
import net.arbee.addola.blockentities.backpack.GoldBackpackEntity;
import net.arbee.addola.blockentities.backpack.IronBackpackEntity;
import net.arbee.addola.blockentities.backpack.NetheriteBackpackEntity;
import net.arbee.addola.blocks.backpack.DiamondBackpackBlock;
import net.arbee.addola.blocks.backpack.GoldBackpackBlock;
import net.arbee.addola.blocks.backpack.IronBackpackBlock;
import net.arbee.addola.blocks.backpack.NetheriteBackpackBlock;
import net.arbee.addola.guis.backpack.DiamondBackpackGui;
import net.arbee.addola.guis.backpack.GoldBackpackGui;
import net.arbee.addola.guis.backpack.IronBackpackGui;
import net.arbee.addola.guis.backpack.NetheriteBackpackGui;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;
import top.theillusivec4.curios.api.SlotTypeInfo.BuildScheme;
import top.theillusivec4.curios.api.SlotTypePreset;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reference implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();
    
    public static final String MOD_ID = "addola";
    public static final String MOD_NAME = "Addola";

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
    
    public static final Identifier BACKPACK_IDENTIFIER_IRON = new Identifier(MOD_ID, "backpack_iron");
    public static final Identifier BACKPACK_IDENTIFIER_GOLD = new Identifier(MOD_ID, "backpack_gold");
    public static final Identifier BACKPACK_IDENTIFIER_DIAMOND = new Identifier(MOD_ID, "backpack_diamond");
    public static final Identifier BACKPACK_IDENTIFIER_NETHERITE = new Identifier(MOD_ID, "backpack_netherite");
    public static BlockEntityType<IronBackpackEntity> BACKPACK_BLOCK_ENTITY_IRON;
    public static BlockEntityType<GoldBackpackEntity> BACKPACK_BLOCK_ENTITY_GOLD;
    public static BlockEntityType<DiamondBackpackEntity> BACKPACK_BLOCK_ENTITY_DIAMOND;
    public static BlockEntityType<NetheriteBackpackEntity> BACKPACK_BLOCK_ENTITY_NETHERITE;
    public static ScreenHandlerType<IronBackpackGui> SCREEN_HANDLER_TYPE_IRON;
    public static ScreenHandlerType<GoldBackpackGui> SCREEN_HANDLER_TYPE_GOLD;
    public static ScreenHandlerType<DiamondBackpackGui> SCREEN_HANDLER_TYPE_DIAMOND;
    public static ScreenHandlerType<NetheriteBackpackGui> SCREEN_HANDLER_TYPE_NETHERITE;

    public static final Block BACKPACK_IRON = new IronBackpackBlock(FabricBlockSettings.of(Material.CARPET).hardness(0.1f).sounds(BlockSoundGroup.WOOL));
    public static final Block BACKPACK_GOLD = new GoldBackpackBlock(FabricBlockSettings.of(Material.CARPET).hardness(0.1f).sounds(BlockSoundGroup.WOOL));
    public static final Block BACKPACK_DIAMOND = new DiamondBackpackBlock(FabricBlockSettings.of(Material.CARPET).hardness(0.1f).sounds(BlockSoundGroup.WOOL));
    public static final Block BACKPACK_NETHERITE = new NetheriteBackpackBlock(FabricBlockSettings.of(Material.CARPET).hardness(0.1f).sounds(BlockSoundGroup.WOOL));
    public static final ItemGroup ADDOLA = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "general"), () -> new ItemStack(BACKPACK_IRON));
    public static final Item BACKPACK_ITEM_IRON = new BlockItem(BACKPACK_IRON, new Item.Settings().group(Reference.ADDOLA).maxCount(1));
    public static final Item BACKPACK_ITEM_GOLD = new BlockItem(BACKPACK_GOLD, new Item.Settings().group(Reference.ADDOLA).maxCount(1));
    public static final Item BACKPACK_ITEM_DIAMOND = new BlockItem(BACKPACK_DIAMOND, new Item.Settings().group(Reference.ADDOLA).maxCount(1));
    public static final Item BACKPACK_ITEM_NETHERITE = new BlockItem(BACKPACK_NETHERITE, new Item.Settings().group(Reference.ADDOLA).maxCount(1));

	@Override
    public void onInitialize() {
		BACKPACK_BLOCK_ENTITY_IRON = Registry.register(Registry.BLOCK_ENTITY_TYPE, BACKPACK_IDENTIFIER_IRON, BlockEntityType.Builder.create(IronBackpackEntity::new, BACKPACK_IRON).build(null));
		BACKPACK_BLOCK_ENTITY_GOLD = Registry.register(Registry.BLOCK_ENTITY_TYPE, BACKPACK_IDENTIFIER_GOLD, BlockEntityType.Builder.create(GoldBackpackEntity::new, BACKPACK_GOLD).build(null));
		BACKPACK_BLOCK_ENTITY_DIAMOND = Registry.register(Registry.BLOCK_ENTITY_TYPE, BACKPACK_IDENTIFIER_DIAMOND, BlockEntityType.Builder.create(DiamondBackpackEntity::new, BACKPACK_DIAMOND).build(null));
		BACKPACK_BLOCK_ENTITY_NETHERITE = Registry.register(Registry.BLOCK_ENTITY_TYPE, BACKPACK_IDENTIFIER_NETHERITE, BlockEntityType.Builder.create(NetheriteBackpackEntity::new, BACKPACK_NETHERITE).build(null));
		Registry.register(Registry.BLOCK, BACKPACK_IDENTIFIER_IRON, BACKPACK_IRON);
		Registry.register(Registry.BLOCK, BACKPACK_IDENTIFIER_GOLD, BACKPACK_GOLD);
		Registry.register(Registry.BLOCK, BACKPACK_IDENTIFIER_DIAMOND, BACKPACK_DIAMOND);
		Registry.register(Registry.BLOCK, BACKPACK_IDENTIFIER_NETHERITE, BACKPACK_NETHERITE);
	    Registry.register(Registry.ITEM, BACKPACK_IDENTIFIER_IRON, BACKPACK_ITEM_IRON);
	    Registry.register(Registry.ITEM, BACKPACK_IDENTIFIER_GOLD, BACKPACK_ITEM_GOLD);
	    Registry.register(Registry.ITEM, BACKPACK_IDENTIFIER_DIAMOND, BACKPACK_ITEM_DIAMOND);
	    Registry.register(Registry.ITEM, BACKPACK_IDENTIFIER_NETHERITE, BACKPACK_ITEM_NETHERITE);
		SCREEN_HANDLER_TYPE_IRON = ScreenHandlerRegistry.registerSimple(BACKPACK_IDENTIFIER_IRON, (syncId, inventory) -> new IronBackpackGui(syncId, inventory, ScreenHandlerContext.EMPTY));
		SCREEN_HANDLER_TYPE_GOLD = ScreenHandlerRegistry.registerSimple(BACKPACK_IDENTIFIER_GOLD, (syncId, inventory) -> new GoldBackpackGui(syncId, inventory, ScreenHandlerContext.EMPTY));
		SCREEN_HANDLER_TYPE_DIAMOND = ScreenHandlerRegistry.registerSimple(BACKPACK_IDENTIFIER_DIAMOND, (syncId, inventory) -> new DiamondBackpackGui(syncId, inventory, ScreenHandlerContext.EMPTY));
		SCREEN_HANDLER_TYPE_NETHERITE = ScreenHandlerRegistry.registerSimple(BACKPACK_IDENTIFIER_NETHERITE, (syncId, inventory) -> new NetheriteBackpackGui(syncId, inventory, ScreenHandlerContext.EMPTY));
        CuriosApi.enqueueSlotType(SlotTypeInfo.BuildScheme.REGISTER, SlotTypePreset.BACK.getInfoBuilder().size(3).build());
    }
}