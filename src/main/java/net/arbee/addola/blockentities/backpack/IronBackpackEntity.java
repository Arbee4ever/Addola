package net.arbee.addola.blockentities.backpack;

import net.arbee.addola.Reference;
import net.arbee.addola.guis.backpack.IronBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class IronBackpackEntity extends BackpackEntity {
	public IronBackpackEntity() {
		super(Reference.BACKPACK_BLOCK_ENTITY_IRON);
	}

	public ScreenHandler createScreenHandler(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new IronBackpackGui(syncId, inventory, ScreenHandlerContext.create(world, pos));
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
	      return new IronBackpackGui(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
	}
}
