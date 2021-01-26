package net.arbee.addola.blockentities.backpack;

import net.arbee.addola.Reference;
import net.arbee.addola.guis.backpack.GoldBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class GoldBackpackEntity extends BackpackEntity {
	public GoldBackpackEntity() {
		super(Reference.BACKPACK_BLOCK_ENTITY_GOLD);
	}

	public ScreenHandler createScreenHandler(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new GoldBackpackGui(syncId, inventory, ScreenHandlerContext.create(world, pos));
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
	      return new GoldBackpackGui(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
	}
}
