package net.arbee.addola.blockentities.backpack;

import net.arbee.addola.Reference;
import net.arbee.addola.guis.backpack.DiamondBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class DiamondBackpackEntity extends BackpackEntity {
	public DiamondBackpackEntity() {
		super(Reference.BACKPACK_BLOCK_ENTITY_DIAMOND);
	}

	public ScreenHandler createScreenHandler(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new DiamondBackpackGui(syncId, inventory, ScreenHandlerContext.create(world, pos));
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
	      return new DiamondBackpackGui(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
	}
}
