package net.arbee.addola.blockentities.backpack;

import net.arbee.addola.Reference;
import net.arbee.addola.guis.backpack.NetheriteBackpackGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class NetheriteBackpackEntity extends BackpackEntity {
	public NetheriteBackpackEntity() {
		super(Reference.BACKPACK_BLOCK_ENTITY_NETHERITE);
	}

	public ScreenHandler createScreenHandler(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new NetheriteBackpackGui(syncId, inventory, ScreenHandlerContext.create(world, pos));
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
	      return new NetheriteBackpackGui(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
	}
}
